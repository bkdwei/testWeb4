package com.bkdwei.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

    @SuppressWarnings("unchecked")
    public List<T> findListByMap(final Map map) {
        final Criteria criteria = getCriteriaByMap(map);
        final List<T> result = criteria.list();
        if (result.size() == 0) {
            return null;
        }
        return result;

    }

    /*
     * (non-Javadoc)
     * @see com.bkdwei.core.IBaseDao#findObjectByMap(java.util.Map)
     */
    public T findObjectByMap(final Map map) {
        final List<T> list = findListByMap(map);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * @function 分页显示符合所有的记录数，将查询结果封装为Pager
     * @param pageNo
     *            当前页数
     * @param pageSize
     *            每页显示的条数
     * @param map
     *            将查询条件封装为map
     * @return 查询结果Pager
     */
    public Pager findPageByCriteria(final int pageNo, final int pageSize, final Map map) {
        Pager pager = null;
        try {
            final Criteria criteria = getCriteriaByMap(map);

            // 获取根据条件分页查询的总行数
            final int rowCount = (Integer) criteria.setProjection(Projections.rowCount())
                    .uniqueResult();
            criteria.setProjection(null);

            criteria.setFirstResult((pageNo - 1) * pageSize);
            criteria.setMaxResults(pageSize);

            final List result = criteria.list();

            pager = new Pager(pageSize, pageNo, rowCount, result);

        } catch (final RuntimeException re) {
            throw re;
        } finally {
            return pager;
        }

    }

    protected Criteria getCriteriaByMap(final Map map) {
        Criteria criteria = null;
        try {
            criteria = getSession().createCriteria(Class.forName(clazz.getCanonicalName()));
            if (map != null) {
                final Set<String> keys = map.keySet();
                for (final String key : keys) {
                    criteria.add(Restrictions.eq(key, map.get(key)));
                }
            }
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
        return criteria;
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
