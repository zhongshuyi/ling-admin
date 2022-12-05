package com.ling.system.service.impl;

import com.ling.common.core.mybatisplus.core.ServicePlusImpl;
import com.ling.system.dto.SysDeptRoleDTO;
import com.ling.system.entity.SysDeptRole;
import com.ling.system.mapper.SysDeptRoleMapper;
import com.ling.system.service.ISysDeptRoleService;
import com.ling.system.vo.SysDeptRoleVO;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * 上级管理负责的部门服务实现.
 *
 * @author 钟舒艺
 **/
@Service
public class SysDeptRoleServiceImpl
        extends ServicePlusImpl<SysDeptRoleMapper, SysDeptRole, SysDeptRoleVO, SysDeptRoleDTO>
        implements ISysDeptRoleService {


    private static final long serialVersionUID = 2942596221152038864L;

    @Override
    public final Set<Long> getDeptRoleIdsByUserId(
            final Long deptId,
            final Long userId) {
        return getBaseMapper().getDeptRoleIdsByUserId(deptId, userId);
    }

    @Override
    public final Boolean setDeptRoleToUser(
            final Long deptId,
            final Long userId,
            final Set<Long> deptRoleIds) {

        // 分析差异存储
        final Set<Long> oldIds = getBaseMapper().getDeptRoleIdsByUserId(deptId, userId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(deptRoleIds);
        final boolean isSuccess =
                result.isEmpty() || getBaseMapper().deleteDeptRoleToUser(deptId, userId, deptRoleIds) == result.size();
        result.clear();
        result.addAll(deptRoleIds);
        result.removeAll(oldIds);
        return isSuccess
                &&
                (result.isEmpty() || getBaseMapper().insertDeptRoleToUser(deptId, userId, result) == result.size());
    }
}
