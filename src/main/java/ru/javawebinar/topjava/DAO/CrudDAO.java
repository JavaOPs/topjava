package ru.javawebinar.topjava.DAO;

/**
 * Created by skorpion on 12.09.16.
 */
public class CrudDAO<T> implements DAO<T> {


    private static CrudDAO ourInstance = new CrudDAO();

    public static CrudDAO getInstance() {
        return ourInstance;
    }

    private CrudDAO() {
    }

    @Override
    public void delete (T param) {

    }

}
