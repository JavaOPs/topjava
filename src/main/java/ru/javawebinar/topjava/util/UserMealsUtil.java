package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
        int startHour = 10;
        int startMinute = 0;
        int endHour = 12;
        int endMinute = 59;
        int caloriesPerDay = 8000;

        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        List<UserMealWithExceed> list = getFilteredWithExceededSimple(mealList, startTime, endTime, caloriesPerDay);

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
        //TestUserMealsUtil.testGetFilteredWithExceeded(mealList, caloriesPerDay, startTime, endTime, list);

        //benchmark
        //org.openjdk.jmh.Main.main(args);
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
        UserMeal userMeal;
        UserMeal userMealPrevious;
        List<UserMeal> tempUserMealList = new ArrayList<>();
        tempUserMealList.add(mealList.get(0));
        int caloriesCounter = mealList.get(0).getCalories();
        //iterate every except first
        for (int i = 1; i < mealList.size(); i++) {
            userMeal = mealList.get(i);
            userMealPrevious = mealList.get(i - 1);
            // add to temp list if the same day and summarize calories
            if (!userMeal.getDateTime().toLocalDate().isAfter(userMealPrevious.getDateTime().toLocalDate())) {
                tempUserMealList.add(userMeal);
                caloriesCounter += userMeal.getCalories();
            } else {
                //else check all from temp list and add to result list by filter
                for (UserMeal userMealFromTemp : tempUserMealList) {
                    LocalTime userMealTime = userMealFromTemp.getDateTime().toLocalTime();
                    Boolean isExceeded = caloriesCounter > caloriesPerDay;
                    if (TimeUtil.isBetween(userMealTime, startTime, endTime)) {
                        userMealWithExceedList.add(new UserMealWithExceed(userMealFromTemp.getDateTime(),
                                userMealFromTemp.getDescription(), userMealFromTemp.getCalories(),
                                isExceeded));
                    }
                }
                caloriesCounter = userMeal.getCalories();
                tempUserMealList.clear();
                tempUserMealList.add(userMeal);
            }
        }
        return userMealWithExceedList;
    }

    /**
     * get filtered using Map without sorting.
     *
     * @param mealList
     * @param startTime
     * @param endTime
     * @param caloriesPerDay
     * @return
     */
    public static List<UserMealWithExceed> getFilteredWithExceededMap(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceeds = new LinkedList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        // map userMeal to group by date, value is summarized calories of same day
        for (UserMeal userMeal : mealList) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            if (map.containsKey(localDate)) {
                map.put(localDate, (map.get(localDate) + userMeal.getCalories()));
            } else {
                map.put(localDate, userMeal.getCalories());
            }
        }
        //iterate every and add to result list by filter
        for (UserMeal userMeal : mealList) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            LocalTime localTime = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(localTime, startTime, endTime)) {
                mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), map.get(localDate) > caloriesPerDay));
            }
        }
        return mealWithExceeds;
    }
}
