package edu.mtp.Library.util;

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

public class UserReport {

    private final static String TEMP_PATH = "tmp/";

    public static File generate(User user) {
        File file = new File(TEMP_PATH + user.getUsername() + ".csv");
        List<FavoriteBook> favoriteBooks = user.getFavoriteBooks();
        favoriteBooks.sort(Comparator.comparing(FavoriteBook::getDate));
        List<StatusBook> statusBooks = user.getStatusBooks();
        statusBooks.sort(Comparator.comparing(StatusBook::getDate));

        try (CSVPrinter printer = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT)) {
            printer.printRecord("Идентификатор", "Дата добавления");
            for (FavoriteBook favoriteBook : favoriteBooks)
                printer.printRecord(favoriteBook.getBook().getId(), favoriteBook.getDate());
            printer.println();

            printer.printRecord("Идентификатор", "Статус", "Дата установления");
            for (StatusBook statusBook : statusBooks)
                printer.printRecord(statusBook.getBook().getId(), statusBook.getStatus().getName(),
                        statusBook.getDate());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
