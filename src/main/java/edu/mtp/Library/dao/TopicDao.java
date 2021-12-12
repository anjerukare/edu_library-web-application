package edu.mtp.Library.dao;

import edu.mtp.Library.models.Topic;
import edu.mtp.Library.models.TopicReply;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.SqlParameterSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:/sql/topics.properties")
public class TopicDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ResultSetExtractorImpl<Topic> extractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id", "creator_id", "replies_id", "replies_topic_id", "replies_creator_id")
                    .newResultSetExtractor(Topic.class);

    private final SqlParameterSourceFactory<TopicReply> replyParameterFactory =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .newSqlParameterSourceFactory(TopicReply.class);

    @Value("${topics.queries.get-all}")
    private String GET_ALL_QUERY;

    @Value("${topics.queries.add}")
    private String ADD_QUERY;

    @Value("${topics.queries.get-by-id}")
    private String GET_BY_ID_QUERY;

    @Value("${topics.queries.insert-reply}")
    private String INSERT_REPLY_QUERY;

    @Value("${topics.queries.delete}")
    private String DELETE_QUERY;

    @Autowired
    public TopicDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Topic> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, extractor);
    }

    public void add(Topic topic, String text) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", topic.getName())
                .addValue("creator_id", topic.getCreator().getId())
                .addValue("date", topic.getDate())
                .addValue("text", text);
        jdbcTemplate.update(ADD_QUERY, parameterSource);
    }

    public Topic get(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(GET_BY_ID_QUERY, parameterSource, extractor)
                .stream().findAny().orElse(null);
    }

    public void addReply(TopicReply reply) {
        jdbcTemplate.update(INSERT_REPLY_QUERY, replyParameterFactory.newSqlParameterSource(reply));
    }

    public void delete(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(DELETE_QUERY, parameterSource);
    }
}
