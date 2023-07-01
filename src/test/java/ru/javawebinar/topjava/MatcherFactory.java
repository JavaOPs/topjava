package ru.javawebinar.topjava;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Factory for creating test matchers.
 * <p>
 * Comparing actual and expected objects via AssertJ
 */
public class MatcherFactory {
    public static <T> Matcher<T> usingIgnoringFieldsComparator(String... fieldsToIgnore) {
        return new Matcher<>(fieldsToIgnore);
    }

    public static class Matcher<T> {
        private final String[] fieldsToIgnore;

        private Matcher(String... fieldsToIgnore) {
            this.fieldsToIgnore = fieldsToIgnore;
        }

        public void assertMatch(T actual, T expected) {
            assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
        }

        @SafeVarargs
        public final void assertMatch(Iterable<T> actual, T... expected) {
            assertMatch(actual, List.of(expected));
        }

        public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
            assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
        }
    }
}
