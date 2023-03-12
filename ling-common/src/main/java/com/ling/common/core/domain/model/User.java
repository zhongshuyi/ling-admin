package com.ling.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ling.common.core.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户类.
 *
 * @author 钟舒艺
 * @since 2022-10-27 14:38
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseEntity {

    private static final long serialVersionUID = 844732306134765370L;


    /**
     * 用户名.
     */
    private String username;

    /**
     * 密码.
     */
    private String password;


    /**
     * 用户状态.
     */
    private Byte status;


}
