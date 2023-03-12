package com.ling.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ling.common.core.domain.model.SysDept;
import com.ling.common.core.mybatisplus.core.ServicePlusImpl;
import com.ling.system.dto.SysDeptDTO;
import com.ling.system.mapper.SysDeptMapper;
import com.ling.system.service.ISysDeptService;
import com.ling.system.vo.SysDeptVO;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
@Service
public class SysDeptServiceImpl extends ServicePlusImpl<SysDeptMapper, SysDept, SysDeptVO, SysDeptDTO> implements ISysDeptService {

    private static final long serialVersionUID = 8302483662294489594L;

    /**
     * 添加部门并设置父级列表.
     *
     * @param deptDTO 添加对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addDept(final SysDeptDTO deptDTO) {
        final SysDept dept = BeanUtil.toBean(deptDTO, SysDept.class);
        // 获取父部门信息
        final SysDept parent = getBaseMapper().selectOne(
                Wrappers.<SysDept>lambdaQuery()
                        .eq(SysDept::getId, dept.getParentId())
                        .select(SysDept::getId, SysDept::getParentList));
        dept.setParentList(parent.getParentList() + parent.getId() + ",");
        final Boolean isSave = save(dept);
        dept.setParentList(dept.getParentList() + dept.getId() + ",");
        final Boolean isUpdate = updateById(dept);
        return isSave && isUpdate;
    }


    @Override
    public final Boolean checkDeptUnique(final SysDeptDTO deptDTO) {
        return getOne(
                Wrappers.<SysDept>lambdaQuery()
                        .eq(SysDept::getParentId, deptDTO.getParentId())
                        .eq(SysDept::getDeptName, deptDTO.getDeptName())
        )
                != null;
    }

    @Override
    public final List<SysDept> selectDeptChildren(final Long parentId) {
        return getBaseMapper().listDeptChildren(parentId);
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
        final List<SysDept> list = selectDeptChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (final SysDept dept : list) {
                if (Boolean.FALSE.equals(deleteById(dept.getId()))) {
                    isDeleted = false;
                }
            }
        }
        return removeById(id) && isDeleted;
    }

    @Override
    public final List<SysDept> selectDeptListByUserId(final Long userId) {
        return getBaseMapper().listDeptListByUserId(userId);
    }

    @Override
    public final Set<Long> selectDeptIdsByUserId(final Long userId) {
        return getBaseMapper().getDeptIdsByUserId(userId);
    }

    @Override
    public final Boolean setUserDept(
            final Long userId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getBaseMapper().getDeptIdsByUserId(userId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess = result.isEmpty() || getBaseMapper().deleteUserDept(userId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().insertUserDept(userId, result) == result.size());
    }

}
