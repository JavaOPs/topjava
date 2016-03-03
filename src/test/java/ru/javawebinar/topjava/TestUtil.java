package ru.javawebinar.topjava;

import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.matcher.ModelMatcher;

import java.io.UnsupportedEncodingException;
import java.util.function.Function;

/**
 * GKislin
 * 05.01.2015.
 */
public class TestUtil {

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    /**
     * Compare entities using toString
     */
    public static class ToStringModelMatcher<T> extends ModelMatcher<T, String> {
        public ToStringModelMatcher(Class<T> entityClass) {
            super(new Function<T, String>() {
                @Override
                public String apply(T t) {
                    return t.toString();
                }
            }, entityClass);
        }
    }
}
