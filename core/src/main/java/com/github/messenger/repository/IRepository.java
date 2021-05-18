package com.github.messenger.repository;

import org.hibernate.Session;

import java.util.Collection;

public interface IRepository<T> {

    Collection<T> findAll();

    T findBy(String field, Object value);

    Collection<T> findAllBy(String field, Object value);

    void save(T entity);

    void update(T entity);

    void delete(T entity);

}
