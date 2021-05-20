package com.github.messenger.repository.impl;

import com.github.messenger.repository.IRepository;
import com.github.messenger.utils.HibernateSessionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
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
    public T findById(Long id) {
        try (Session session = sessionManager.getSession()){
            return session.get(type, id);
        }
    }

    @Override
    public <K> T findBy(String field, Class<K> clz, K value) {
        try (Session session = sessionManager.getSession()) {
            return parametrizedQuery(session, field, clz, value).getSingleResult();
        }
    }

    @Override
    public <K> Collection<T> findAllBy(String field, Class<K> clz, K value) {
        try (Session session = sessionManager.getSession()) {
            return parametrizedQuery(session, field, clz, value).getResultList();
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

    private <K> TypedQuery<T> parametrizedQuery(Session session, String field, Class<K> clz, K value){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);

        ParameterExpression<K> params = criteriaBuilder.parameter(clz);
        criteriaQuery.where(criteriaBuilder.equal(root.get(field), params));

        TypedQuery<T> query = session.createQuery(criteriaQuery);
        query.setParameter(params, value);
        return query;
    }

}
