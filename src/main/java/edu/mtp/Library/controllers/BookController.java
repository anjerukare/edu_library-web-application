package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.AuthorDao;
import edu.mtp.Library.dao.BookDao;
import edu.mtp.Library.dao.TagDao;
import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Book;
import edu.mtp.Library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Random;

import static java.lang.Math.abs;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final TagDao tagDao;
    private final UserDao userDao;

    private final static Random random = new Random();

    @Autowired
    public BookController(BookDao bookDao, AuthorDao authorDao, TagDao tagDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.tagDao = tagDao;
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDao.get(id));
        model.addAttribute("random", abs(random.nextInt()));
        return "books/get";
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
                          Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allAuthors", authorDao.getAll());
            model.addAttribute("allTags", tagDao.getAll());
            return "books/new";
        }

        User publisher = new User();
        publisher.setId(userDao.getIdByUsername(principal.getName()));
        book.setPublisher(publisher);

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBook(@PathVariable int id) {
        bookDao.delete(id);
        return "redirect:/";
    }
}
