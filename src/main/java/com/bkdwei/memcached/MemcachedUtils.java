/**
 *
 */
package com.bkdwei.memcached;

import java.util.List;

import org.springframework.stereotype.Component;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

/**
 *
 * @author bkd
 * @Date 2016年8月4日
 */
@Component
public class MemcachedUtils {
    public static MemcachedClient MEMCACHED_CLIENT;

    static {
        try {
            /* 在本地安装好后，用memcached -p 11211 -m 64m -d开启11211端口监听 */
            MEMCACHED_CLIENT = new MemcachedClient(AddrUtil.getAddresses("localhost:11211"));
        } catch (final Exception e) {
            System.err.println("Cannot init MEMCACHED Memcached Client");
        }
    }

    public static Object get(final String key) throws Exception {
        final Object value = MEMCACHED_CLIENT.get(key);
        if (null != value) {
            return value;
        }
        return null;
    }

    public static List<?> getList(final String tableName) {
        final List<?> value = (List<?>) MEMCACHED_CLIENT.get(tableName);
        return value;
    }

    /*
     * public static T getObject(final String tableName，final Object[] params){
     * List list = getList(tableName);
     * }
     */
    public static void set(final String key, final Object value, final int expireTime)
            throws Exception {
        MEMCACHED_CLIENT.set(key, expireTime, value);
    }
}
