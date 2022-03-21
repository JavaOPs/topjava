package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.Role;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("role")!=null ? Role.valueOf(rs.getString("role")) : null;
    }
}
