package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.*;
import edu.mtp.Library.models.Book;
import edu.mtp.Library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Random;

import static edu.mtp.Library.util.SecurityUtils.hasAnyRole;
import static java.lang.Math.abs;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final TagDao tagDao;
    private final UserDao userDao;
    private final StatusDao statusDao;

    private final static Random random = new Random();
    private final AuthenticationTrustResolver trustResolver;

    @Autowired
    public BookController(BookDao bookDao, AuthorDao authorDao, TagDao tagDao, UserDao userDao,
                          StatusDao statusDao, AuthenticationTrustResolver trustResolver) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.tagDao = tagDao;
        this.userDao = userDao;
        this.statusDao = statusDao;
        this.trustResolver = trustResolver;
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable int id, Model model,
                          @CurrentSecurityContext SecurityContext securityContext) {
        model.addAttribute("book", bookDao.get(id));
        model.addAttribute("random", abs(random.nextInt()));

        Authentication authentication = securityContext.getAuthentication();
        if (!trustResolver.isAnonymous(authentication)) {
            Integer userId = userDao.getIdByUsername(authentication.getName());
            model.addAttribute("user", userDao.get(userId));
            model.addAttribute("statuses", statusDao.getAll());
        }

        return "books/get";
    }

    @PostMapping("/{id}/favorite")
    public String setBookFavorite(@PathVariable int id, @RequestParam boolean favorite,
                                  Principal principal) {
        Integer userId = userDao.getIdByUsername(principal.getName());
        if (favorite)
            userDao.insertBookToFavorites(userId, id);
        else
            userDao.deleteBookFromFavorites(userId, id);

        return "redirect:/books/{id}";
    }

    @PostMapping("/{id}/status")
    public String setBookStatus(@PathVariable int id, @RequestParam int status,
                                Principal principal) {
        Integer userId = userDao.getIdByUsername(principal.getName());
        if (status == 0)
            userDao.deleteStatusFromBook(userId, id);
        else
            userDao.insertStatusForBook(userId, id, status);

        return "redirect:/books/{id}";
    }

    @PostMapping("/{id}/publish")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public String setBookPublished(@PathVariable int id, Principal principal) {
        Integer moderatorId = userDao.getIdByUsername(principal.getName());
        bookDao.setPublished(id, moderatorId);

        return "redirect:/requests";
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String newBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("allAuthors", authorDao.getAll());
        model.addAttribute("allTags", tagDao.getAll());
        return "books/new";
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addBook(@ModelAttribute @Valid Book book, BindingResult bindingResult,
                          Model model, @CurrentSecurityContext SecurityContext securityContext) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allAuthors", authorDao.getAll());
            model.addAttribute("allTags", tagDao.getAll());
            return "books/new";
        }

        Authentication authentication = securityContext.getAuthentication();
        User publisher = new User();
        publisher.setId(userDao.getIdByUsername(authentication.getName()));
        book.setPublisher(publisher);

        if (hasAnyRole(authentication, "ROLE_MODERATOR", "ROLE_ADMIN")) {
            book.setModerator(publisher);
            book.setPublished(true);
        }

        bookDao.add(book);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBook(@PathVariable int id, Model model) {
        Book book = bookDao.get(id);
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authorDao.getAll());
        model.addAttribute("allTags", tagDao.getAll());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setBook(@ModelAttribute @Valid Book book, BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allAuthors", authorDao.getAll());
            model.addAttribute("allTags", tagDao.getAll());
            return "books/edit";
        }

        bookDao.save(book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public String deleteBook(@PathVariable int id) {
        bookDao.delete(id);
        return "redirect:/";
    }
}
