package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

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
            System.out.println(adminUserController.create(new User(1, "userName", "", "password", Role.ROLE_ADMIN)));
        }
    }
}
