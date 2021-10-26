package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.core.domain.entity.UmsDept;
import com.mall.system.bo.add.DeptAddBo;
import com.mall.system.mapper.UmsDeptMapper;
import com.mall.system.service.IUmsDeptService;
import com.mall.system.vo.DeptVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门服务实现类
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
@Service
public class UmsDeptServiceImpl extends ServiceImpl<UmsDeptMapper, UmsDept> implements IUmsDeptService {

    @Override
    public List<DeptVo> getDeptVoList() {
        return build(list(), 0L);
    }

    @Override
    public Boolean addDept(DeptAddBo addBo) {
        UmsDept dept = BeanUtil.toBean(addBo, UmsDept.class);
        return save(dept);
    }


    @Override
    public Boolean checkDeptUnique(DeptAddBo addBo) {
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

    /**
     * 构建层级关系
     *
     * @param list     原始数据
     * @param parentId 父级id
     * @return 属于该父级的子级列表
     */
    private List<DeptVo> build(List<UmsDept> list, Long parentId) {
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        List<DeptVo> voList = new ArrayList<>();
        for (UmsDept dept : list) {
            if (!dept.getParentId().equals(parentId)) {
                continue;
            }
            DeptVo vo = BeanUtil.toBean(dept, DeptVo.class);
            vo.setChildren(build(list, dept.getId()));
            if (CollUtil.isEmpty(vo.getChildren())) {
                vo.setChildren(null);
            }
            voList.add(vo);
        }
        return voList;
    }

}
