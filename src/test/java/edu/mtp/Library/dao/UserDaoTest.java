package edu.mtp.Library.dao;

import edu.mtp.Library.models.Role;
import edu.mtp.Library.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoTest {

    @Autowired
    UserDao userDao;

    static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user.setUsername("test-user");
        user.setPassword("$2a$12$JvJHlAt1CCwdbw4Mot7V7uWFZlxvmazlKNTuZ7v0eN0W/X1IMw6Hu");

        Role role = new Role();
        role.setId(3);
        user.setRole(role);
    }

    @Test
    void add() {
        userDao.add(user);
    }

    @Test
    void isExists() {
        Boolean exists = userDao.isExists("test-user");
        Boolean notExists = userDao.isExists("TEST_RESERVED");

        assertTrue(exists, "Пользователь не был найден");
        assertFalse(notExists);
    }

    @Test
    void getIdByUsername() {
        Integer id = userDao.getIdByUsername("test-user");

        assertEquals(4, id,
                "Идентификатор пользователя не совпал с ожидаемым значением");
    }

    @Test
    void getAll() {
        userDao.getAll().forEach(System.out::println);
    }

    @Test
    void get() {
        System.out.println(userDao.get(1));
        System.out.println(userDao.get(2));
    }
}