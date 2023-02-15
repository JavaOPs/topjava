package ru.javawebinar.topjava.web;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * @author Alexei Valchuk, 07.02.2023, email: a.valchukav@gmail.com
 */

@UtilityClass
public class SecurityUtil {

    @Getter
    @Setter
    private static int id = AbstractBaseEntity.START_SEQ;

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
