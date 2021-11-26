package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Role;
import edu.mtp.Library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
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
    public String register(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (userDao.isExists(user.getUsername()))
            bindingResult.rejectValue("username", "error.user",
                    "Пользователь с таким именем уже существует");
        if (bindingResult.hasErrors())
            return "users/signup";

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setId(3);
        user.setRole(role);

        userDao.add(user);

        return "redirect:/signin";
    }
}
