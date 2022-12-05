package com.ling.framework.service;

import cn.dev33.satoken.stp.StpInterface;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户权限处理.
 *
 * @author 钟舒艺
 * @since 2022-10-26 20:01
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    /**
     * 权限服务.
     */
    private PermissionService permissionService;


    /**
     * 返回指定账号id所拥有的权限码集合.
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(
            Object loginId,
            String loginType) {
        log.info("获取权限列表");
        return new ArrayList<>(permissionService.getPermissionList(loginId.toString()));
    }

    /**
     * 返回指定账号id所拥有的角色标识集合.
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色标识集合
     */
    @Override
    public List<String> getRoleList(
            Object loginId,
            String loginType) {
        log.info("获取角色列表");
        return new ArrayList<>(permissionService.getRoleList(loginId.toString()));
    }
}
