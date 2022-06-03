package com.ling.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiOperation;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配置一个权限可以访问多个路径.
 *
 * @author zsy
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_permission_url")
public class SysPermissionUrl implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键id.
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 菜单id.
     */
    private Long menuId;
    /**
     * 接口描述, {@link ApiOperation}的值.
     */
    private String description;
    /**
     * 后端接口的访问路径.
     */
    private String url;
    /**
     * 使用的请求方式.
     */
    private String method;
}
