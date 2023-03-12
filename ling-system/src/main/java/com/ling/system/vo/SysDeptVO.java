package com.ling.system.vo;

import com.ling.common.core.domain.base.BaseVO;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门信息vo.
 *
 * @author 钟舒艺
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id.
     */
    private Long id;

    /**
     * 父id.
     */
    private Long parentId;

    /**
     * 部门名称.
     */
    private String deptName;

    /**
     * 显示顺序.
     */
    private Integer orderNo;

    /**
     * 负责人.
     */
    private String leader;

    /**
     * 联系电话.
     */
    private String phone;

    /**
     * 邮箱.
     */
    private String email;

    /**
     * 父级列表.
     */
    private String parentList;

    /**
     * 子级部门.
     */
    private List<SysDeptVO> children;
}
