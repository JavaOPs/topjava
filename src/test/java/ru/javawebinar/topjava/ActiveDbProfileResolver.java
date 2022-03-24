package ru.javawebinar.topjava;

import org.springframework.lang.NonNull;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver extends DefaultActiveProfilesResolver {
    @Override
    public @NonNull
    String[] resolve(@NonNull Class<?> aClass) {
        List<String> profiles = new ArrayList<>(Arrays.asList(super.resolve(aClass)));
        profiles.add(Profiles.getActiveDbProfile());
        return profiles.toArray(String[]::new);
    }
}
