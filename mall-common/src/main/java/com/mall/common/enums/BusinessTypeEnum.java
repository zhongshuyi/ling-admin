package com.mall.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型枚举.
 *
 * @author 钟舒艺
 **/
@Getter
@AllArgsConstructor
public enum BusinessTypeEnum {

    /**
     * 用户头像业务.
     */
    USER_AVATAR(0, "用户头像业务,用于标识头像文件");


    /**
     * 代码.
     */
    private final int code;
    /**
     * 备注.
     */
    private final String remark;

}
