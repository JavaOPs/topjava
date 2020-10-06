package ru.javawebinar.topjava.dao;

import java.util.List;

public interface DaoInterface<T, ID> {

    T save(T entity);

    T getOne(ID id);

    List<T> findAll();

    T update(T entity);

    void deleteById(ID id);

}
