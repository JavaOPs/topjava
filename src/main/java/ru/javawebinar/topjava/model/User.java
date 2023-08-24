package ru.javawebinar.topjava.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.HasIdAndEmail;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.util.validation.NoHtml;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

import static ru.javawebinar.topjava.util.UsersUtil.DEFAULT_CALORIES_PER_DAY;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.BY_EMAIL, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.name, u.email"),
})
@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity implements HasIdAndEmail {

    public static final String DELETE = "User.delete";
    public static final String BY_EMAIL = "User.getByEmail";
    public static final String ALL_SORTED = "User.getAllSorted";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml(groups = {View.Web.class})  // https://stackoverflow.com/questions/17480809
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 128)
    // https://stackoverflow.com/a/12505165/548473
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    //    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @Column(name = "calories_per_day", nullable = false, columnDefinition = "int default 2000")
    @Range(min = 10, max = 10000)
    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("dateTime DESC")
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
    @ApiModelProperty(hidden = true)
    private List<Meal> meals;

    public User() {
    }

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.caloriesPerDay, u.enabled, u.registered, u.roles);
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, Role... roles) {
        this(id, name, email, password, caloriesPerDay, true, new Date(), List.of(roles));
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public String getPassword() {
        return password;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                '}';
    }
}