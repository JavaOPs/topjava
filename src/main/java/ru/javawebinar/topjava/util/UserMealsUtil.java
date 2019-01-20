package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserMealsUtil {
    public static final int START_HOUR = 12;
    public static final int START_MINUTE = 0;
    public static final int END_HOUR = 15;
    public static final int END_MINUTE = 59;
    public static final int INITIAL_CAPACITY = 150;
    public static final int CALORIES_PER_DAY = 8000;


    public static void main(String[] args) throws Exception {
//        List<UserMealWithExceed> list = getFilteredWithExceededSimple(prepareMealList(), getStartTime(), getEndTime(), CALORIES_PER_DAY);
        List<UserMealWithExceed> list = getFilteredWithExceededMap(prepareMealList(), getStartTime(), getEndTime(), CALORIES_PER_DAY);
        UserMealWithExceedUtil.sortListByDateTime(list);
        UserMealWithExceedUtil.printList(list);
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
        Collections.sort(mealList, UserMealsUtil::compareByDateASC);
        //collection for return
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        List<UserMeal> tempUserMealList = new ArrayList<>();
        tempUserMealList.add(mealList.get(0));
        int caloriesCounterOutLoop = mealList.get(0).getCalories();
        //iterate every except first
        for (int i = 1; i < mealList.size(); i++) {
            UserMeal userMeal = mealList.get(i);
            UserMeal userMealPrevious = mealList.get(i - 1);
            // add to temp list if the same day and summarize calories
            if (!userMeal.getDateTime().toLocalDate().isAfter(userMealPrevious.getDateTime().toLocalDate())) {
                tempUserMealList.add(userMeal);
                caloriesCounterOutLoop += userMeal.getCalories();
            } else {
                //else check all from temp list and add to result list by filter
                int caloriesCounterInLoop = caloriesCounterOutLoop;
                tempUserMealList.forEach(userMealFromTemp -> {
                    LocalTime userMealTime = userMealFromTemp.getDateTime().toLocalTime();
                    Boolean isExceeded = caloriesCounterInLoop > caloriesPerDay;
                    if (TimeUtil.isBetween(userMealTime, startTime, endTime)) {
                        userMealWithExceedList.add(new UserMealWithExceed(userMealFromTemp.getDateTime(),
                                userMealFromTemp.getDescription(), userMealFromTemp.getCalories(),
                                isExceeded));
                    }
                });
                caloriesCounterOutLoop = userMeal.getCalories();
                tempUserMealList.clear();
                tempUserMealList.add(userMeal);
            }
        }
        return userMealWithExceedList;
    }

    /**
     * get filtered using Map without sorting.     *
     *
     * @param mealList
     * @param startTime
     * @param endTime
     * @param caloriesPerDay
     * @return ----->>>> can this method be considered as completed "Optional (Java 8 Stream API)"???
     */
    public static List<UserMealWithExceed> getFilteredWithExceededMap(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) throws Exception {
        if (hasaNull(mealList, startTime, endTime)) {
            return Collections.emptyList();
        }
        Map<LocalDate, Integer> map = new HashMap<>();
        mealList.forEach(userMeal -> {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            map.merge(localDate, userMeal.getCalories(), Integer::sum);
        });
        return mealList.stream()
                .filter(userMeal -> filterByTime(userMeal, startTime, endTime))
                .map(a -> UserMealWithExceedUtil.createUserMealWithExceed(a, map, caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMeal> prepareMealList() {
        List<UserMeal> mealList = new ArrayList<>(INITIAL_CAPACITY);
        IntStream.range(1, INITIAL_CAPACITY).forEach(i -> UserMealsUtil.addNewUserMeal(i, mealList));
        return mealList;
    }

    private static void addNewUserMeal(int i, List<UserMeal> mealList) {
        Random random = new Random();
        int month = random.nextInt(2) + 1;//for two month
        int day = random.nextInt(28) + 1;
        int hour = random.nextInt(23) + 1;
        int minute = random.nextInt(59) + 1;
        int calories = random.nextInt(4100) + 100;
        UserMeal userMeal = new UserMeal(LocalDateTime.of(2015, month, day, hour, minute), "ОпятьКушать номер " + i, calories);
        mealList.add(userMeal);
    }

    public static LocalTime getStartTime() {
        int startHour = START_HOUR;
        int startMinute = START_MINUTE;

        return LocalTime.of(startHour, startMinute);
    }

    public static LocalTime getEndTime() {
        int endHour = END_HOUR;
        int endMinute = END_MINUTE;
        return LocalTime.of(endHour, endMinute);
    }

    public static int compareByDateASC(UserMeal o1, UserMeal o2) {
        if (o1.getDateTime().isEqual(o2.getDateTime())) {
            return 0;
        }
        return o1.getDateTime().isBefore(o2.getDateTime()) ? -1 : 1;
    }

    public static boolean filterByTime(UserMeal userMeal, LocalTime startTime, LocalTime endTime) {
        return TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime);
    }

    public static boolean hasaNull(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime) {
        return !isPresent(mealList) || !TimeUtil.isPresent(startTime) || !TimeUtil.isPresent(endTime);
    }

    public static boolean isPresent(List<UserMeal> mealList) {
        return mealList != null;
    }
}
