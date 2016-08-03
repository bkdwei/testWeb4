package com.bkdwei.t2;

import java.io.Serializable;

import com.bkdwei.core.IBaseDao;
import com.bkdwei.model.User;

public interface IUserDao extends IBaseDao<User, Serializable> {
    public User findUserByName(String name);
}
