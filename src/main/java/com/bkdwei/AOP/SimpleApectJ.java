/**
 *
 */
package com.bkdwei.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.bkdwei.model.User;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
@Component
@Aspect
public class SimpleApectJ {
    @Around("execution(* com.bkdwei.*.UserService.*(..))&&args(name)")
    public User infoUserName(final ProceedingJoinPoint joinPoint, final String name)
            throws Throwable {
        System.out.println("in SimpleApectJ class,the username is " + name);
        return (User) joinPoint.proceed();
    }
}
