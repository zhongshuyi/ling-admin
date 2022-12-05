package com.ling.framework.service;

import java.io.Serializable;
import java.util.Set;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-27 15:32
 **/
public interface PermissionService {
    /**
     * 获取权限列表.
     *
     * @param userId 用户id.
     * @return 权限列表
     */
    Set<String> getPermissionList(Serializable userId);


    /**
     * 获取角色标识列表.
     *
     * @param userId 用户id.
     * @return 角色标识列表
     */
    Set<String> getRoleList(Serializable userId);

}
