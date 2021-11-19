package com.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.system.entity.UmsDept;
import com.mall.system.bo.DeptBo;
import java.io.Serializable;
import java.util.List;

/**
 * 部门表 服务类.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
public interface IUmsDeptService extends IService<UmsDept>, Serializable {

    /**
     * 添加部门.
     *
     * @param addBo 添加对象
     * @return 是否成功
     */
    Boolean addDept(DeptBo addBo);

    /**
     * 检查同一级部门下是否有相同名称部门.
     *
     * @param addBo 新增部门对象
     * @return 是否存在
     */
    Boolean checkDeptUnique(DeptBo addBo);

    /**
     * 获取子部门列表.
     *
     * @param parentId 父级id
     * @return 子级列表
     */
    List<UmsDept> getDeptChildren(Long parentId);

    /**
     * 根据id删除部门.
     *
     * @param id 部门id
     * @return 是否成功
     */
    Boolean deleteById(Long id);

    /**
     * 根据用户id获取部门列表.
     *
     * @param userId 用户id
     * @return 部门列表
     */
    List<UmsDept> getDeptListByUserId(Long userId);
}
