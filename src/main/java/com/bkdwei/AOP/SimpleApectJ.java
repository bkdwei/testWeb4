/**
 *
 */
package com.bkdwei.AOP;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
@Component
@Aspect
public class SimpleApectJ {
    @Around("execution(* com.bkdwei.*.*Service.*(..))&&args(name)")
    public void infoUserName(final String name) {
        System.out.println("in SimpleApectJ class,the username is " + name);
    }
}
