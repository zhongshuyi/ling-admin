package com.ling.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.time.LocalDateTime;
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
     * 主键id.
     */
    @TableId
    private Serializable id;

    /**
     * 删除标志（0 代表未删除， 1 代表删除）.
     */
    @TableLogic
    private Byte isDeleted;

    /**
     * 创建者.
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间.
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者.
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间.
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注.
     */
    private String remark;
}
