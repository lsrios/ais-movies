package com.rls.ais.repositories;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    T findById(long id);
    void add(T entity);
    void update(T entity);
    void remove(long id);
}
