package ru.javawebinar.topjava;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import ru.javawebinar.topjava.dto.MealWithExceed;

import java.util.List;

import static java.time.LocalTime.of;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.javawebinar.topjava.dao.MealDao.mealList;
import static ru.javawebinar.topjava.util.MealUtil.getFilteredWithExceeded;

@DisplayName("Тестирование класса MealUtil")
public class MealUtilTest {

    @RepeatedTest(5)
    @DisplayName("Тестирование метода getFilteredWithExceeded")
    void getFilteredWithExceededTest() {
        List<MealWithExceed> listMealWithExceed
                = getFilteredWithExceeded(mealList, of(12, 0), of(21, 0), 2000);
        assertAll(
                () -> assertThat(listMealWithExceed.size()).isEqualTo(4),

                () -> assertThat(listMealWithExceed.stream()
                        .filter(MealWithExceed::isExceed)
                        .collect(toList()).size()).isEqualTo(2),

                () -> assertThat(listMealWithExceed.stream()
                        .filter(mealWithExceed -> !mealWithExceed.isExceed())
                        .collect(toList()).size()).isEqualTo(2)
        );
    }
}
