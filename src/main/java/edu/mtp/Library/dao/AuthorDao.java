package edu.mtp.Library.dao;

import edu.mtp.Library.models.Author;
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
@PropertySource("classpath:/sql/authors.properties")
public class AuthorDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ResultSetExtractorImpl<Author> extractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id", "publisher_id", "publisher_role_id",
                            "moderator_id", "moderator_role_id")
                    .newResultSetExtractor(Author.class);

    private final SqlParameterSourceFactory<Author> parameterFactory =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .newSqlParameterSourceFactory(Author.class);

    @Value("${authors.queries.get-all}")
    private String GET_ALL_QUERY;

    @Value("${authors.queries.add}")
    private String ADD_QUERY;

    @Value("${authors.queries.get-by-id}")
    private String GET_BY_ID_QUERY;

    @Value("${authors.queries.save}")
    private String SAVE_QUERY;

    @Value("${authors.queries.has-books}")
    private String HAS_BOOKS_QUERY;

    @Value("${authors.queries.set-published}")
    private String SET_PUBLISHED_QUERY;

    @Value("${authors.queries.delete}")
    private String DELETE_QUERY;

    @Autowired
    public AuthorDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Author> getAll() {
        return getAll(true);
    }

    public List<Author> getAll(boolean published) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("published", published);
        return jdbcTemplate.query(GET_ALL_QUERY, parameterSource, extractor);
    }

    public void add(Author author) {
        jdbcTemplate.update(ADD_QUERY, parameterFactory.newSqlParameterSource(author));
    }

    public Author get(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(GET_BY_ID_QUERY, parameterSource, extractor)
                .stream().findAny().orElse(null);
    }

    public void save(Author author) {
        jdbcTemplate.update(SAVE_QUERY, parameterFactory.newSqlParameterSource(author));
    }

    public Boolean hasBooks(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        Optional<Boolean> hasBooks = jdbcTemplate.query(HAS_BOOKS_QUERY,
                parameterSource, new SingleColumnRowMapper<>(Boolean.class)).stream().findAny();
        return hasBooks.orElse(false);
    }

    public void setPublished(int id, int moderatorId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("moderatorid", moderatorId);
        jdbcTemplate.update(SET_PUBLISHED_QUERY, parameterSource);
    }

    public void delete(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(DELETE_QUERY, parameterSource);
    }
}
