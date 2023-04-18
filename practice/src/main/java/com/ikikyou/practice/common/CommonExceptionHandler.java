package com.ikikyou.practice.common;

import com.ikikyou.practice.utils.Result;
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
     * 拦截自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> exceptionHandler(BusinessException exception){
        exception.printStackTrace();
        return Result.fail(exception.getMessage());
    }


    /**
     * 一般异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception exception){
        exception.printStackTrace();
        return Result.fail("发生异常");
    }
}
