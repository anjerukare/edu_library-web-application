package edu.mtp.Library.dao;

import edu.mtp.Library.models.User;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.SqlParameterSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@PropertySource("classpath:/sql/users.properties")
public class UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ResultSetExtractorImpl<User> extractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id", "role_id", "favoritebooks_book_id", "favoritebooks_book_authors_id",
                            "statusbooks_book_id", "statusbooks_status_id")
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

    @Value("${users.queries.get-all}")
    private String GET_ALL_QUERY;

    @Value("${users.queries.get-by-id}")
    private String GET_BY_ID_QUERY;

    @Value("${users.queries.update-user}")
    private String UPDATE_USER_QUERY;

    @Value("${users.queries.delete-book-from-favorites}")
    private String DELETE_BOOK_FROM_FAVORITES_QUERY;

    @Value("${users.queries.insert-book-to-favorites}")
    private String INSERT_BOOK_TO_FAVORITES_QUERY;

    @Value("${users.queries.delete-status-from-book}")
    private String DELETE_STATUS_FROM_BOOK_QUERY;

    @Value("${users.queries.insert-status-for-book}")
    private String INSERT_STATUS_FOR_BOOK_QUERY;

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

    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, extractor);
    }

    public User get(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(GET_BY_ID_QUERY, parameterSource, extractor)
                .stream().findAny().orElse(null);
    }

    public void save(User user) {
        jdbcTemplate.update(UPDATE_USER_QUERY, parameterFactory.newSqlParameterSource(user));
    }

    public void deleteBookFromFavorites(int userId, int bookId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("userid", userId)
                .addValue("bookid", bookId);
        jdbcTemplate.update(DELETE_BOOK_FROM_FAVORITES_QUERY, parameterSource);
    }

    public void insertBookToFavorites(int userId, int bookId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("userid", userId)
                .addValue("bookid", bookId);
        jdbcTemplate.update(INSERT_BOOK_TO_FAVORITES_QUERY, parameterSource);
    }

    public void deleteStatusFromBook(int userId, int bookId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("userid", userId)
                .addValue("bookid", bookId);
        jdbcTemplate.update(DELETE_STATUS_FROM_BOOK_QUERY, parameterSource);
    }

    public void insertStatusForBook(int userId, int bookId, int statusId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("userid", userId)
                .addValue("bookid", bookId)
                .addValue("statusid", statusId);
        jdbcTemplate.update(INSERT_STATUS_FOR_BOOK_QUERY, parameterSource);
    }
}
