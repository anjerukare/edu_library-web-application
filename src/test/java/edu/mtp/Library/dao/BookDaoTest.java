package edu.mtp.Library.dao;

import edu.mtp.Library.models.Author;
import edu.mtp.Library.models.Book;
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
        author1.setId(3);
        List<Author> authorsA = Arrays.asList(author1);
        bookA.setAuthors(authorsA);

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
        bookDao.add(bookB);
    }

    @Test
    void get() {
        System.out.println(bookDao.get(2));
    }

    @Test
    void getByName() {
        System.out.println(bookDao.getBySearchQuery("у"));
        System.out.println(bookDao.getBySearchQuery("w"));
        System.out.println(bookDao.getBySearchQuery("т"));
    }

    @Test
    void save() {
        bookA.setId(19);
        bookDao.save(bookA);
    }

    @Test
    void delete() {
        bookDao.delete(20);
    }
}