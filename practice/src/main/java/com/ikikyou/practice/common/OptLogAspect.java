package com.ikikyou.practice.common;

import com.alibaba.fastjson2.JSON;
import com.ikikyou.practice.annotation.OptLog;
import com.ikikyou.practice.entity.SysLog;
import com.ikikyou.practice.mapper.SysLogMapper;
import com.ikikyou.practice.utils.IpUtil;
import com.ikikyou.practice.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 操作日记录
 * @author hongx
 * @date 2023/03/30 11:48
 */
@Aspect
@Component
public class OptLogAspect {

    @Resource
    private SysLogMapper logMapper;
    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.ikikyou.practice.annotation.OptLog)")
    public void optLogPointCut() {}


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "optLogPointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public void saveOptLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        SysLog operationLog = new SysLog();
        operationLog.setId(System.currentTimeMillis());
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        OptLog optLog = method.getAnnotation(OptLog.class);
        // 操作模块
        operationLog.setModule(api.tags()[0]);
        // 操作类型
        operationLog.setType(optLog.optType().getType());
        // 操作描述
        operationLog.setContent(apiOperation.value());
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 请求方法
        operationLog.setOpMethod(methodName);
        // 请求参数
        operationLog.setOpParams(JSON.toJSONString(joinPoint.getArgs()));
        // 返回结果
        operationLog.setResult(JSON.toJSONString(keys));
        // 请求用户ID
        operationLog.setUserId(SecurityUtil.getUserId());
        // 请求用户
        operationLog.setUserNickName(SecurityUtil.getBaseUser().getNickName());
        // 请求IP
        String ipAddress = IpUtil.getIpAddress(request);
        operationLog.setUserIp(ipAddress);
        operationLog.setUserIpSource(IpUtil.getIpSource(ipAddress));
        // 请求URL
        operationLog.setOpUrl(request.getRequestURI());
        logMapper.insert(operationLog);
    }

}
