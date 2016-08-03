package com.bkdwei.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T, PK extends Serializable> implements IBaseDao<T, PK> {

    protected Class<T>     clazz;

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        // 当前对象的直接超类的 Type
        final Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            // 参数化类型
            final ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            // 返回表示此类型实际类型参数的 Type 对象的数组
            final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.clazz = (Class<T>) actualTypeArguments[0];
        } else {
            this.clazz = (Class<T>) genericSuperclass;
        }
    }

    public void delete(final int id) {
        final Object entity = findById(id);
        if (entity != null) {
            getSession().delete(entity);
        }

    }

    @SuppressWarnings("unchecked")

    public List<T> findAll() {
        return getSession().createQuery("from " + clazz.getSimpleName()).list();

    }

    @SuppressWarnings("unchecked")

    public T findById(final int id) {
        return (T) getSession().get(clazz, id);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(final T entity) {
        getSession().save(entity);
    }

    public void saveOrUpdate(final T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void update(final T entity) {
        getSession().update(entity);

    }

}
