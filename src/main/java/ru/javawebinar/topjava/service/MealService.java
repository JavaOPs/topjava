package ru.javawebinar.topjava.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.repository.MealRepository;

/**
 * @author Alexei Valchuk, 07.02.2023, email: a.valchukav@gmail.com
 */

@Service
@Getter
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }
}
