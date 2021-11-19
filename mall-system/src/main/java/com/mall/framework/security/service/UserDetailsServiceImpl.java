package com.mall.framework.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.constant.GlobalConstants;
import com.mall.common.enums.BusinessMsgEnum;
import com.mall.common.enums.Status;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.entity.UmsRole;
import com.mall.system.mapper.UmsRoleMapper;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.service.IUmsDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户验证处理.
 *
 * @author 钟舒艺
 * @date 2021-07-06-17:31
 **/
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final transient IUmsAdminService umsAdminService;

    private final transient IUmsDeptService umsDeptService;

    private final transient PermissionService permissionService;

    private final transient UmsRoleMapper umsRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username + "尝试登录");
        UmsAdmin umsAdmin = umsAdminService.getOne(
                Wrappers.<UmsAdmin>lambdaQuery()
                        .eq(UmsAdmin::getUsername, username).last("limit 1"));
        if (umsAdmin == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (Status.DELETED.getCode().equals(umsAdmin.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new BusinessErrorException(BusinessMsgEnum.ACCOUNT_DELETED);
        } else if (Status.DISABLE.getCode().equals(umsAdmin.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BusinessErrorException(BusinessMsgEnum.ACCOUNT_DISABLE);
        }
        List<UmsRole> roles = umsRoleMapper.selectRoleListByUserId(umsAdmin.getId());
        roles.forEach((r) -> {
            if (r.getStatus().equals(Status.DISABLE.getCode())) {
                log.info("登录用户：{} 角色{}已被停用.", username, r.getRoleName());
                throw new BusinessErrorException(BusinessMsgEnum.ROLE_DISABLE);
            }
            if (r.getId().equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
                umsAdmin.setIsAdmin(true);
            }
        });
        return new AdminUserDetails(
                umsAdmin,
                permissionService.getPermissionList(umsAdmin),
                roles, umsDeptService.getDeptListByUserId(umsAdmin.getId()));
    }
}
