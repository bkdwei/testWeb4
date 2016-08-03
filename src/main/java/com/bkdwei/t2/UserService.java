package com.bkdwei.t2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkdwei.core.BaseService;
import com.bkdwei.model.User;

@Service
public class UserService extends BaseService<User> implements IUserService {

    @Autowired
    private IUserDao userDao;

    public User findUserByName(final String name) {
        return userDao.findUserByName(name);
    }

    public IUserDao getUserDao() {
        return userDao;
    }
}
