package com.bkdwei.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseService<T, PK extends Serializable> {
    public void delete(int id);

    public List<T> findAll();

    public T findById(final int id);

    public List<T> findListByMap(final Map map);

    public T findObjectByMap(final Map map);

    public void save(final T entity);

    public void saveOrUpdate(final T entity);

    public void update(final T entity);
}
