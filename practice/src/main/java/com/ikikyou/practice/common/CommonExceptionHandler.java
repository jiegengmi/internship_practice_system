//package com.ikikyou.practice.common;
//
//import com.ikikyou.practice.utils.Result;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 统一异常处理
// * @author ikikyou
// * @date 2023/03/21 10:12
// */
//@ControllerAdvice
//@RestController
//public class CommonExceptionHandler {
//
//    /**
//     * 拦截自定义异常
//     */
//    @ExceptionHandler(BusinessException.class)
//    @ResponseBody
//    public Result<Object> exceptionHandler(BusinessException exception){
//        exception.printStackTrace();
//        return Result.fail("发生异常");
//    }
//
//
//    /**
//     * 一般异常
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result<Object> exceptionHandler(Exception e){
//        e.printStackTrace();
//        return Result.fail("发生异常");
//    }
//}
