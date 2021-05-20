package com.github.messenger.repository;

import java.util.Collection;

public interface IRepository<T> {

    Collection<T> findAll();

    T findById(Long id);

    <K> T findBy(String field, Class<K> clz, K value);

    <K> Collection<T> findAllBy(String field, Class<K> clz, K value);

    void save(T entity);

    void update(T entity);

    void delete(T entity);

}
