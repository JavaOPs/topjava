package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) throws Exception {
        Random random = new Random();

        List<UserMeal> mealList = new ArrayList<>();
        for (int i = 0; i < 130; i++) {
            int month = random.nextInt(2) + 1;
            int day = random.nextInt(28) + 1;
            int hour = random.nextInt(23) + 1;
            int minute = random.nextInt(59) + 1;
            int calories = random.nextInt(4100) + 100;
            UserMeal userMeal = new UserMeal(LocalDateTime.of(2015, month, day, hour, minute), "Завтрак", calories);
            mealList.add(userMeal);
        }
        int startHour = 12;
        int startMinute = 0;
        int endHour = 18;
        int endMinute = 30;
        int caloriesPerDay = 8000;

        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        List<UserMealWithExceed> list = getFilteredWithExceededMap(mealList, startTime, endTime, caloriesPerDay);

        // sort
        Collections.sort(list, (o1, o2) -> {
            if (o1.getDateTime().isEqual(o2.getDateTime())) {
                return 0;
            }
            return o1.getDateTime().isBefore(o2.getDateTime()) ? -1 : 1;
        });

        // to print
        for (UserMealWithExceed umwe : list) {
            System.out.println(umwe);
        }
    }

    /**
     * get filtered, list is sorted before handling.
     *
     * @param mealList
     * @param startTime
     * @param endTime
     * @param caloriesPerDay
     * @return
     */
    public static List<UserMealWithExceed> getFilteredWithExceededSimple(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //sort collection
        Collections.sort(mealList, (o1, o2) -> {
            if (o1.getDateTime().isEqual(o2.getDateTime())) {
                return 0;
            }
            return o1.getDateTime().isBefore(o2.getDateTime()) ? -1 : 1;
        });
        //collection for return
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        List<UserMeal> tempUserMealList = new ArrayList<>();
        tempUserMealList.add(mealList.get(0));
        int caloriesCounterOut = mealList.get(0).getCalories();
        //iterate every except first
        for (int i = 1; i < mealList.size(); i++) {
            UserMeal userMeal = mealList.get(i);
            UserMeal userMealPrevious = mealList.get(i - 1);
            // add to temp list if the same day and summarize calories
            if (!userMeal.getDateTime().toLocalDate().isAfter(userMealPrevious.getDateTime().toLocalDate())) {
                tempUserMealList.add(userMeal);
                caloriesCounterOut += userMeal.getCalories();
            } else {
                //else check all from temp list and add to result list by filter
                int caloriesCounterIn = caloriesCounterOut;
                tempUserMealList.forEach(userMealFromTemp -> {
                    LocalTime userMealTime = userMealFromTemp.getDateTime().toLocalTime();
                    Boolean isExceeded = caloriesCounterIn > caloriesPerDay;
                    if (TimeUtil.isBetween(userMealTime, startTime, endTime)) {
                        userMealWithExceedList.add(new UserMealWithExceed(userMealFromTemp.getDateTime(),
                                userMealFromTemp.getDescription(), userMealFromTemp.getCalories(),
                                isExceeded));
                    }
                });
                caloriesCounterOut = userMeal.getCalories();
                tempUserMealList.clear();
                tempUserMealList.add(userMeal);
            }
        }
        return userMealWithExceedList;
    }

    /**
     * get filtered using Map without sorting.     *
     * @param mealList
     * @param startTime
     * @param endTime
     * @param caloriesPerDay
     * @return
     *
     * ----->>>> can this method be considered as completed "Optional (Java 8 Stream API)"?
     */
    public static List<UserMealWithExceed> getFilteredWithExceededMap(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceeds;
        Map<LocalDate, Integer> map = new HashMap<>();
        // map userMeal to group by date, value is summarized calories of same day
        mealList.forEach(userMeal -> {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            if (map.containsKey(localDate)) {
                map.put(localDate, (map.get(localDate) + userMeal.getCalories()));
            } else {
                map.put(localDate, userMeal.getCalories());
            }
        });
        //stream and add to result list by filter
        mealWithExceeds = mealList.stream()
                .filter(userMeal -> userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime))
                .map(a -> new UserMealWithExceed(a.getDateTime(), a.getDescription(), a.getCalories(), map.get(a.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return mealWithExceeds;
    }
}
