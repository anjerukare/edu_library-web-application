package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.AuthorDao;
import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Author;
import edu.mtp.Library.models.User;
import edu.mtp.Library.util.Response;
import edu.mtp.Library.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

import static edu.mtp.Library.util.SecurityUtils.hasAnyRole;

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
                            @CurrentSecurityContext SecurityContext securityContext,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "authors/new";

        Authentication authentication = securityContext.getAuthentication();
        User publisher = new User();
        publisher.setId(userDao.getIdByUsername(authentication.getName()));
        author.setPublisher(publisher);

        if (hasAnyRole(authentication, "ROLE_MODERATOR", "ROLE_ADMIN")) {
            author.setModerator(publisher);
            author.setPublished(true);
            redirectAttributes.addFlashAttribute(new Response(Result.SUCCESS,
                    "Автор успешно добавлен"));
        } else {
            redirectAttributes.addFlashAttribute(new Response(Result.SUCCESS,
                    "Заявка на добавление автора успешно создана"));
        }

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

    @PostMapping("/{id}/publish")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public String setAuthorPublished(@PathVariable int id, Principal principal,
                                     RedirectAttributes redirectAttributes) {
        Integer moderatorId = userDao.getIdByUsername(principal.getName());
        authorDao.setPublished(id, moderatorId);

        redirectAttributes.addFlashAttribute(new Response(Result.SUCCESS,
                "Автор успешно добавлен в библиотеку"));
        return "redirect:/requests";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public String deleteAuthor(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (authorDao.hasBooks(id)) {
            redirectAttributes.addFlashAttribute(new Response(Result.ERROR,
                    "Невозможно удалить автора, который имеет книги"));
            return "redirect:/";
        }

        authorDao.delete(id);
        redirectAttributes.addFlashAttribute(new Response(Result.SUCCESS,
                "Автор успешно удалён"));
        return "redirect:/";
    }
}
