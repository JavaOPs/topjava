package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id and m.user=:user"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m left JOIN FETCH m.user where m.user=:user ORDER BY m.dateTime desc"),
        @NamedQuery(name = Meal.FILTERED_BETWEEN, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user where m.user=:user and m.dateTime between :fd and :td ORDER BY m.dateTime desc")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time","user_id"}, name = "meal_unique_date_time_user")})
public class Meal extends AbstractBaseEntity {

    public static final String ALL_SORTED = "MeaFFl.getAllSorted";
    public static final String DELETE = "Meal.delete";
    public static final String FILTERED_BETWEEN = "Meal.getFilteredByDate";


    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 10000)
    private int calories;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                "} " + super.toString();
    }
}
