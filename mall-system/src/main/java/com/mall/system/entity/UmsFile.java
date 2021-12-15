package com.mall.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件.
 *
 * @author 钟舒艺
 */
@Data
@TableName(value = "ums_file")
@EqualsAndHashCode(callSuper = true)
public class UmsFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id.
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 储存文件名(UUID).
     */
    private String name;

    /**
     * 原始文件名.
     */
    private String originalName;

    /**
     * 文件后缀名.
     */
    private String postfix;

    /**
     * 文件路径.
     */
    private String path;

    /**
     * 文件大小.
     */
    private Long size;

    /**
     * 业务类型.
     */
    private Integer businessType;

    /**
     * 上传者id.
     */
    private Long uploadById;
}