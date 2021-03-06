package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.AuthorDao;
import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Author;
import edu.mtp.Library.models.User;
import edu.mtp.Library.util.Response;
import edu.mtp.Library.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorDao authorDao;
    private final UserDao userDao;

    @Autowired
    public AuthorController(AuthorDao authorDao, UserDao userDao) {
        this.authorDao = authorDao;
        this.userDao = userDao;
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String newAuthor(@ModelAttribute Author author) {
        return "authors/new";
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addAuthor(@ModelAttribute @Valid Author author, BindingResult bindingResult,
                            Principal principal) {
        if (bindingResult.hasErrors())
            return "authors/new";

        User publisher = new User();
        publisher.setId(userDao.getIdByUsername(principal.getName()));
        author.setPublisher(publisher);

        authorDao.add(author);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editAuthor(@PathVariable int id, Model model) {
        Author author = authorDao.get(id);
        model.addAttribute("author", author);

        return "authors/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setAuthor(@ModelAttribute @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "authors/edit";

        authorDao.save(author);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAuthor(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (authorDao.hasBooks(id)) {
            redirectAttributes.addFlashAttribute(new Response(Result.ERROR,
                    "???????????????????? ?????????????? ????????????, ?????????????? ?????????? ??????????"));
            return "redirect:/";
        }

        authorDao.delete(id);
        return "redirect:/";
    }
}
