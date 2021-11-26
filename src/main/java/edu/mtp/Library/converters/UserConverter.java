package edu.mtp.Library.converters;

import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<String, User> {

    private final UserDao userDao;

    @Autowired
    public UserConverter(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User convert(String userId) {
        return userDao.get(Integer.parseInt(userId));
    }
}
