package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Role;
import edu.mtp.Library.models.User;
import edu.mtp.Library.util.UserReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.Random;

import static java.lang.Math.abs;

@Controller
public class UserController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    private final static Random random = new Random();

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

    @GetMapping("/users/{username}")
    @PreAuthorize("isAuthenticated()")
    public String getUserProfile(@PathVariable String username, Model model) {
        model.addAttribute("user", userDao.get(userDao.getIdByUsername(username)));
        model.addAttribute("random", abs(random.nextInt()));
        return "users/profile";
    }

    @GetMapping("/users/{username}/userinfo")
    @PreAuthorize("authentication.principal.username == #username")
    @ResponseBody
    public FileSystemResource getUserInfo(@PathVariable String username, HttpServletResponse response) {
        File file = UserReport.generate(userDao.get(userDao.getIdByUsername(username)));
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + username + ".csv");
        return new FileSystemResource(file);
    }
}
