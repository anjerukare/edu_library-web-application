package edu.mtp.Library.dao;

import edu.mtp.Library.models.Book;
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
@PropertySource("classpath:/sql/books.properties")
public class BookDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ResultSetExtractorImpl<Book> extractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id", "authors_id", "authors_publisher_id", "authors_publisher_role_id",
                            "authors_moderator_id", "authors_moderator_role_id", "tags_id",
                            "publisher_id", "publisher_role_id", "moderator_id", "moderator_role_id")
                    .newResultSetExtractor(Book.class);

    private final SqlParameterSourceFactory<Book> parameterFactory =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .newSqlParameterSourceFactory(Book.class);

    @Value("${books.queries.get-all}")
    private String GET_ALL_QUERY;

    @Value("${books.query-pieces.insert-book}")
    private String INSERT_BOOK_QUERY_PIECE;

    @Value("${books.query-pieces.add-junction-with-author.i}")
    private String ADD_JUNCTION_WITH_AUTHOR_I_QUERY_PIECE;

    @Value("${books.query-pieces.add-junction-with-tag.i}")
    private String ADD_JUNCTION_WITH_TAG_I_QUERY_PIECE;

    @Value("${books.query-pieces.add-junction-with-author.n}")
    private String ADD_JUNCTION_WITH_AUTHOR_N_QUERY_PIECE;

    @Value("${books.queries.get-by-id}")
    private String GET_BY_ID_QUERY;

    @Value("${books.queries.search}")
    private String SEARCH_QUERY;

    @Value("${books.query-pieces.update-book}")
    private String UPDATE_BOOK_QUERY_PIECE;

    @Value("${books.query-pieces.delete-junctions-with-tags}")
    private String DELETE_JUNCTIONS_WITH_TAGS_QUERY_PIECE;

    @Value("${books.query-pieces.delete-junctions-with-authors}")
    private String DELETE_JUNCTIONS_WITH_AUTHORS_QUERY_PIECE;

    @Value("${books.queries.set-published}")
    private String SET_PUBLISHED_QUERY;

    @Value("${books.query-pieces.delete-book-form-favorites}")
    private String DELETE_BOOK_FROM_FAVORITES_QUERY_PIECES;

    @Value("${books.query-pieces.delete-statuses-from-book}")
    private String DELETE_STATUSES_FROM_BOOK_QUERY_PIECES;

    @Value("${books.query-pieces.delete-book}")
    private String DELETE_BOOK_QUERY_PIECE;

    @Autowired
    public BookDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return getAll(true);
    }

    public List<Book> getAll(boolean published) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("published", published);
        return jdbcTemplate.query(GET_ALL_QUERY, parameterSource, extractor);
    }

    public void add(Book book) {
        String query = buildAddQuery(book.getAuthors().size(), book.getTags().size());
        jdbcTemplate.update(query, parameterFactory.newSqlParameterSource(book));
    }

    private String buildAddQuery(int authorsCount, int tagsCount) {
        StringBuilder builder = new StringBuilder(INSERT_BOOK_QUERY_PIECE);
        for (int i = 0; i < authorsCount - 1; ++i)
            builder.append(String.format(ADD_JUNCTION_WITH_AUTHOR_I_QUERY_PIECE, i));
        for (int i = 0; i < tagsCount; ++i)
            builder.append(String.format(ADD_JUNCTION_WITH_TAG_I_QUERY_PIECE, i));
        builder.append(String.format(ADD_JUNCTION_WITH_AUTHOR_N_QUERY_PIECE, authorsCount - 1));
        return builder.toString();
    }

    public Book get(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(GET_BY_ID_QUERY, parameterSource, extractor)
                .stream().findAny().orElse(null);
    }

    public List<Book> getBySearchQuery(String query) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("query", query);
        return jdbcTemplate.query(SEARCH_QUERY, parameterSource, extractor);
    }

    public void save(Book book) {
        String sql = buildSaveQuery(book.getAuthors().size(), book.getTags().size());
        jdbcTemplate.update(sql, parameterFactory.newSqlParameterSource(book));
    }

    private String buildSaveQuery(int authorsCount, int tagsCount) {
        StringBuilder builder = new StringBuilder(UPDATE_BOOK_QUERY_PIECE);
        for (int i = 0; i < authorsCount; ++i)
            builder.append(String.format(ADD_JUNCTION_WITH_AUTHOR_I_QUERY_PIECE, i));
        for (int i = 0; i < tagsCount; ++i)
            builder.append(String.format(ADD_JUNCTION_WITH_TAG_I_QUERY_PIECE, i));
        builder.append(DELETE_JUNCTIONS_WITH_TAGS_QUERY_PIECE);
        builder.append(DELETE_JUNCTIONS_WITH_AUTHORS_QUERY_PIECE);
        return builder.toString();
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
        jdbcTemplate.update(DELETE_BOOK_QUERY_PIECE + DELETE_BOOK_FROM_FAVORITES_QUERY_PIECES +
                DELETE_STATUSES_FROM_BOOK_QUERY_PIECES + DELETE_JUNCTIONS_WITH_TAGS_QUERY_PIECE +
                DELETE_JUNCTIONS_WITH_AUTHORS_QUERY_PIECE, parameterSource);
    }
}
