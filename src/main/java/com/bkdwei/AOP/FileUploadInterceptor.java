/**
 *
 */
package com.bkdwei.AOP;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
@Component
public class FileUploadInterceptor implements MethodBeforeAdvice {

    /*
     * (non-Javadoc)
     * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.
     * Method, java.lang.Object[], java.lang.Object)
     */
    public void before(final Method method, final Object[] args, final Object target)
            throws Throwable {
        System.out.println("[DaoMethodBeforeAdvice]:" + method + " : " + ":" + target);
        System.out.println("good");
    }

}
