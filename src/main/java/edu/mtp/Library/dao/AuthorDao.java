package edu.mtp.Library.dao;

import edu.mtp.Library.models.Author;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Value("${authors.queries.get-all}")
    private String GET_ALL_QUERY;

    @Value("${authors.queries.get-by-id}")
    private String GET_BY_ID_QUERY;

    @Autowired
    public AuthorDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Author> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, extractor);
    }

    public Author get(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(GET_BY_ID_QUERY, parameterSource, extractor)
                .stream().findAny().orElse(null);
    }
}
