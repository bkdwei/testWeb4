package com.bkdwei.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLog implements ThrowsAdvice {

    private static Logger logger = LoggerFactory.getLogger(ExceptionLog.class);

    /**
     * Owner 参数解释
     * Method method 执行的方法
     * Object[] args 方法参数
     * Object target 代理的目标对象
     * Throwable throwable 产生的异常
     */
    @AfterThrowing(
            pointcut = "daoAspect()", throwing = "e")
    public void afterThrowingDaoException(final JoinPoint joinPoint, final Throwable e) {
        commonLoger(joinPoint, e, "dao异常。");
    }

    @AfterThrowing(
            pointcut = "serviceAspect()", throwing = "e")
    public void afterThrowingServiceException(final JoinPoint joinPoint, final Throwable e) {
        commonLoger(joinPoint, e, "service异常。");
    }

    private void commonLoger(final JoinPoint joinPoint, final Throwable e,
            final String additionMsg) {
        ExceptionLog.logger.error(additionMsg);

        //获取用户请求方法的参数
        final StringBuffer params = new StringBuffer();
        final Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (final Object arg : args) {
                params.append(arg.toString()).append(",");
            }
        }
        //获取堆栈信息
        final StringBuffer stackTrace = new StringBuffer();
        final StackTraceElement[] trace = e.getStackTrace();
        for (final StackTraceElement s : trace) {
            stackTrace.append(s).append("\n");
        }

        final StringBuilder fullExceptionMsg = new StringBuilder();
        fullExceptionMsg.append("异常类:" + joinPoint.getTarget().getClass().getName())
                .append(";\t异常方法名称:").append(joinPoint.getSignature().getName())
                .append("\t参数:" + params.toString()).append("\n抛出的异常:" + e.getMessage())
                .append("\n异常详细信息：" + e.fillInStackTrace())
                .append("\n堆栈详细信息:\n" + stackTrace.toString());

        ExceptionLog.logger.error(fullExceptionMsg.toString());
    }

    @Pointcut("execution(* com.bkdwei..*Dao.*(..))")
    public void daoAspect() {
    }

    @Pointcut("execution(* com.bkdwei..*Service.*(..))")
    public void serviceAspect() {
    }
}
