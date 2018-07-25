package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {


    @PersistenceContext
    private EntityManager em;

    private UserRepository userRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {

        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        }

        Meal mealCheck = em.find(Meal.class, meal.getId());

        if (mealCheck.getUser().getId() != userId)
            throw new NotFoundException("Not found");

        return em.merge(meal);
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).
                setParameter("id", id).
                setParameter("user", em.getReference(User.class, userId)).
                executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);

        if (meal.getUser().getId() != userId)
            throw new NotFoundException("Not found");

        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).
                setParameter("user", userRepository.get(userId)).
                getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.FILTERED_BETWEEN, Meal.class)
                .setParameter("user", em.getReference(User.class, userId))
                .setParameter("fd", startDate)
                .setParameter("td", endDate)
                .getResultList();
    }
}