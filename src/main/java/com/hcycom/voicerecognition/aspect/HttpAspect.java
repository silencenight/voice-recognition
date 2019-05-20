package com.hcycom.voicerecognition.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *记录所有HTTP请求
 *
 * 使用@Aspect注解声明一个切面
 * @author Silence
 * @create 2019-05-13 18:01
 */
@Aspect
@Component
@Slf4j
public class HttpAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    /**
     * 定义切入点
     *通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     * 第一个*，返回类型不限
     * 第二个*，表示所有类
     * 第三个*，表示所有方法
     * ..表示方法里的参数不限
     *
     * 也可以通过注解的形式：@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
     */
    @Pointcut("execution(public  * com.hcycom.voicerecognition.controller.*.*(..))")
    public void httpLog(){}

    /**
     * 前置通知，方法调用前执行
     * @param joinPoint
     */
    @Before("httpLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        startTime.set(System.currentTimeMillis());

        //获取RequestAttributes
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();
        //如果要获取Session信息的话，可以这样写：
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);

        //记录请求内容
        log.info("【URL】 - {}", httpServletRequest.getRequestURL());
        log.info("【HTTP_METHOD】 - {}",httpServletRequest.getMethod());
        log.info("【IP】 - {}",httpServletRequest.getRemoteAddr());
        log.info("【CLASS_METHOD】 - {}", joinPoint.getSignature().getDeclaringTypeName() +"."+ joinPoint.getSignature().getName());
        log.info("【ARGS】 - {}", joinPoint.getArgs());
    }


    /**
     * 后置返回通知
     * @param object
     */
    @AfterReturning(returning = "object",pointcut = "httpLog()")
    public void afterReturning(Object object){
        //http请求返回结果
        log.info("【RESPONSE】 - {}",object.toString());
        log.info("【SPEND TIME】 - 【" + (System.currentTimeMillis() - startTime.get()) + "】 milliseconds.");
    }
}
