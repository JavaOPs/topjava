package ru.javawebinar.topjava.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author Alexei Valchuk, 07.02.2023, email: a.valchukav@gmail.com
 */

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = "authorities")
public class User extends AbstractNamedEntity {

    private String email;
    private String password;
    private boolean enabled = true;
    private Date registered = new Date();
    private int caloriesPerDay = MealsUtil.DEFAULT_CALORIES_PER_DAY;
    private Set<Role> authorities;

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, MealsUtil.DEFAULT_CALORIES_PER_DAY, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Set<Role> authorities) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.caloriesPerDay = caloriesPerDay;
        this.authorities = authorities;
    }
}
