package com.mall.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录信息
 * @author 钟舒艺
 * @date 2021-07-07-21:56
 **/
@Data
@ApiModel(description = "用户登录对象")
public class LoginBody {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required=true)
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码",required=true)
    private String password;
}
