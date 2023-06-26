package com.ikikyou.practice.common.exception;

import com.ikikyou.practice.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 * @author ikikyou
 * @date 2023/03/21 10:12
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * 一般异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception exception){
        exception.printStackTrace();
        if (exception instanceof AccessDeniedException) {
            return Result.fail(HttpStatus.FORBIDDEN.value(),"您没有权限访问");
        } else if (exception instanceof MethodArgumentNotValidException) {
            //校验异常
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            return Result.fail(allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(";")));
        } else if (exception instanceof NullPointerException) {
            return Result.fail("传递异常");
        } else if (exception instanceof BusinessException) {
            log.warn("系统自定义异常:{}", exception.getMessage());
            return Result.fail("业务异常");
        } else {
            return Result.fail("发生异常!");
        }
    }
}
