package com.hystrix.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 处理订单服务调用支付服务失败后的服务降级提示信息
 */
@Aspect
@Component
public class HystrixOrderAspect {
    // 配置切入点表达式
    @Pointcut("execution(* com.hystrix.service.HystrixOrderFallbackService.*(..))")
    public void pointCut() {
    }


    // 环绕通知
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        // 类型转换，向下转型，必定成功，因为其内部的实现MethodSignatureImpl实现的就是MethodSignature接口
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        if (String.class.isAssignableFrom(methodSignature.getReturnType()) && result == null) {
            return String.format("[%s] fallback [%s]", proceedingJoinPoint.getTarget().getClass().getName(),
                    methodSignature.getName());
        }

        return result;
    }
}
