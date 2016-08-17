package com.bkdwei.testCase.baseDao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.bkdwei.core.BaseDao;
import com.bkdwei.model.User;

@Repository
public class UserDao extends BaseDao<User, Serializable> {

}
