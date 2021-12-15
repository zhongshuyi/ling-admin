package com.mall.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 公共类.
 *
 * @author zsy
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者.
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间.
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者.
     */
    private String updateBy;

    /**
     * 更新时间.
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注.
     */
    private String remark;
}
