package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.system.bo.DeptBo;
import com.mall.system.entity.UmsDept;
import com.mall.system.mapper.UmsDeptMapper;
import com.mall.system.service.IUmsDeptService;
import java.util.List;
import org.springframework.stereotype.Service;

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
    public final Boolean addDept(final DeptBo addBo) {
        UmsDept dept = BeanUtil.toBean(addBo, UmsDept.class);
        return save(dept);
    }


    @Override
    public final Boolean checkDeptUnique(final DeptBo addBo) {
        return
                getOne(
                        Wrappers.<UmsDept>lambdaQuery()
                                .eq(UmsDept::getParentId, addBo.getParentId())
                                .eq(UmsDept::getDeptName, addBo.getDeptName())
                ) != null;
    }

    @Override
    public final List<UmsDept> getDeptChildren(final Long parentId) {
        return list(Wrappers.<UmsDept>lambdaQuery().eq(UmsDept::getParentId, parentId));
    }

    @Override
    public final Boolean deleteById(final Long id) {
        List<UmsDept> list = getDeptChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (UmsDept dept : list) {
                deleteById(dept.getId());
            }
        }
        return removeById(id);
    }

    @Override
    public final List<UmsDept> getDeptListByUserId(final Long userId) {
        return getBaseMapper().getDeptListByUserId(userId);
    }

}
