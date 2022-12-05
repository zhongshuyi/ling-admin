package com.ling.system.security.service;

import com.ling.framework.service.PermissionService;
import com.ling.system.utils.SecurityUtils;
import java.io.Serializable;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-27 15:45
 **/
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {


    /**
     * 获取权限列表.
     *
     * @param userId 用户id.
     * @return 权限列表
     */
    @Override
    public Set<String> getPermissionList(Serializable userId) {
        return SecurityUtils.getLoginUserInfo().getPermissionList();
    }

    /**
     * 获取角色标识列表.
     *
     * @param userId 用户id.
     * @return 角色标识列表
     */
    @Override
    public Set<String> getRoleList(Serializable userId) {
        return SecurityUtils.getLoginUserInfo().getRoleNameSet();
    }
}
