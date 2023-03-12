package com.ling.system.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ling.common.constant.AppConstants;
import com.ling.common.core.domain.model.LoginUser;
import com.ling.common.core.domain.model.SysMenu;
import com.ling.common.core.domain.model.User;
import com.ling.common.enums.BusinessExceptionMsgEnum;
import com.ling.common.enums.DataScopeTypeEnum;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.mybatisplus.helper.DataPermissionHelper;
import com.ling.framework.service.UserService;
import com.ling.framework.utils.SecurityUtils;
import com.ling.system.mapper.SysMenuMapper;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysDeptService;
import com.ling.system.service.ISysRoleService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-27 17:52
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    /**
     * 自定义配置信息.
     */
    private final CustomConfig config;

    /**
     * 系统用户服务.
     */
    private final ISysAdminService sysAdminService;

    /**
     * 菜单服务.
     */
    private final SysMenuMapper sysMenuMapper;

    /**
     * 角色服务.
     */
    private final ISysRoleService sysRoleService;

    /**
     * 部门服务.
     */
    private final ISysDeptService sysDeptService;

    /**
     * 根据用户名获取用户.
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User getUserByUsername(String username) {
        return sysAdminService.getSysAdminByUsername(username);
    }

    /**
     * 登录之后需要进行的操作.
     *
     * @param user 用户信息.
     */
    @Override
    public void afterLogin(User user) {
        // 组合在线用户信息
        LoginUser userInfo = new LoginUser();

        // 设置用户基本信息
        userInfo.setUser(user);

        // 添加用户角色菜单
        HashSet<SysMenu> menus = new HashSet<>(sysMenuMapper.listUserPermsById(user.getId()));
        // 添加用户部门角色菜单
        menus.addAll(sysMenuMapper.listDeptRolePermsByUserId(Long.valueOf(user.getId().toString())));
        userInfo.setSysMenuAndPermissionList(new ArrayList<>(menus));

        // 设置角色
        userInfo.setSysRoles(sysRoleService.selectRoleListByUserId(Long.valueOf(user.getId().toString())));

        // 设置部门
        userInfo.setSysDepts(sysDeptService.selectDeptListByUserId(Long.valueOf(user.getId().toString())));

        // 获取该用户的数据权限集
        userInfo.setDataScopeTypeEnumCodeList(
                DataPermissionHelper.getDataPermissionTypeEnum(userInfo)
                        .stream().map(DataScopeTypeEnum::getCode).collect(Collectors.toList())
        );

        // 判断是否是超级管理员以及角色是否被禁用
        userInfo.getSysRoles().forEach(r -> {
            if (r.getStatus().equals(AppConstants.DISABLE)) {
                log.info("登录用户：{} 角色{}已被停用.", user.getUsername(), r.getRoleName());
                throw new BusinessErrorException(BusinessExceptionMsgEnum.ROLE_DISABLE);
            }
            // 判断配置文件中的角色id
            if (r.getId().equals(config.getApp().getSuperAdminRoleId())) {
                userInfo.setIsAdmin(true);
            }
        });
        if (userInfo.getIsAdmin() == null) {
            userInfo.setIsAdmin(false);
        }

        // 管理员拥有所有权限与菜单
        if (Boolean.TRUE.equals(userInfo.getIsAdmin())) {
            userInfo.setPermissionList(Set.of("*"));
            userInfo.setSysMenuAndPermissionList(
                    sysMenuMapper.selectList(
                            Wrappers.<SysMenu>lambdaQuery()
                                    .eq(SysMenu::getIsDeleted, 0)
                                    .select(SysMenu.class, info -> !"update_time".equals(info.getColumn()) || !"create_time".equals(info.getColumn()))
                    ));
        } else {
            // 提取出权限
            userInfo.setPermissionList(menus.stream().filter(p -> p.getMenuType() == 2).map(SysMenu::getPerms).collect(Collectors.toSet()));
        }


        // 将用户信息存储至redis
        SecurityUtils.setLoginUserInfo(userInfo);
    }


}
