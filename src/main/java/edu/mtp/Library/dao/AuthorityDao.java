package edu.mtp.Library.dao;

import edu.mtp.Library.models.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorityDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorityDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAllUniqueAuthorities() {
        return jdbcTemplate.query("select distinct authority from authorities", new SingleColumnRowMapper<>(String.class));
    }

    public void add(Authority authority) {
        jdbcTemplate.update("insert into authorities values(?, ?)", authority.getUserId(),
                authority.getAuthority());
    }
}
