package com.ikikyou.practice.annotation;

import com.ikikyou.practice.emun.LogEnum;

import java.lang.annotation.*;

/**
 * @author hongx
 * @date 2023/03/30 11:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {
    /**
     * @return 操作类型
     */
    LogEnum optType() default LogEnum.DEFAULT;
}
