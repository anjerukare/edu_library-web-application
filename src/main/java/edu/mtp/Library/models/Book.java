package edu.mtp.Library.models;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Book {

    private int id;

    @Size(min = 2, max = 32, message = "Поле \"Имя\" должно иметь от 2 до 32 символов")
    private String name;

    private List<Author> authors = new ArrayList<>();

    @Size(min = 8, max = 256, message = "Поле \"Описание\" должно иметь от 8 до 256 символов")
    private String annotation;

    private String coverUrl;

    private String bookUrl;

    private User publisher;

    private Date createDate = new Date();

    private User moderator;

    private boolean published = false;

    /* Getters, setters, equals, hashCode and to String */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && published == book.published && Objects.equals(name, book.name) && Objects.equals(authors, book.authors) && Objects.equals(annotation, book.annotation) && Objects.equals(coverUrl, book.coverUrl) && Objects.equals(bookUrl, book.bookUrl) && Objects.equals(publisher, book.publisher) && Objects.equals(createDate, book.createDate) && Objects.equals(moderator, book.moderator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authors, annotation, coverUrl, bookUrl, publisher, createDate, moderator, published);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", annotation='" + annotation + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                ", publisher=" + publisher +
                ", createDate=" + createDate +
                ", moderator=" + moderator +
                ", published=" + published +
                '}';
    }
}
