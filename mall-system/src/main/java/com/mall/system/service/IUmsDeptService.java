package com.mall.system.service;

import com.mall.common.core.domain.entity.UmsDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.system.bo.add.DeptAddBo;
import com.mall.system.vo.DeptVo;

import java.util.List;

/**
 * 部门表 服务类
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
public interface IUmsDeptService extends IService<UmsDept> {

    /**
     * 获取层级部门列表
     * @return 部门列表
     */
    List<DeptVo> getDeptVoList();

    /**
     * 添加部门
     * @param addBo 添加对象
     * @return 是否成功
     */
    Boolean addDept(DeptAddBo addBo);

    /**
     * 检查同一级部门下是否有相同名称部门
     * @param addBo 新增部门对象
     * @return 是否存在
     */
    Boolean checkDeptUnique(DeptAddBo addBo);

    /**
     * 获取子部门列表
     * @param parentId 父级id
     * @return 子级列表
     */
    List<UmsDept> getDeptChildren(Long parentId);

    /**
     * 根据id删除部门
     * @param id 部门id
     * @return 是否成功
     */
    Boolean deleteById(Long id);

    /**
     * 根据用户id获取部门列表
     * @param userId 用户id
     * @return 部门列表
     */
    List<UmsDept> getDeptListByUserId(Long userId);
}
