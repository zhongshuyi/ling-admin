package com.mall.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.enums.BusinessMsgEnum;
import com.mall.common.enums.UserStatus;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.system.mapper.UmsRoleMapper;
import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.system.service.IUmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用户验证处理
 * @author 钟舒艺
 * @date 2021-07-06-17:31
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    final
    IUmsAdminService umsAdminService;

    final
    PermissionService permissionService;

    final
    UmsRoleMapper umsRoleMapper;

    public UserDetailsServiceImpl(IUmsAdminService umsAdminService, PermissionService permissionService, UmsRoleMapper umsRoleMapper) {
        this.umsAdminService = umsAdminService;
        this.permissionService = permissionService;
        this.umsRoleMapper = umsRoleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        UmsAdmin umsAdmin = umsAdminService.getOne(queryWrapper);
        if(umsAdmin==null){
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(umsAdmin.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new BusinessErrorException(BusinessMsgEnum.ACCOUNT_DELETED);
        }
        else if (UserStatus.DISABLE.getCode().equals(umsAdmin.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new BusinessErrorException(BusinessMsgEnum.ACCOUNT_DISABLE);
        }
        return new AdminUserDetails(umsAdmin,permissionService.getPermissionList(umsAdmin),umsRoleMapper.selectRoleListByUserId(umsAdmin.getUserId()));
    }
}
