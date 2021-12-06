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
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 添加部门并设置父级列表.
     *
     * @param addBo 添加对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addDept(final DeptBo addBo) {
        UmsDept dept = BeanUtil.toBean(addBo, UmsDept.class);
        UmsDept parent = getBaseMapper()
                .selectOne(Wrappers.<UmsDept>lambdaQuery()
                        .eq(UmsDept::getId, dept.getParentId())
                        .select(UmsDept::getId, UmsDept::getParentList));
        dept.setParentList(parent.getParentList() + parent.getId() + ",");
        Boolean isSave = save(dept);
        dept.setParentList(dept.getParentList() + dept.getId() + ",");
        Boolean isUpdate = updateById(dept);
        return isSave && isUpdate;
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

    /**
     * 删除部门以及该部门的子部门.
     *
     * @param id 部门id
     * @return 是否全部删除成功;
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(final Long id) {
        boolean isDeleted = true;
        List<UmsDept> list = getDeptChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (UmsDept dept : list) {
                if (!deleteById(dept.getId())) {
                    isDeleted = false;
                }
            }
        }
        return removeById(id) && isDeleted;
    }

    @Override
    public final List<UmsDept> getDeptListByUserId(final Long userId) {
        return getBaseMapper().getDeptListByUserId(userId);
    }

}
