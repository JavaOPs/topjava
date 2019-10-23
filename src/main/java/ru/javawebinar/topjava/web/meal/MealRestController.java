package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;


public class MealRestController {


    private MealService service = new MealService();

    public Meal save(Meal meal){
        return service.save(meal);
    }

        public boolean delete (Integer id)
        {
            return service.delete(id);
        }


    public Meal get (Integer id,Integer userId){
        return service.get(id);
    }

    public Collection<Meal> getAll(int userId){return service.getAll(userId);}

}