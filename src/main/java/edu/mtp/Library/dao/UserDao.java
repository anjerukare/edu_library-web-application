package edu.mtp.Library.dao;

import edu.mtp.Library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<>(User.class));
    }

    public void add(User user) {
        jdbcTemplate.update("insert into users(username, password) values(?, ?)",
                user.getUsername(), user.getPassword());
    }

    public User get(int id) {
        return jdbcTemplate.query("select * from users where id = ?",
                new BeanPropertyRowMapper<>(User.class), id).stream().findAny().orElse(null);
    }

    public void set(int id, User user) {
        jdbcTemplate.update("update users set username = ?, password = ? where id = ?",
                user.getUsername(), user.getPassword(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from users where id = ?", id);
    }

    public Boolean isExists(String username) {
        Optional<Boolean> exists = jdbcTemplate.query("select exists(select 1 from users where username = ?)",
                new SingleColumnRowMapper<>(Boolean.class), username).stream().findAny();
        return exists.orElse(false);
    }

    public Integer getIdByUsername(String username) {
        Optional<Integer> userId = jdbcTemplate.query("select id from users where username = ?",
                new SingleColumnRowMapper<>(Integer.class), username).stream().findAny();
        return userId.orElse(null);
    }
}
