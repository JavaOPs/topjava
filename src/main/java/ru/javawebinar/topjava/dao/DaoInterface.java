package ru.javawebinar.topjava.dao;

import java.util.List;

/**
 * Created by alena.nikiforova on 28.03.2017.
 */
public interface DaoInterface<T> {
    void add(T obj);
    void delete(int id);
    void update(T obj);
    T getById(int id);
    List<T> getAll();
}
