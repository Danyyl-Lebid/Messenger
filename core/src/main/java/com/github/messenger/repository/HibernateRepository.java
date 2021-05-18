package com.github.messenger.repository;

import com.github.messenger.utils.HibernateSessionManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

public class HibernateRepository<T> implements IRepository<T> {

    private final Class<T> type;

    private final HibernateSessionManager sessionManager;

    public HibernateRepository(Class<T> type, HibernateSessionManager sessionManager) {
        this.type = type;
        this.sessionManager = sessionManager;
    }


    @Override
    public Collection<T> findAll() {
        try (Session session = sessionManager.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(type);
            criteria.from(type);
            return session.createQuery(criteria).getResultList();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findBy(String field, Object value) {
        try (Session session = sessionManager.getSession()) {
            Criteria criteria = session.createCriteria(type);
            return (T) criteria.add(Restrictions.eq(field, value)).uniqueResult();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<T> findAllBy(String field, Object value) {
        try (Session session = sessionManager.getSession()) {
            Criteria criteria = session.createCriteria(type);
            return (Collection<T>) criteria.add(Restrictions.eq(field, value)).list();
        }
    }

    @Override
    public void save(T entity) {
        try (Session session = sessionManager.getSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(entity);
            tx1.commit();
        }
    }

    @Override
    public void update(T entity) {
        try (Session session = sessionManager.getSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(entity);
            tx1.commit();
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionManager.getSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(entity);
            tx1.commit();
        }
    }

}
