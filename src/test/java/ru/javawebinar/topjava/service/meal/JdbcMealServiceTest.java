package ru.javawebinar.topjava.service.meal;

import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"jdbc & !datajpa"})
public class JdbcMealServiceTest extends BaseMealServiceTest {

    @Override
    @Profile("hsqldb")
    public void delete() {
        super.delete();
    }

    @Override
    @Profile("hsqldb")
    public void deleteNotFound() {
        super.deleteNotFound();
    }

    @Override
    @Profile("hsqldb")
    public void deleteNotOwn() {
        super.deleteNotOwn();
    }

    @Override
    @Profile("hsqldb")
    public void create() {
        super.create();
    }

    @Override
    @Profile("postgres")
    public void duplicateDateTimeCreate() {
        super.duplicateDateTimeCreate();
    }

    @Override
    @Profile("hsqldb")
    public void get() {
        super.get();
    }

    @Override
    @Profile("hsqldb")
    public void getNotFound() {
        super.getNotFound();
    }

    @Override
    @Profile("hsqldb")
    public void getNotOwn() {
        super.getNotOwn();
    }

    @Override
    @Profile("hsqldb")
    public void update() {
        super.update();
    }

    @Override
    @Profile("hsqldb")
    public void updateNotOwn() {
        super.updateNotOwn();
    }

    @Override
    @Profile("hsqldb")
    public void getAll() {
        super.getAll();
    }

    @Override
    @Profile("postgres")
    public void getBetweenInclusive() {
        super.getBetweenInclusive();
    }

    @Override
    @Profile("postgres")
    public void getBetweenWithNullDates() {
        super.getBetweenWithNullDates();
    }
}
