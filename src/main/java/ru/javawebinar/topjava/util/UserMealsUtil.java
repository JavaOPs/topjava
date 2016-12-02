package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 510)
        );

        UserMeal[] myArray = (UserMeal[]) mealList.toArray();
        Supplier<Stream<UserMeal>> mealStream =
                () -> Arrays.stream(myArray);
        /*Supplier<Stream<UserMeal>> mealStream =
                () -> Stream.of(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,20,0), "Ужин", 510)
        );*/

        /*Map<String, List<UserMeal>> mealByClories = mealStream.collect(
                Collectors.groupingBy(UserMeal::getDescription));*/

        //подсчет калорий по description
        /*Map<String, Integer> mealByCloriesSum = mealStream.collect(
                Collectors.groupingBy(UserMeal::getDescription, Collectors.summingInt(UserMeal::getCalories)));
        for(Map.Entry<String, Integer> item : mealByCloriesSum.entrySet()){
            System.out.println(item.getKey() + " - " + item.getValue());
        }*/

        //подсчет калорий по LocalDateTime
        //подсчет по дню
        Map<LocalDate, Integer> mealByCloriesSum2 = mealStream.get().collect(
                Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        //for(Map.Entry<LocalDate, Integer> item : mealByCloriesSum2.entrySet()){
        //    System.out.println(item.getKey() + " - " + item.getValue());
        //}

        TimeUtil timeUtil = new TimeUtil();
        List<UserMeal> mealList1 = new ArrayList<>();

        mealList.stream()
                //.filter(meal -> meal.getDescription().contains("Завтрак"))
                .filter(meal -> timeUtil.isBetween(meal.getDateTime().toLocalTime(), LocalTime.of(7, 0),
                        LocalTime.of(12, 0)))
                .forEach(i -> mealList1.add(new UserMeal(i.getDateTime(), i.getDescription(), i.getCalories())));

        /*mealList1.forEach((meal)-> System.out.println(meal.getDateTime()+" "+
                meal.getDescription()+" "+meal.getCalories()));*/

        int calor = 0;
        boolean exceed = false;
        ArrayList<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (int i = 0; i < mealList1.size(); i++) {
            calor = mealByCloriesSum2.get(mealList1.get(i).getDate());
            exceed = calor > 2000 ? true : false;
            /*userMealWithExceedList =  Arrays.asList(
                    new UserMealWithExceed(mealList1.get(i).getDateTime(),
                            mealList1.get(i).getDescription(), mealList1.get(i).getCalories(), exceed));*/
            userMealWithExceedList.add(new UserMealWithExceed(mealList1.get(i).getDateTime(),
                    mealList1.get(i).getDescription(), mealList1.get(i).getCalories(), exceed));
            //System.out.println(userMealWithExceedList.get(i));
        }
        //итоговый вывод
        /*userMealWithExceedList.stream()
                .forEach(s -> System.out.println(s.getDateTime()+" "+s.getCalories()+
                        " "+s.getDescription()+" "+s.getExceeded()));*/

/*
        HashMap<LocalDate, Integer> tmpCalories = new HashMap<>();
        for (Map.Entry<LocalDateTime, Integer> entry : mealByCloriesSum.entrySet()){
            tmpCalories.put(entry.getKey().toLocalDate(), entry.getValue());
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        for (Map.Entry<LocalDate, Integer> entry : tmpCalories.entrySet()){
            tmpCalories.put(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println(tmpCalories.size());
*/
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesPerDay) {
        TimeUtil timeUtil = new TimeUtil();

        //Проеобразование List к многократно используемому стриму
        UserMeal[] myArray = (UserMeal[]) mealList.toArray();
        Supplier<Stream<UserMeal>> mealStream = () -> Arrays.stream(myArray);

        //подсчет калорий с группировкой по дню
        Map<LocalDate, Integer> mealByCloriesSum2 = mealStream.get().collect(
                Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        //List для отфильтрованых данных
        List<UserMeal> mealList1 = new ArrayList<>();
        mealList.stream()
                //.filter(meal -> meal.getDescription().contains("Завтрак"))
                .filter(meal -> timeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(i -> mealList1.add(new UserMeal(i.getDateTime(), i.getDescription(), i.getCalories())));
        //вывод отфильтрованых данных
        /*mealList1.forEach((meal)-> System.out.println(meal.getDateTime()+" "+
                meal.getDescription()+" "+meal.getCalories()));*/

        //заполнение объектами класса UserMealWithExceed с установкой флага превышения калорий
        int calor = 0;
        boolean exceed = false;
        ArrayList<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (int i = 0; i < mealList1.size(); i++) {
            calor = mealByCloriesSum2.get(mealList1.get(i).getDate());
            exceed = calor > caloriesPerDay ? true : false;
            /*userMealWithExceedList =  Arrays.asList(
                    new UserMealWithExceed(mealList1.get(i).getDateTime(),
                            mealList1.get(i).getDescription(), mealList1.get(i).getCalories(), exceed));*/
            userMealWithExceedList.add(new UserMealWithExceed(mealList1.get(i).getDateTime(),
                    mealList1.get(i).getDescription(), mealList1.get(i).getCalories(), exceed));
            //System.out.println(userMealWithExceedList.get(i)); //объекты
        }
        //итоговый вывод массива объектов
        userMealWithExceedList.forEach((u) -> System.out.println(u.getDateTime() + " " + u.getDescription() +
                " " + u.getCalories() + " " + u.getExceeded()));
        /*for (UserMeal ul  : mealList) {
            //randDate = LocalDateTime.of(ul.getDateTime());

            if (timeUtil.isBetween(ul.getDateTime().toLocalTime(), startTime, endTime)) {
                System.out.println(ul.getDescription() + "    " + ul.getDateTime());
            }
        }

        for(Map.Entry<String, List<UserMeal>> item : mealByClories.entrySet()){
            for(UserMeal userMeal : item.getValue()){
                System.out.println(userMeal.getDescription());
            }
            System.out.println("");
        }

        //mealList.forEach((meal)-> System.out.println(meal.getDescription()));
        mealList.stream()
                //.filter(meal -> meal.getDescription().contains("Завтрак"))
                .filter(meal -> timeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(System.out::println);

        List<UserMealWithExceed> userMealWithExceedList = null;
        for (int i = 0; i < mealList.size(); i++) {
            userMealWithExceedList =  Arrays.asList(
                    new UserMealWithExceed(mealList.get(i).getDateTime(),
                            mealList.get(i).getDescription(), mealList.get(i).getCalories(), false));

            userMealWithExceedList.stream()
                    .forEach(s -> System.out.println(s.toString()));
        }

        List<UserMealWithExceed> filtered = userMealWithExceedList
                .stream()
                .collect(Collectors.toList());
        System.out.println(filtered.toString());*/


        // TODO return filtered list with correctly exceeded field
        return userMealWithExceedList;
    }
}
