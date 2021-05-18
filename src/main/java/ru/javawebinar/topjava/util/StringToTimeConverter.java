package ru.javawebinar.topjava.util;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Component
class StringToTimeConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return new HashSet<>(Arrays.asList(new ConvertiblePair(String.class, LocalTime.class)
                , new ConvertiblePair(String.class, LocalDate.class)));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        DateTimeFormatter forTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter forDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (source.equals("")) {
            return null;
        } else if (targetType.getType() == LocalTime.class) {
            return LocalTime.parse(source.toString(), forTime);
        } else {
            return LocalDate.parse(source.toString(), forDate);
        }
    }
}
