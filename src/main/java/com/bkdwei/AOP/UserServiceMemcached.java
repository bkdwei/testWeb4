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

import com.bkdwei.memcached.MemcachedUtils;
import com.bkdwei.model.User;

import net.spy.memcached.KeyUtil;
import net.spy.memcached.MemcachedClientIF;

/**
 * 拦截service获取获取用户的方法，先从cache中查找，找不到再到数据库查找并存入缓存
 *
 * @author bkd
 * @Date 2016年8月4日
 */
@Component
@Aspect
public class UserServiceMemcached {
    private static Logger logger = LoggerFactory.getLogger(PerformanceLog.class);

    /* 验证key是否符合memcached的要求 */
    public static void validateKey(final String key) {
        /* key的最大长度是250个字符 */
        final byte[] keyBytes = KeyUtil.getKeyBytes(key);
        if (keyBytes.length > MemcachedClientIF.MAX_KEY_LENGTH) {
            throw new IllegalArgumentException(
                    "Key is too long (maxlen = " + MemcachedClientIF.MAX_KEY_LENGTH + ")");
        }
        if (keyBytes.length == 0) {
            throw new IllegalArgumentException("Key must contain at least one character.");
        }
        /* key 不能有空格和控制字符 */
        for (final byte b : keyBytes) {
            if (b == ' ' || b == '\n' || b == '\r' || b == 0) {
                throw new IllegalArgumentException(
                        "Key contains invalid characters:  ``" + key + "''");
            }
        }
    }

    @Around(
            value = "userServicePoincut(name)")
    public User doFindUser(final ProceedingJoinPoint joinPoint, final String name)
            throws Throwable {
        User user = null;
        final StringBuffer key = new StringBuffer();
        key.append("findUserByName_").append(name);
        final String sKey = key.toString();
        validateKey(sKey);
        /*
         * for (final Map.Entry<String, String> m : condition.entrySet()) {
         * key.append(m.getKey()).append("+").append(m.getValue());
         * }
         */
        user = (User) MemcachedUtils.MEMCACHED_CLIENT.get(sKey);
        System.out.println("key:" + key.toString());
        if (user != null) {
            logger.debug("从缓存中读取User对象。用户名为：" + name);
            return user;
        } else {
            user = (User) joinPoint.proceed();
            if (user != null) {
                MemcachedUtils.set(sKey, user, 1200000);
                logger.debug("从数据库中获取记录。并已将" + sKey + "存入缓存");
            }
            return user;
        }
    }

    @Pointcut("execution(* com.bkdwei.*.*Service.*(..))&&args(name)")
    public void userServicePoincut(final String name) {
    }
}
