package ru.javawebinar.topjava.util;


import org.openjdk.jmh.annotations.*;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BenchMark {

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        List<UserMeal> mealList;

        @Param({"5", "15", "30"})
        public int iterations;

        @Setup(Level.Invocation)
        public void setUp() {

            Random random = new Random();
            mealList = new ArrayList<>();
            for (int i = 0; i < 120; i++) {
                int month = random.nextInt(12) + 1;
                int day = random.nextInt(28) + 1;
                int hour = random.nextInt(18) + 5;
                int minute = random.nextInt(59) + 1;
                int calories = random.nextInt(1000) + 100;
                UserMeal userMeal = new UserMeal(LocalDateTime.of(2015, month, day, hour, minute), "Завтрак", calories);
                mealList.add(userMeal);
                //System.out.println(userMeal);
            }

/*            mealList = new ArrayList<>();
            for (int i = 1; i < iterations; i++) {
                mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 10, 0), "Завтрак", 300 + i * 14));
                mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 13, 0), "Обед", 500 + i * 10));
                mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 20, 0), "Ужин", 200 + i * 13));
            }*/
        }
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    public void benchGetFilteredWithExceededByStream(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceeded(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

/*    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public void benchGetFilteredWithExceededByLoop(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceeded(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }*/

}
