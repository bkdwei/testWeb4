/**
 *
 */
package com.bkdwei.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bkd
 * @Date 2016年8月3日
 */
@Aspect

public class PerformanceLog {
    private static Logger logger = LoggerFactory.getLogger(PerformanceLog.class);

    /* @Pointcut("execution(* com.bkdwei..*Service.*(..))") */
    public void daoPerformanceLog() {
    }

    /* @Pointcut("execution(* com.bkdwei..*Service.*(..))&& args(name)") */
    public void daoPerformanceLog(final Object[] name) {
    }

    @Before("daoPerformanceLog()")
    public void infoMe() {
        System.out.println("in infoMe method.");
    }

    @Around("daoPerformanceLog(name)")
    public void logElapsedTime(final ProceedingJoinPoint joinPoint, final Object[] name)
            throws Throwable {
        final StringBuffer performanceInfo = new StringBuffer();
        final long start = System.currentTimeMillis();
        joinPoint.proceed(name);
        performanceInfo.append("类:" + joinPoint.getTarget().getClass().getName()).append("方法:")
                .append(joinPoint.getSignature().getName()).append("耗时 ")
                .append(System.currentTimeMillis() - start).append("毫秒");
        System.out.println(performanceInfo.toString());
        PerformanceLog.logger.debug(performanceInfo.toString());
    }
}
