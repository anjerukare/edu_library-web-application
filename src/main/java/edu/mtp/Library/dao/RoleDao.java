package edu.mtp.Library.dao;

import edu.mtp.Library.models.Role;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/sql/roles.properties")
public class RoleDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ResultSetExtractorImpl<Role> extractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Role.class);

    @Value("${roles.queries.get-by-id}")
    private String GET_BY_ID_QUERY;

    @Autowired
    public RoleDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Role get(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(GET_BY_ID_QUERY, parameterSource, extractor)
                .stream().findAny().orElse(null);
    }
}
