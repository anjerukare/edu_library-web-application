package edu.mtp.Library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    void getAll() {
        authorDao.getAll().forEach(System.out::println);
    }
}