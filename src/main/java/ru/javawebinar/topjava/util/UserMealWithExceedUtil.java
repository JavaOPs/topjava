package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserMealWithExceedUtil {

    public static UserMealWithExceed createUserMealWithExceed(UserMeal a, Map<LocalDate, Integer> map, int caloriesPerDay) {
        return new UserMealWithExceed(a.getDateTime(), a.getDescription(), a.getCalories(), map.get(a.getDateTime().toLocalDate()) > caloriesPerDay);
    }

    public static void printList(List<UserMealWithExceed> list) {
        for (UserMealWithExceed u : list) {
            System.out.println(u);
        }
    }

    public static void sortListByDateTime(List<UserMealWithExceed> list) {
        Collections.sort(list, UserMealWithExceedUtil::compareByDateASC);
    }

    public static int compareByDateASC(UserMealWithExceed o1, UserMealWithExceed o2) {
        if (o1.getDateTime().isEqual(o2.getDateTime())) {
            return 0;
        }
        return o1.getDateTime().isBefore(o2.getDateTime()) ? -1 : 1;
    }


}
