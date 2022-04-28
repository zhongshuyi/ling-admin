package com.mall.system.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mall.common.core.mybatisplus.core.PagePlus;
import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.common.enums.BusinessTypeEnum;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.config.CustomConfig;
import com.mall.framework.oss.MinioService;
import com.mall.system.bo.UserBo;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.entity.UmsFile;
import com.mall.system.mapper.UmsAdminMapper;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.service.IUmsFileService;
import com.mall.system.vo.UserVo;
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
public class UmsAdminServiceImpl extends ServicePlusImpl<UmsAdminMapper, UmsAdmin, UserVo> implements IUmsAdminService {

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
    private final IUmsFileService fileService;

    @Override
    public final UmsAdmin getUmsAdminByUserName(final String userName) {
        final UmsAdmin umsAdmin = getBaseMapper().getUmsAdminByUserName(userName);
        if (umsAdmin != null && umsAdmin.getAvatar() != null) {
            umsAdmin.setAvatar(
                    getConfig().getMinio().getUrl()
                            + "/" + getConfig().getMinio().getBucketName()
                            + "/" + umsAdmin.getAvatar());
        }
        return umsAdmin;
    }

    @Override
    public final UmsAdmin getUmsAdminById(final Long id) {
        final UmsAdmin umsAdmin = getBaseMapper().getUmsAdminById(id);
        if (CharSequenceUtil.isNotEmpty(umsAdmin.getAvatar())) {
            umsAdmin.setAvatar(
                    getConfig().getMinio().getUrl()
                            + "/" + getConfig().getMinio().getBucketName()
                            + "/" + umsAdmin.getAvatar());
        }
        return umsAdmin;
    }

    @Override
    public final List<UmsAdmin> getUserListByRoleId(final Long roleId) {
        final List<UmsAdmin> list = getBaseMapper().getUserListByRoleId(roleId);
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
    public final PagePlus<UmsAdmin, UserVo> getUserListPage(
            final PagePlus<UmsAdmin, UserVo> pagePlus,
            final UserBo bo
    ) {
        PageMethod.startPage((int) pagePlus.getCurrent(), (int) pagePlus.getSize());
        final List<UmsAdmin> list = getBaseMapper().queryUserList(bo);
        final PageInfo<UmsAdmin> pageInfo = new PageInfo<>(list);
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
        final UmsFile umsFile = new UmsFile();
        final String originalName = file.getOriginalFilename();
        umsFile.setOriginalName(originalName);
        final String postfix = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : "";
        // 校验后缀名
        if (!CharSequenceUtil.contains(getConfig().getApp().getAvatarPostfix(), postfix)) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "只接受jpg与png格式的图片");
        }
        umsFile.setPostfix(postfix);
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
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "只接受jpg与png格式的图片");
        }
        umsFile.setName(UUID.fastUUID().toString());
        // 储存文件至minio(路径为 头像地址/文件名+后缀名)
        minIoService.upload(getConfig().getApp().getAvatarPath() + "/" + umsFile.getName() + postfix, fileInputStream,
                file.getContentType()
        );
        // 设置文件路径(桶名/头像储存路径)
        umsFile.setPath(getConfig().getApp().getAvatarPath());
        umsFile.setSize(file.getSize());
        umsFile.setUploadById(id);
        umsFile.setBusinessType(BusinessTypeEnum.USER_AVATAR.getCode());
        // 储存到文件表
        getFileService().save(umsFile);
        // 获取此用户之前的头像
        final UmsFile oldAvatar = getFileService().getUserAvatar(id);

        final UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(id);
        umsAdmin.setAvatarFileId(umsFile.getId());
        updateById(umsAdmin);

        // 删除旧头像
        if (oldAvatar != null) {
            getMinIoService().deleteFile(oldAvatar.getPath() + "/" + oldAvatar.getName() + oldAvatar.getPostfix());
            getFileService().removeById(oldAvatar.getId());
        }
        return true;
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
    public final void validEntityBeforeSave(final UmsAdmin umsAdmin) {
        super.validEntityBeforeSave(umsAdmin);
        if (Boolean.FALSE.equals(checkUsernameUnique(umsAdmin))) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "用户名已存在");
        }
    }

    /**
     * 检查用户名是否唯一.
     *
     * @param umsAdmin 用户信息
     * @return true就是唯一的
     */
    @Override
    public Boolean checkUsernameUnique(final UmsAdmin umsAdmin) {
        final UmsAdmin user = getOne(
                Wrappers.<UmsAdmin>lambdaQuery().eq(UmsAdmin::getUsername, umsAdmin.getUsername()).last("limit 1"));
        if (umsAdmin.getId() != null) {
            return user != null && user.getId().equals(umsAdmin.getId());
        } else {
            return user == null;
        }
    }
}
