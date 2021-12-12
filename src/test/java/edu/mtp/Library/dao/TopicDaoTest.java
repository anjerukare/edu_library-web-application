package edu.mtp.Library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TopicDaoTest {

    @Autowired
    private TopicDao topicDao;

    @Test
    void getAll() {
        topicDao.getAll().forEach(System.out::println);
    }

    @Test
    void get() {
        System.out.println(topicDao.get(2));
    }
}