/**
 *
 */
package com.bkdwei.AOP.introduction;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * 定义引入增强的切面，给UserService新增功能，在TestUser中演示
 *
 * @author bkd
 * @Date 2016年8月5日
 */
@Aspect
@Component
public class UserServiceIntroductionAdvice {
    @DeclareParents(
            value = "com.bkdwei.daoTest.UserService", defaultImpl = SayHelloImp.class)
    private ISayHello sayHello;
}
