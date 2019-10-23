package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import java.util.Collection;


public class MealService {


     MealRepository repository=new InMemoryMealRepository();



    public Meal save (Meal meal ){
       return repository.save(meal);
    }

    public boolean delete (Integer id){
        return repository.delete(id);
    }

    public Meal get (Integer id){
        return repository.get(id);
    }

    public Collection <Meal> getAll(int userId){ return repository.getAll(userId);}

}