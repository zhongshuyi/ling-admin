package com.mall.system.bo;

import java.io.Serializable;
import lombok.Data;

/**
 * 部门角色Bo.
 *
 * @author 钟舒艺
 **/
@Data
public class DeptRoleBo implements Serializable {


    private static final long serialVersionUID = -4714617053737228787L;


    /**
     * 部门id.
     */
    private Long deptId;

    /**
     * 部门角色名.
     */
    private String roleName;
    

}
