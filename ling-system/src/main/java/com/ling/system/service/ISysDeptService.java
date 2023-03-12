package com.ling.system.service;

import com.ling.common.core.domain.model.SysDept;
import com.ling.common.core.mybatisplus.core.IServicePlus;
import com.ling.system.dto.SysDeptDTO;
import com.ling.system.vo.SysDeptVO;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 部门表 服务类.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
public interface ISysDeptService extends IServicePlus<SysDept, SysDeptVO, SysDeptDTO>, Serializable {

    /**
     * 添加部门.
     *
     * @param deptDTO 添加对象
     * @return 是否成功
     */
    Boolean addDept(SysDeptDTO deptDTO);

    /**
     * 检查同一级部门下是否有相同名称部门.
     *
     * @param deptDTO 新增部门对象
     * @return 是否存在
     */
    Boolean checkDeptUnique(SysDeptDTO deptDTO);

    /**
     * 获取子部门列表.
     *
     * @param parentId 父级id
     * @return 子级列表
     */
    List<SysDept> selectDeptChildren(Long parentId);

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
    List<SysDept> selectDeptListByUserId(Long userId);

    /**
     * 根据用户id查询部门id.
     *
     * @param userId 用户id
     * @return 部门id
     */
    Set<Long> selectDeptIdsByUserId(Long userId);

    /**
     * 设置用户的部门.
     *
     * @param userId  用户id.
     * @param deptIds 部门id
     * @return 是否成功
     */
    Boolean setUserDept(
            Long userId,
            Set<Long> deptIds
    );
}
