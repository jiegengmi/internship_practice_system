package com.ikikyou.practice.common.annotation;

import com.ikikyou.practice.enums.LogEnums;

import java.lang.annotation.*;

/**
 * @author ikikyou
 * @date 2023/03/30 11:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {
    /**
     * @return 操作类型
     */
    LogEnums optType() default LogEnums.DEFAULT;
}
