package edu.mtp.Library.models;

import java.util.Date;
import java.util.Objects;

public class StatusBook {

    private Book book;

    private Status status;

    private Date date = new Date();

    /* Getters, setters, equals, hashCode and toString */
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        StatusBook that = (StatusBook) o;
        return Objects.equals(book, that.book) && Objects.equals(status, that.status) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, status, date);
    }

    @Override
    public String toString() {
        return "StatusBook{" +
                "book=" + book +
                ", status=" + status +
                ", date=" + date +
                '}';
    }
}
