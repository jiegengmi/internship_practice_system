package com.ikikyou.practice.common;

import com.ikikyou.practice.utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * @author ikikyou
 * @date 2023/03/21 10:12
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * 一般异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception exception){
        exception.printStackTrace();
        if (exception instanceof AccessDeniedException) {
            return Result.fail(403,"您没有权限访问");
        } else if (exception instanceof NullPointerException) {
            return Result.fail(500, "空指针异常");
        } else if (exception instanceof BusinessException) {
            return Result.fail(500, "业务异常" + exception.getMessage());
        }
        return Result.fail("发生异常");
    }
}
