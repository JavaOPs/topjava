package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import ru.javawebinar.topjava.util.UserUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo implements Serializable {
    protected int id;

    @NotEmpty
    protected String name;

    @Email
    @NotEmpty
    protected String email;

    @Size(min = 5, max = 64, message = " must between 5 and 64 characters")
    protected String password;

    @Range(min = 100, max = 5000)
    @NotNull(message = " must not be empty")
    protected Integer caloriesPerDay = UserUtil.DEFAULT_CALORIES_PER_DAY;

    public UserTo() {
    }

    public UserTo(int id, String name, String email, String password, int caloriesPerDay) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = (id == null ? 0 : id);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", caloriesPerDay='" + caloriesPerDay + '\'' +
                '}';
    }
}
