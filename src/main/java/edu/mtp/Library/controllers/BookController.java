package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.BookDao;
import edu.mtp.Library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final static Random random = new Random();

    @Autowired
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping
    public String allBooks(Model model) {
        List<Book> books = bookDao.getAll();
        books.sort(Comparator.comparing(Book::getName));
        model.addAttribute("books", books);
        model.addAttribute("random", abs(random.nextInt()));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDao.get(id));
        model.addAttribute("random", abs(random.nextInt()));
        return "books/get";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("q") String query,
                              Model model) {
        if (query.equals(""))
            return "redirect:/books";

        List<Book> books = bookDao.getByNameOrAuthor(query);
        books.sort(Comparator.comparing(Book::getName));
        model.addAttribute("books", books);
        model.addAttribute("random", abs(random.nextInt()));
        model.addAttribute("q", query);
        return "books/search";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITER')")
    public String newBook(@ModelAttribute Book book) {
        return "books/new";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITER')")
    public String addBook(@ModelAttribute @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDao.add(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDao.get(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String setBook(@PathVariable int id,
                          @ModelAttribute @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDao.set(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteBook(@PathVariable int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
}
