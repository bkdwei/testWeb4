package com.bkdwei.testCase.baseDao;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkdwei.core.BaseService;
import com.bkdwei.model.User;

@Service
public class UserService extends BaseService<User> {

    @Autowired
    private UserDao userDao;

    public User findUserByName(final String name) {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        return userDao.findObjectByMap(map);
    }
}
