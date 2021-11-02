package ru.javawebinar.topjava;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Factory for creating test matchers.
 * <p>
 * Comparing actual and expected objects via AssertJ
 */
public class MatcherFactory<T> {
    private final String[] fieldsToIgnore;

    private MatcherFactory(String... fieldsToIgnore) {
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public static <T> MatcherFactory<T> usingIgnoringFieldsComparator(String... fieldsToIgnore) {
        return new MatcherFactory<>(fieldsToIgnore);
    }

    public void assertMatch(T actual, T expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
    }

    @SafeVarargs
    public final void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
    }
}
