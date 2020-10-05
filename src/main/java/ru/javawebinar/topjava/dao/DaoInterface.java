package ru.javawebinar.topjava.dao;

import java.util.List;

public interface DaoInterface<T, ID> {

    <S extends T> S save(S entity);

    T getOne(ID id);

    List<T> findAll();

    <S extends T> S update(S entity);

    void deleteById(ID id);

    void delete(T entity);
}
