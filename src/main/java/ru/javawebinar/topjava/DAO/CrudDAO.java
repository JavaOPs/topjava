package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;


/**
 * Created by skorpion on 12.09.16.
 */
public class CrudDAO implements DAO {


    private static CrudDAO ourInstance = new CrudDAO();

    public static CrudDAO getInstance() {
        return ourInstance;
    }

    private CrudDAO() {
    }


    @Override
    public void delete(Object object) {
        if (object instanceof Meal) {
            MealDB.getInstance().delete(object);
        }


    }
}
