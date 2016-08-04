package com.bkdwei.daoTest;

import java.io.Serializable;

import com.bkdwei.core.IBaseService;
import com.bkdwei.model.User;

public interface IUserService extends IBaseService<User, Serializable> {
    public User findUserByName(String name);
}
