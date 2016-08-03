package com.bkdwei.core;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> implements IBaseService<T, Serializable> {

    @Autowired
    private IBaseDao<T, Serializable> baseDao;

    public void delete(final int id) {
        baseDao.delete(id);
    }

    public List<T> findAll() {
        return baseDao.findAll();

    }

    public T findById(final int id) {
        return baseDao.findById(id);
    }

    public void save(final T entity) {
        baseDao.save(entity);
    }

    public void saveOrUpdate(final T entity) {
        baseDao.saveOrUpdate(entity);
    }

    public void update(final T entity) {
        baseDao.update(entity);
    }

}
