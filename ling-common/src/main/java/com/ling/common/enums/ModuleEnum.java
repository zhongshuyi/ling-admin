package com.ling.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块枚举.
 *
 * @author 钟舒艺
 * @since 2023-03-09 14:31
 **/
@Getter
@AllArgsConstructor
public enum ModuleEnum {

    /**
     * 系统模块.
     */
    SYSTEM_MODULE("sys", "系统模块");


    /**
     * 模块代码.
     */
    private final String code;

    /**
     * 模块名.
     */
    private final String moduleName;
}
