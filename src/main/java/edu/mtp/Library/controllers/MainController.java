package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.AuthorDao;
import edu.mtp.Library.dao.BookDao;
import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Author;
import edu.mtp.Library.models.Book;
import edu.mtp.Library.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

@Controller
@RequestMapping("/")
public class MainController {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final UserDao userDao;

    private final static Random random = new Random();

    @Autowired
    public MainController(BookDao bookDao, AuthorDao authorDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.userDao = userDao;
    }

    @GetMapping
    public String index(Model model, @ModelAttribute Response response) {
        List<Book> books = bookDao.getAll();
        books.sort(Comparator.comparing(Book::getName));
        model.addAttribute("books", books);
        List<Author> authors = authorDao.getAll();
        authors.sort(Comparator.comparing(Author::getFullName));
        model.addAttribute("authors", authors);
        model.addAttribute("random", abs(random.nextInt()));

        return "index";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("q") String query,
                              Model model) {
        if (query.equals(""))
            return "redirect:/";

        List<Book> books = bookDao.getBySearchQuery(query);
        books.sort(Comparator.comparing(Book::getName));
        model.addAttribute("books", books);
        model.addAttribute("random", abs(random.nextInt()));
        model.addAttribute("q", query);

        return "search";
    }
}
