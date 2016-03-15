package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            Stream.of(appCtx.getBeanDefinitionNames()).forEach(System.out::println);
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            System.out.println(userMealRestController.create(new UserMeal(3, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)));
            userMealRestController.getAll().forEach(System.err::println);
            System.out.println(adminUserController.create(new User(1, "userName", "2@ru.ru", "password", Role.ROLE_ADMIN)));
        }
    }
}
