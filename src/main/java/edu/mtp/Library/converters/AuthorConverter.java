package edu.mtp.Library.converters;

import edu.mtp.Library.dao.AuthorDao;
import edu.mtp.Library.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<String, Author> {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorConverter(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Author convert(String authorId) {
        return authorDao.get(Integer.parseInt(authorId));
    }
}
