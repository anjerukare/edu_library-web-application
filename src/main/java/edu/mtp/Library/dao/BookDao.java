package edu.mtp.Library.dao;

import edu.mtp.Library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("select * from books", new BeanPropertyRowMapper<>(Book.class));
    }

    public void add(Book book) {
        jdbcTemplate.update("insert into books(name, author, annotation, coverurl, bookurl) " +
                        "values(?, ?, ?, ?, ?)", book.getName(), book.getAuthor(), book.getAnnotation(),
                book.getCoverUrl(), book.getBookUrl());
    }

    public Book get(int id) {
        return jdbcTemplate.query("select * from books where id = ?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }

    public List<Book> getByNameOrAuthor(String query) {
        return jdbcTemplate.query("select * from books where lower(name) like lower(?) " +
                        "or lower(author) like lower(?)",
                new BeanPropertyRowMapper<>(Book.class), "%"+query+"%", "%"+query+"%");
    }

    public void set(int id, Book book) {
        jdbcTemplate.update("update books set name = ?, author = ?, annotation = ?, coverurl = ?, " +
                "bookurl = ? where id = ?", book.getName(), book.getAuthor(), book.getAnnotation(),
                book.getCoverUrl(), book.getBookUrl(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from books where id = ?", id);
    }
}
