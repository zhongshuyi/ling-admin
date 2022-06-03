package com.ling.system.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.ling.common.core.mybatisplus.core.PagePlus;
import com.ling.common.core.mybatisplus.core.ServicePlusImpl;
import com.ling.common.enums.BusinessTypeEnum;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.oss.MinioService;
import com.ling.system.dto.UserDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.entity.SysFile;
import com.ling.system.mapper.SysAdminMapper;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysFileService;
import com.ling.system.vo.UserVo;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息表 服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class SysAdminServiceImpl extends ServicePlusImpl<SysAdminMapper, SysAdmin, UserVo> implements ISysAdminService {

    private static final long serialVersionUID = -6767396404407827925L;

    /**
     * 配置信息.
     */
    @Getter
    private final CustomConfig config;

    /**
     * minIo服务.
     */
    @Getter
    private final MinioService minIoService;

    /**
     * 文件表服务.
     */
    @Getter
    private final ISysFileService fileService;

    @Override
    public final SysAdmin selectSysAdminByUserName(final String userName) {
        final SysAdmin sysAdmin = getBaseMapper().getSysAdminByUserName(userName);
        if (sysAdmin != null && sysAdmin.getAvatar() != null) {
            sysAdmin.setAvatar(
                    getConfig().getMinio().getUrl()
                            + "/" + getConfig().getMinio().getBucketName()
                            + "/" + sysAdmin.getAvatar());
        }
        return sysAdmin;
    }

    @Override
    public final SysAdmin selectSysAdminById(final Long id) {
        final SysAdmin sysAdmin = getBaseMapper().getSysAdminById(id);
        // 拼接头像路径
        if (CharSequenceUtil.isNotEmpty(sysAdmin.getAvatar())) {
            sysAdmin.setAvatar(
                    getConfig().getMinio().getUrl()
                            + "/" + getConfig().getMinio().getBucketName()
                            + "/" + sysAdmin.getAvatar());
        }
        return sysAdmin;
    }

    @Override
    public final List<SysAdmin> selectUserListByRoleId(final Long roleId) {
        final List<SysAdmin> list = getBaseMapper().getUserListByRoleId(roleId);
        // 拼接头像路径
        list.forEach(u -> {
            if (CharSequenceUtil.isNotEmpty(u.getAvatar())) {
                u.setAvatar(
                        getConfig().getMinio().getUrl()
                                + "/" + getConfig().getMinio().getBucketName()
                                + "/" + u.getAvatar());
            }
        });
        return list;
    }

    @Override
    public final PagePlus<SysAdmin, UserVo> selectUserListPage(
            final PagePlus<SysAdmin, UserVo> pagePlus,
            final UserDTO bo
    ) {
        // 开启分页
        PageMethod.startPage((int) pagePlus.getCurrent(), (int) pagePlus.getSize());
        final List<SysAdmin> list = getBaseMapper().queryUserList(bo);
        final PageInfo<SysAdmin> pageInfo = new PageInfo<>(list);
        // 拼接头像路径
        pageInfo.getList().forEach(u -> {
            if (CharSequenceUtil.isNotEmpty(u.getAvatar())) {
                u.setAvatar(
                        getConfig().getMinio().getUrl()
                                + "/" + getConfig().getMinio().getBucketName()
                                + "/" + u.getAvatar());
            }
        });
        pagePlus.setRecords(pageInfo.getList());
        pagePlus.setRecordsVo(BeanUtil.copyToList(pageInfo.getList(), UserVo.class));
        pagePlus.setTotal(pageInfo.getTotal());
        return pagePlus;
    }

    /**
     * 上传头像.
     *
     * @param id         用户id
     * @param file       头像文件
     * @param uploadById 上传人id
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean uploadAvatar(
            final Long id,
            final MultipartFile file,
            final Long uploadById
    ) {
        final SysFile sysFile = new SysFile();
        // 原始文件名
        final String originalName = file.getOriginalFilename();
        sysFile.setOriginalName(originalName);
        final String postfix = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : "";
        // 校验后缀名
        if (!CharSequenceUtil.contains(getConfig().getApp().getAvatarPostfix(), postfix)) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "只接受jpg与png格式的图片");
        }
        sysFile.setPostfix(postfix);
        final String fileType;
        final InputStream fileInputStream;
        try {
            fileInputStream = file.getInputStream();
            // 根据文件头获取文件类型
            fileType = FileTypeUtil.getType(file.getInputStream());
        } catch (final IOException e) {
            throw new BusinessErrorException("读取文件失败", e);
        }
        // 校验文件头
        if (!CharSequenceUtil.contains(getConfig().getApp().getAvatarPostfix(), fileType)) {
            throw new BusinessErrorException(
                    HttpStatus.HTTP_BAD_REQUEST,
                    "只接收" + config.getApp().getAvatarPostfix() + "图片"
            );
        }
        // 储存文件名
        sysFile.setName(UUID.fastUUID().toString());
        // 储存文件至minio(路径为 头像地址/文件名+后缀名)
        minIoService.upload(getConfig().getApp().getAvatarPath() + "/" + sysFile.getName() + postfix, fileInputStream,
                file.getContentType()
        );
        // 设置文件路径(桶名/头像储存路径)
        sysFile.setPath(getConfig().getApp().getAvatarPath());
        sysFile.setSize(file.getSize());
        // 上传者id
        sysFile.setUploadById(id);
        // 设置业务类型
        sysFile.setBusinessType(BusinessTypeEnum.USER_AVATAR.getCode());

        boolean flag;

        // 储存到文件表
        flag = getFileService().save(sysFile);
        // 获取此用户之前的头像
        final SysFile oldAvatar = getFileService().selectUserAvatar(id);
        // 更新用户头像信息
        flag = flag && updateById(new SysAdmin().setId(id).setAvatarFileId(sysFile.getId()));

        // 删除旧头像
        if (oldAvatar != null) {
            getMinIoService().deleteFile(oldAvatar.getPath() + "/" + oldAvatar.getName() + oldAvatar.getPostfix());
            flag = flag && getFileService().removeById(oldAvatar.getId());
        }
        return flag;
    }

    @Override
    public final String getUserAvatar(final Long id) {
        final String path = getBaseMapper().getUserAvatar(id);
        if (CharSequenceUtil.isEmpty(path)) {
            return null;
        }
        return getConfig().getMinio().getUrl() + "/" + getConfig().getMinio().getBucketName() + "/" + path;
    }

    @Override
    public final void validEntityBeforeSave(final SysAdmin sysAdmin) {
        super.validEntityBeforeSave(sysAdmin);
        if (Boolean.FALSE.equals(checkUsernameUnique(sysAdmin))) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "用户名已存在");
        }
    }

    /**
     * 检查用户名是否唯一.
     *
     * @param sysAdmin 用户信息
     * @return true就是唯一的
     */
    @Override
    public Boolean checkUsernameUnique(final SysAdmin sysAdmin) {
        final SysAdmin user = getOne(
                Wrappers.<SysAdmin>lambdaQuery().eq(SysAdmin::getUsername, sysAdmin.getUsername()).last("limit 1"));
        if (sysAdmin.getId() != null) {
            return user != null && user.getId().equals(sysAdmin.getId());
        } else {
            return user == null;
        }
    }
}
