package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.system.bo.DeptBo;
import com.mall.system.entity.UmsDept;
import com.mall.system.mapper.UmsDeptMapper;
import com.mall.system.service.IUmsDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
@Service
public class UmsDeptServiceImpl
        extends ServiceImpl<UmsDeptMapper, UmsDept>
        implements IUmsDeptService {

    private static final long serialVersionUID = 8302483662294489594L;

    @Override
    public Boolean addDept(DeptBo addBo) {
        UmsDept dept = BeanUtil.toBean(addBo, UmsDept.class);
        return save(dept);
    }


    @Override
    public Boolean checkDeptUnique(DeptBo addBo) {
        return
                getOne(
                        Wrappers.<UmsDept>lambdaQuery()
                                .eq(UmsDept::getParentId, addBo.getParentId())
                                .eq(UmsDept::getDeptName, addBo.getDeptName())
                ) != null;
    }

    @Override
    public List<UmsDept> getDeptChildren(Long parentId) {
        return list(Wrappers.<UmsDept>lambdaQuery().eq(UmsDept::getParentId, parentId));
    }

    @Override
    public Boolean deleteById(Long id) {
        List<UmsDept> list = getDeptChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (UmsDept dept : list) {
                deleteById(dept.getId());
            }
        }
        return removeById(id);
    }

    @Override
    public List<UmsDept> getDeptListByUserId(Long userId) {
        return baseMapper.getDeptListByUserId(userId);
    }

}
