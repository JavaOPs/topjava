package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.UserMeal;

import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Profile(Profiles.POSTGRES)
public class Java8JdbcUserMealRepositoryImpl extends AbstractJdbcUserMealRepositoryImpl<LocalDateTime> {

    @Autowired
    public Java8JdbcUserMealRepositoryImpl(DataSource dataSource) {
        super(BeanPropertyRowMapper.newInstance(UserMeal.class), dataSource);
    }

    @Override
    protected LocalDateTime toDbValue(LocalDateTime ldt) {
        return ldt;
    }
}