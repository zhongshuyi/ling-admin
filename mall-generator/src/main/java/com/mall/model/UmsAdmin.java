package com.mall.model;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.*;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UmsAdmin对象", description="用户信息表")
public class UmsAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String realName;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String tel;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private Integer sex;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "首页路径")
    private String homePath;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private Integer delFlag;

    @ApiModelProperty(value = "最后登录IP")
    private Integer loginIp;

    @ApiModelProperty(value = "最后登录时间")
    private Date loginDate;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

}
