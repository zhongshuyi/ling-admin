package com.mall.system.service;

import com.mall.common.core.mybatisplus.core.IServicePlus;
import com.mall.common.core.mybatisplus.core.PagePlus;
import com.mall.system.bo.UserBo;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.vo.UserVo;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息表 服务类.
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
public interface IUmsAdminService extends IServicePlus<UmsAdmin, UserVo> {


    /**
     * 根据用户名查询用户.
     *
     * @param userName 用户名.
     * @return 用户信息(含密码)
     */
    UmsAdmin getUmsAdminByUserName(String userName);

    /**
     * 根据id获取用户信息.
     *
     * @param id 用户id
     * @return 用户信息
     */
    UmsAdmin getUmsAdminById(Long id);

    /**
     * 根据角色id获取用户列表.
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UmsAdmin> getUserListByRoleId(Long roleId);


    /**
     * 条件分页查询用户列表.
     *
     * @param pagePlus 分页信息
     * @param bo       条件
     * @return 分页后信息
     */
    PagePlus<UmsAdmin, UserVo> getUserListPage(
            PagePlus<UmsAdmin, UserVo> pagePlus,
            UserBo bo
    );

    /**
     * 上传头像.
     *
     * @param id         用户id
     * @param file       头像文件
     * @param uploadById 上传人id
     * @return 是否成功
     */
    Boolean uploadAvatar(
            Long id,
            MultipartFile file,
            final Long uploadById
    );

    /**
     * 获取用户头像地址.
     *
     * @param id 用户id
     * @return 头像地址
     */
    String getUserAvatar(Long id);

    /**
     * 检查用户名是否重复.
     *
     * @param umsAdmin 用户信息
     * @return 是否重复
     */
    Boolean checkUsernameUnique(UmsAdmin umsAdmin);
}
