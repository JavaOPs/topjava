package ru.javawebinar.topjava.util.validation;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value == null || Jsoup.isValid(value, Safelist.none());
    }
}
