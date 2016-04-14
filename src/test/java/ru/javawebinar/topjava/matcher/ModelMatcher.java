package ru.javawebinar.topjava.matcher;

import org.junit.Assert;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * GKislin
 * 06.01.2015.
 *
 * @param <T> : entity
 * @param <R> : testEntity, converter result for compare
 */
public class ModelMatcher<T, R> {
    protected Function<T, R> entityConverter;
    protected Class<T> entityClass;

    public ModelMatcher(Function<T, R> entityConverter, Class<T> entityClass) {
        this.entityConverter = entityConverter;
        this.entityClass = entityClass;
    }

    private T fromJsonValue(String json) {
        return JsonUtil.readValue(json, entityClass);
    }

    private Collection<T> fromJsonValues(String json) {
        return JsonUtil.readValues(json, entityClass);
    }

    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(entityConverter.apply(expected), entityConverter.apply(actual));
    }

    public void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
        Assert.assertEquals(map(expected, entityConverter), map(actual, entityConverter));
    }

    public static <S, T> List<T> map(Collection<S> collection, Function<S, T> converter) {
        return collection.stream().map(converter).collect(Collectors.toList());
    }

    public ResultMatcher contentMatcher(T expect) {
        return content().string(
                new TestMatcher<T>(expect) {
                    @Override
                    protected boolean compare(T expected, String body) {
                        R actualForCompare = entityConverter.apply(fromJsonValue(body));
                        R expectedForCompare = entityConverter.apply(expected);
                        return expectedForCompare.equals(actualForCompare);
                    }
                });
    }

    public final ResultMatcher contentListMatcher(T... expected) {
        return contentListMatcher(Arrays.asList(expected));
    }

    public final ResultMatcher contentListMatcher(List<T> expected) {
        return content().string(new TestMatcher<List<T>>(expected) {
            @Override
            protected boolean compare(List<T> expected, String actual) {
                List<R> actualList = map(fromJsonValues(actual), entityConverter);
                List<R> expectedList = map(expected, entityConverter);
                return expectedList.equals(actualList);
            }
        });
    }

    public T fromJsonAction(ResultActions action) throws UnsupportedEncodingException {
        return fromJsonValue(TestUtil.getContent(action));
    }
}
