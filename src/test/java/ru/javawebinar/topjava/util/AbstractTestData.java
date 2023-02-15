package ru.javawebinar.topjava.util;

import org.assertj.core.api.Assertions;

import java.util.Arrays;

/**
 * @author Alexei Valchuk, 15.02.2023, email: a.valchukav@gmail.com
 */

public abstract class AbstractTestData<T> {

    protected String[] fieldsToIgnore;

    public AbstractTestData(String... fieldsToIgnore) {
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public void assertMatch(T actual, T expected) {
        Assertions.assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
    }

    @SafeVarargs
    public final void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        Assertions.assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
    }
}
