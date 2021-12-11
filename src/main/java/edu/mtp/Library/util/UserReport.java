package edu.mtp.Library.util;

import edu.mtp.Library.models.Author;
import edu.mtp.Library.models.FavoriteBook;
import edu.mtp.Library.models.StatusBook;
import edu.mtp.Library.models.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserReport {

    private final static String TEMP_PATH = "tmp/";

    public static File generate(User user) {
        File file = new File(TEMP_PATH + user.getUsername() + ".csv");
        List<FavoriteBook> favoriteBooks = user.getFavoriteBooks();
        favoriteBooks.sort(Comparator.comparing(FavoriteBook::getDate));
        List<StatusBook> statusBooks = user.getStatusBooks();
        statusBooks.sort(Comparator.comparing(StatusBook::getDate));

        try (CSVPrinter printer = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT)) {
            printer.printRecord("Название книги", "Авторы", "Дата добавления");
            for (FavoriteBook favoriteBook : favoriteBooks) {
                printer.printRecord(favoriteBook.getBook().getName(),
                        getAuthorsAsString(favoriteBook.getBook().getAuthors()), favoriteBook.getDate());
            }
            printer.println();

            printer.printRecord("Статус", "Кол-во книг");
            for (Status status : Status.values())
                printer.printRecord(status, user.getStatusBooksCountByStatusName(status.name()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private static String getAuthorsAsString(List<Author> authors) {
        return authors.stream()
                .map(Author::getFullName)
                .collect(Collectors.joining(", "));
    }
}
