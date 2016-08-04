package com.bkdwei.daoTest;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bkdwei.core.BaseDao;
import com.bkdwei.model.User;

@Repository
public class UserDao extends BaseDao<User, Serializable> implements IUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public User findUserByName(final String name) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        final List<User> list = criteria.list();
        return list.get(0);
    }

}
