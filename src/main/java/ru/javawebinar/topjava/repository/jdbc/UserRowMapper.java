package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRegistered(rs.getTimestamp("registered"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setCaloriesPerDay(rs.getInt("calories_per_day"));

        Set<Role> roles = new HashSet<>();

        if (rs.getString("role")!=null) {
            roles.add(Role.valueOf(rs.getString("role")));
        }
        user.setRoles(roles);


        return user;
    }
}
