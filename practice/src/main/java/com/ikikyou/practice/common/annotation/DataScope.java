package com.ikikyou.practice.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * @author ikikyou
 * @date 2023/04/21 16:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门表的别名
     */
    String deptAlias() default "";

    /**
     * 用户表的别名
     */
    String userAlias() default "";

    /**
     * 权限字符（用于多个角色匹配符合要求的权限）默认根据权限注解@ss获取，多个权限用逗号分隔开来
     */
    String permission() default "";
}
