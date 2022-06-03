package com.ling.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限注解.
 *
 * @author 钟舒艺
 * @date 2022-05-21-20:33
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 用户标识列名.
     *
     * @return 用户标识列名
     */
    String userIdColumnName() default "user_id";

    /**
     * 部门标识列名.
     *
     * @return 部门标识列名
     */
    String deptIdColumnName() default "dept_id";

}
