package com.mall.framework.security.service;

import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.system.mapper.UmsMenuMapper;
import com.mall.system.mapper.UmsRoleMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户权限处理
 *
 * @author 钟舒艺
 * @date 2021-07-07-11:20
 **/
@Service
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PermissionService {

    private final UmsMenuMapper umsMenuMapper;

    private final UmsRoleMapper umsRoleMapper;


    /**
     * 通过用户信息获取权限列表
     *
     * @param umsAdmin 用户信息
     * @return 权限列表
     */
    public List<UmsMenu> getPermissionList(UmsAdmin umsAdmin) {

        List<UmsMenu> perms = new ArrayList<>();

        if (!umsAdmin.getIsAdmin()) {
            Set<Long> p = umsMenuMapper.selectUserPermsIdsById(umsAdmin.getId());
            p.addAll(umsMenuMapper.selectDeptPermsId(umsAdmin.getId()));
            perms.addAll(umsMenuMapper.selectBatchIds(p));
        }
        return perms;
    }
}
