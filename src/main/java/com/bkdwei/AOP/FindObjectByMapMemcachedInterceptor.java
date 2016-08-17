/**
 *
 */
package com.bkdwei.AOP;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bkdwei.core.memcached.MemcachedUtils;

import net.spy.memcached.KeyUtil;
import net.spy.memcached.MemcachedClientIF;

/**
 * @author bkd
 * @Date 2016年8月9日
 */
public class FindObjectByMapMemcachedInterceptor {
    private static Logger logger = LoggerFactory.getLogger(FindObjectByMapMemcachedInterceptor.class);

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
            value = "findObjectByMap(map)")
    public Object doFindUser(final ProceedingJoinPoint joinPoint, final Map map) throws Throwable {
        Object obj = null;
        final StringBuffer key = new StringBuffer();
        key.append("findObjectByMap_");

        final java.util.Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            final java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            key.append(entry.getKey() + "_" + entry.getValue());
        }
        final String sKey = key.toString();
        validateKey(sKey);
        obj = MemcachedUtils.MEMCACHED_CLIENT.get(sKey);
        System.out.println("key:" + key.toString());
        if (obj != null) {
            logger.debug("从缓存中读取对象：" + map.toString());
            return obj;
        } else {
            obj = joinPoint.proceed();
            if (obj != null) {
                MemcachedUtils.set(sKey, obj, 1200000);
                logger.debug("从数据库中获取记录。并已将" + sKey + "存入缓存");
            }
            return obj;
        }
    }

    @Pointcut("execution(* com.bkdwei.*.StudentService.*(..))&&args(map)")
    public void findObjectByMapPoincut(final Map map) {
    }
}
