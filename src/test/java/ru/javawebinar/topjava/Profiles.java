package ru.javawebinar.topjava;

import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfilesResolver;
import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }

    //http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
    public static class ActiveDbProfileResolver implements ActiveProfilesResolver {
        @Override
        public @NonNull String[] resolve(@NonNull Class<?> aClass) {
            return new String[]{getActiveDbProfile()};
        }
    }
}
