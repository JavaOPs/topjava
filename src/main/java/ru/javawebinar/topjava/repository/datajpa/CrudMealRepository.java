package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Modifying
    @Query("UPDATE Meal m SET m.description=:description, m.calories=:calories, m.dateTime=:date_time WHERE m.id=:id AND m.user.id=:userId")
    int update(@Param("description") String description, @Param("calories") int calories, @Param("date_time") LocalDateTime dateTime, @Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    Meal get(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("userId") int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("userId") int userId);
}
