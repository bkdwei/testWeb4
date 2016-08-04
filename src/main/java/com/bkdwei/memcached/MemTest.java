/**
 *
 */
package com.bkdwei.memcached;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkdwei.daoTest.UserService;
import com.bkdwei.model.User;

/**
 * 测试Memcached
 *
 * @author bkd
 * @Date 2016年8月4日
 */
@Controller
@RequestMapping("/mem")
public class MemTest {

    @Autowired
    private UserService userService;

    public Object findObject(final String table, final HashMap<String, String> condition) {
        final StringBuffer key = new StringBuffer();
        key.append(table).append(":");
        for (final Map.Entry<String, String> m : condition.entrySet()) {
            key.append(m.getKey()).append("+").append(m.getValue());
        }
        return MemcachedUtils.MEMCACHED_CLIENT.get(key.toString());
    }

    @RequestMapping("/test")
    public void testCache() throws Exception {
        MemcachedUtils.set("name", "bkdwei", 20000);
        System.out.println("get name form memcached:" + MemcachedUtils.get("name"));
        MemcachedUtils.set("user", userService.findUserByName("bkdwei"), 20000);

        final User user = (User) MemcachedUtils.get("user");

        System.out.println(user.getName());
        MemcachedUtils.set("userList", userService.findAll(), 20000);
        System.out.println((MemcachedUtils.get("userList")).getClass().getTypeName());

        @SuppressWarnings("unchecked")
        final List<User> users = (List<User>) MemcachedUtils.get("userList");
        System.out.println(users.get(0).getName());

    }
}
