package com.ling.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.ling.common.core.mybatisplus.core.PagePlus;
import com.ling.common.enums.BusinessTypeEnum;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.srvice.MinioService;
import com.ling.system.convert.SysAdminConvert;
import com.ling.system.dto.SysAdminDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.entity.SysFile;
import com.ling.system.mapper.SysAdminMapper;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysDeptService;
import com.ling.system.service.ISysFileService;
import com.ling.system.service.ISysRoleService;
import com.ling.system.vo.SysAdminVO;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-24 14:41
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements ISysAdminService {

    /**
     * 系统用户mapper.
     */
    private final SysAdminMapper sysAdminMapper;


    /**
     * 部门服务.
     */
    private final ISysDeptService sysDeptService;

    /**
     * 角色服务.
     */
    private final ISysRoleService sysRoleService;

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


    /**
     * 根据用户名查询用户.
     *
     * @param userName 用户名.
     * @return 用户信息(含密码)
     */
    @Override
    public SysAdmin getSysAdminByUsername(String userName) {
        return setAvatarUrl(sysAdminMapper.getSysAdminByUserName(userName));
    }


    /**
     * 根据id获取用户信息.
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public SysAdmin getSysAdminById(Long userId) {
        return setAvatarUrl(sysAdminMapper.getSysAdminById(userId));
    }

    /**
     * 根据角色id获取用户列表.
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    @Override
    public List<SysAdmin> listSysAdminListByRoleId(Long roleId) {
        return setAvatarUrl(sysAdminMapper.listAdminListByRoleId(roleId));
    }

    /**
     * 条件分页查询用户列表.
     *
     * @param pagePlus 分页信息
     * @param dto      前端传来的条件
     * @return 分页后信息
     */
    @Override
    public PagePlus<SysAdmin, SysAdminVO> listSysAdminPage(
            PagePlus<SysAdmin, SysAdminVO> pagePlus,
            SysAdminDTO dto) {
        // 开启分页
        PageMethod.startPage((int) pagePlus.getCurrent(), (int) pagePlus.getSize());
        final List<SysAdmin> list = sysAdminMapper.listSysAdmin(dto);
        final PageInfo<SysAdmin> pageInfo = new PageInfo<>(list);
        pagePlus.setRecords(setAvatarUrl(pageInfo.getList()));
        // 转为VO
        pagePlus.setRecordsVo(SysAdminConvert.INSTANCT.convertToVOList(pageInfo.getList()));
        // 设置总数
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
    public Boolean setSysAdminAvatar(
            Long id,
            MultipartFile file,
            Long uploadById) {

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
        final SysFile oldAvatar = getFileService().getUserAvatar(id);
        // 更新用户头像信息
        flag = flag && updateById(new SysAdmin().setId(id).setAvatarFileId(sysFile.getId()));

        // 删除旧头像
        if (oldAvatar != null) {
            getMinIoService().deleteFile(oldAvatar.getPath() + "/" + oldAvatar.getName() + oldAvatar.getPostfix());
            flag = flag && getFileService().removeById(oldAvatar.getId());
        }
        return flag;
    }

    /**
     * 获取用户头像地址.
     *
     * @param userId 用户id
     * @return 头像地址
     */
    @Override
    public String getSysAdminAvatarUrl(Long userId) {
        final String path = sysAdminMapper.getUserAvatar(userId);
        if (CharSequenceUtil.isEmpty(path)) {
            return null;
        }
        return getConfig().getMinio().getUrl() + "/" + getConfig().getMinio().getBucketName() + "/" + path;
    }

    /**
     * 检查用户名是否重复.
     *
     * @param sysAdminDTO 用户信息
     * @return 是否重复
     */
    @Override
    public Boolean checkUsernameUnique(SysAdminDTO sysAdminDTO) {
        final SysAdmin user = getSysAdminByUsername(sysAdminDTO.getUsername());
        if (sysAdminDTO.getId() != null) {
            return user != null && user.getId().equals(sysAdminDTO.getId());
        } else {
            return user == null;
        }
    }

    /**
     * 新增系统用户.
     *
     * @param sysAdminDTO 用户信息.
     * @return 是否成功
     */
    @Override
    public Boolean saveSysAdmin(SysAdminDTO sysAdminDTO) {
        validEntityBeforeSave(sysAdminDTO);

        boolean flag = super.save(SysAdminConvert.INSTANCT.dtoToEntity(sysAdminDTO));
        // 设置用户角色
        flag = flag && sysRoleService.updateUserRole(sysAdminDTO.getId(), sysAdminDTO.getRoleIds());
        // 设置用户部门
        flag = flag && sysDeptService.setUserDept(sysAdminDTO.getId(), sysAdminDTO.getDeptIds());

        return flag;
    }

    /**
     * 根据id删除系统用户.
     *
     * @param sysAdminId 系统用户id
     * @return 是否成功
     */
    @Override
    public Boolean removeSysAdminById(Long sysAdminId) {
        return super.removeById(sysAdminId);
    }

    /**
     * 根据id修改系统用户.
     *
     * @param sysAdminDTO 数据传输对象
     * @return 是否更新成功
     */
    @Override
    public Boolean updateSysAdminById(SysAdminDTO sysAdminDTO) {
        validEntityBeforeSave(sysAdminDTO);
        return sysAdminMapper.updateById(SysAdminConvert.INSTANCT.dtoToEntity(sysAdminDTO)) > 0;
    }


    /**
     * 保存前的数据校验.
     *
     * @param sysAdminDTO 用户信息
     */
    public final void validEntityBeforeSave(final SysAdminDTO sysAdminDTO) {
        if (Boolean.FALSE.equals(checkUsernameUnique(sysAdminDTO))) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "用户名已存在");
        }
        // 设置默认的角色与部门
        setDefaultDeptAndRole(sysAdminDTO);

        if (ObjectUtil.isEmpty(sysAdminDTO.getId())) {
            // 加密密码
            sysAdminDTO.setPassword(BCrypt.hashpw(sysAdminDTO.getPassword(), BCrypt.gensalt()));
        }

    }

    /**
     * 设置用户头像的url.
     *
     * @param sysAdminList 用户列表.
     */
    private List<SysAdmin> setAvatarUrl(List<SysAdmin> sysAdminList) {
        if (CollUtil.isNotEmpty(sysAdminList)) {
            sysAdminList.forEach(this::setAvatarUrl);
        }
        return sysAdminList;
    }

    /**
     * 设置用户头像的url.
     *
     * @param sysAdmin 用户信息.
     */
    private SysAdmin setAvatarUrl(SysAdmin sysAdmin) {
        if (sysAdmin != null && CharSequenceUtil.isNotEmpty(sysAdmin.getAvatar())) {
            sysAdmin.setAvatar(builderAvatarUrl(sysAdmin.getAvatar()));
        }
        return sysAdmin;
    }

    /**
     * 拼接url.
     *
     * @param sourceUrl 原始url（文件名）
     * @return 可访问地址
     */
    private String builderAvatarUrl(String sourceUrl) {
        return getConfig().getMinio().getUrl()
                + "/" + getConfig().getMinio().getBucketName()
                + "/" + sourceUrl;
    }

    /**
     * 设置默认的部门与角色.
     *
     * @param user 用户操作对象
     */
    private void setDefaultDeptAndRole(final SysAdminDTO user) {
        if (CollUtil.isEmpty(user.getRoleIds())) {
            final HashSet<Long> roleIds = new HashSet<>(1);
            roleIds.add(config.getApp().getDefaultRoleId());
            user.setRoleIds(roleIds);
        }
        if (CollUtil.isEmpty(user.getDeptIds())) {
            final HashSet<Long> deptIds = new HashSet<>(1);
            deptIds.add(config.getApp().getDefaultDeptId());
            user.setDeptIds(deptIds);
        }
    }
}

