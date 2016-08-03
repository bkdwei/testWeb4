package com.bkdwei.core;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T, PK extends Serializable> {

    public void delete(int id);

    public List<T> findAll();

    public T findById(final int id);

    public void save(final T entity);

    public void saveOrUpdate(final T entity);

    public void update(final T entity);

}
