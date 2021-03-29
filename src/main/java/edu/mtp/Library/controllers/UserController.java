package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.AuthorityDao;
import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Authority;
import edu.mtp.Library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserDao userDao;
    private final AuthorityDao authorityDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDao userDao, AuthorityDao authorityDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.authorityDao = authorityDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signin")
    public String getSignInView() {
        return "users/signin";
    }

    @GetMapping("/signup")
    public String getSignUpView(@ModelAttribute User user) {
        return "users/signup";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute @Valid User user,
                           BindingResult bindingResult, @RequestParam String role) {
        if (bindingResult.hasErrors()) {
            return "users/signup";
        }
        else if (userDao.isExists(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Пользователь с таким именем уже существует");
            return "users/signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
        Long userId = userDao.getIdByUsername(user.getUsername());
        switch (role) {
            case "writer":
                authorityDao.add(new Authority(userId, "ROLE_WRITER"));
                break;
            default:
                authorityDao.add(new Authority(userId, "ROLE_READER"));
                break;
        }
        return "redirect:/signin";
    }
}