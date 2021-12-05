package edu.mtp.Library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatusDaoTest {

    @Autowired
    private StatusDao statusDao;

    @Test
    void getAll() {
        System.out.println(statusDao.getAll());
    }

    @Test
    void get() {
        System.out.println(statusDao.get(3));
    }
}