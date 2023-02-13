package ru.javawebinar.topjava;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Alexei Valchuk, 12.02.2023, email: a.valchukav@gmail.com
 */

@Configuration
@ComponentScan(basePackages = {
        "ru.javawebinar.topjava.**.repository",
        "ru.javawebinar.topjava.**.service",
        "ru.javawebinar.topjava.**.web"
})
public class SpringConfig {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(SpringConfig.class)) {
            System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        }
    }
}
