/**
 *
 */
package com.bkdwei.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author bkd
 * @Date 2016年8月3日
 */
@Aspect
@Component
public class PerformanceLog {
    private static Logger logger = LoggerFactory.getLogger(PerformanceLog.class);

    @Around("logPerformanceTime()")
    public Object logElapsedTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        final StringBuffer performanceInfo = new StringBuffer();
        final long start = System.currentTimeMillis();
        final Object o = joinPoint.proceed();
        performanceInfo.append("类：" + joinPoint.getTarget().getClass().getName()).append("，方法：")
                .append(joinPoint.getSignature().getName()).append("，耗时 ")
                .append(System.currentTimeMillis() - start).append("毫秒");
        System.out.println(performanceInfo.toString());
        PerformanceLog.logger.debug(performanceInfo.toString());
        return o;
    }

    /* 测试业务层的方法的运行时间 */
    @Pointcut("execution(* com.bkdwei..*Service.*(..))")
    public void logPerformanceTime() {
    }
}
