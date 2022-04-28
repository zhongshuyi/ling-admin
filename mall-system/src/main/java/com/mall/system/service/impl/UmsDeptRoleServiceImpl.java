package com.mall.system.service.impl;

import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.system.entity.UmsDeptRole;
import com.mall.system.mapper.UmsDeptRoleMapper;
import com.mall.system.service.IUmsDeptRoleService;
import com.mall.system.vo.DeptRoleVo;
import org.springframework.stereotype.Service;

/**
 * 上级管理负责的部门服务实现.
 *
 * @author 钟舒艺
 **/
@Service
public class UmsDeptRoleServiceImpl
        extends ServicePlusImpl<UmsDeptRoleMapper, UmsDeptRole, DeptRoleVo>
        implements IUmsDeptRoleService {
    private static final long serialVersionUID = 3458696168561963474L;
}
