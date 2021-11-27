package edu.mtp.Library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Test
    void getAll() {
        tagDao.getAll().forEach(System.out::println);
    }

    @Test
    void get() {
        System.out.println(tagDao.get(7));
    }
}