package edu.mtp.Library.dao;

import edu.mtp.Library.models.Author;
import edu.mtp.Library.models.Book;
import edu.mtp.Library.models.Tag;
import edu.mtp.Library.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    private static Book bookA;
    private static Book bookB;

    @BeforeAll
    static void beforeTestClass() {
        bookA = new Book();
        bookA.setName("Book A");

        Author author1 = new Author();
        author1.setId(7);
        List<Author> authorsA = Arrays.asList(author1);
        bookA.setAuthors(authorsA);

        Tag tag1 = new Tag();
        tag1.setId(10);
        Tag tag2 = new Tag();
        tag2.setId(9);
        List<Tag> tagsA = Arrays.asList(tag1, tag2);
        bookA.setTags(tagsA);

        bookA.setAnnotation("It's book A which did for tests");

        User publisherA = new User();
        publisherA.setId(1);
        bookA.setPublisher(publisherA);

        bookA.setPublished(false);

        bookB = new Book();
        bookB.setName("Book B");

        Author author2 = new Author();
        author2.setId(3);
        Author author3 = new Author();
        author3.setId(2);
        List<Author> authorsB = Arrays.asList(author2, author3);
        bookB.setAuthors(authorsB);

        Tag tag3 = new Tag();
        tag3.setId(1);
        Tag tag4 = new Tag();
        tag4.setId(2);
        List<Tag> tagsB = Arrays.asList(tag3, tag4);
        bookB.setTags(tagsB);

        bookB.setAnnotation("It's book B which did for tests");

        User publisherB = new User();
        publisherB.setId(1);
        bookB.setPublisher(publisherB);

        bookB.setPublished(true);
    }

    @Test
    void getAll() {
        List<Book> books = bookDao.getAll();
        books.forEach(System.out::println);
    }

    @Test
    void add() {
        bookDao.add(bookA);
    }

    @Test
    void get() {
        System.out.println(bookDao.get(1));
    }

    @Test
    void getByName() {
        System.out.println(bookDao.getBySearchQuery("у"));
        System.out.println(bookDao.getBySearchQuery("w"));
        System.out.println(bookDao.getBySearchQuery("т"));
    }

    @Test
    void save() {
        bookA.setId(38);
        bookDao.save(bookA);
    }

    @Test
    void delete() {
        bookDao.delete(38);
    }
}