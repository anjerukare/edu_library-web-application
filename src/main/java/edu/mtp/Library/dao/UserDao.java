package edu.mtp.Library.dao;

import edu.mtp.Library.models.User;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.SqlParameterSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@PropertySource("classpath:/sql/users.properties")
public class UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ResultSetExtractorImpl<User> extractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id", "role_id")
                    .newResultSetExtractor(User.class);

    private final SqlParameterSourceFactory<User> parameterFactory =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .newSqlParameterSourceFactory(User.class);

    @Value("${users.queries.insert-user}")
    private String INSERT_USER_QUERY;

    @Value("${users.queries.is-exists}")
    private String IS_EXISTS_QUERY;

    @Value("${users.queries.get-id-by-username}")
    private String GET_ID_BY_USERNAME_QUERY;

    @Value("${users.queries.get-by-id}")
    private String GET_BY_ID_QUERY;

    @Autowired
    public UserDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) {
        jdbcTemplate.update(INSERT_USER_QUERY, parameterFactory.newSqlParameterSource(user));
    }

    public Boolean isExists(String username) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("username", username);
        Optional<Boolean> exists = jdbcTemplate.query(IS_EXISTS_QUERY,
                parameterSource, new SingleColumnRowMapper<>(Boolean.class)).stream().findAny();
        return exists.orElse(false);
    }

    public Integer getIdByUsername(String username) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("username", username);
        Optional<Integer> userId = jdbcTemplate.query(GET_ID_BY_USERNAME_QUERY,
                parameterSource, new SingleColumnRowMapper<>(Integer.class)).stream().findAny();
        return userId.orElse(null);
    }

    public User get(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(GET_BY_ID_QUERY, parameterSource, extractor)
                .stream().findAny().orElse(null);
    }
}
