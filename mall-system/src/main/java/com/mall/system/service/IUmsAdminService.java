package com.mall.system.service;

import com.mall.common.core.mybatisplus.core.IServicePlus;
import com.mall.common.core.mybatisplus.core.PagePlus;
import com.mall.system.bo.UserBo;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.vo.UserVo;
import java.util.List;

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
            PagePlus<UmsAdmin, UserVo> pagePlus, UserBo bo
    );
}
