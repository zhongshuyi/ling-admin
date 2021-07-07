package com.mall.service.impl;

import com.mall.model.UmsRole;
import com.mall.mapper.UmsRoleMapper;
import com.mall.service.IUmsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements IUmsRoleService {

    @Override
    public Set<String> getRoleKeyListByUserId(Long id) {
        return null;
    }
}
