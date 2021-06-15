package ru.javawebinar.topjava;

import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.TestUtil.readListFromJsonMvcResult;

public class TestMatcher<T> {
    private final Class<T> clazz;
    private final BiConsumer<T, T> assertion;
    private final BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion;

    private TestMatcher(Class<T> clazz, BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion) {
        this.clazz = clazz;
        this.assertion = assertion;
        this.iterableAssertion = iterableAssertion;
    }

    public static <T> TestMatcher<T> usingAssertions(Class<T> clazz, BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion) {
        return new TestMatcher<>(clazz, assertion, iterableAssertion);
    }

    public static <T> TestMatcher<T> usingEqualsComparator(Class<T> clazz) {
        return usingAssertions(clazz,
                (a, e) -> assertThat(a).isEqualTo(e),
                (a, e) -> assertThat(a).isEqualTo(e));
    }

    public static <T> TestMatcher<T> usingIgnoringFieldsComparator(Class<T> clazz, String... fieldsToIgnore) {
        return usingAssertions(clazz,
                (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(e),
                (a, e) -> assertThat(a).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(e));
    }

    public void assertMatch(T actual, T expected) {
        assertion.accept(actual, expected);
    }

    @SafeVarargs
    public final void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        iterableAssertion.accept(actual, expected);
    }

    public ResultMatcher contentJson(T expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, clazz), expected);
    }

    @SafeVarargs
    public final ResultMatcher contentJson(T... expected) {
        return contentJson(List.of(expected));
    }

    public ResultMatcher contentJson(Iterable<T> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, clazz), expected);
    }
}
