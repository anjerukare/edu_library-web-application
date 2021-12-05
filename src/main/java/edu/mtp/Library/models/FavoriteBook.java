package edu.mtp.Library.models;

import java.util.Date;
import java.util.Objects;

public class FavoriteBook {

    private Book book;

    private Date date = new Date();

    /* Getters, setters, equals, hashCode and toString */
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteBook that = (FavoriteBook) o;
        return Objects.equals(book, that.book) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, date);
    }

    @Override
    public String toString() {
        return "FavoriteBook{" +
                "book=" + book +
                ", date=" + date +
                '}';
    }
}
