package com.mall.system.service.impl;

import com.mall.common.core.domain.entity.UmsRole;
import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.system.mapper.UmsRoleMapper;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.vo.RoleVo;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务实现类
 * @author 钟舒艺
 * @since 2021-07-07
 */
@Service
public class UmsRoleServiceImpl extends ServicePlusImpl<UmsRoleMapper, UmsRole, RoleVo> implements IUmsRoleService {

}
