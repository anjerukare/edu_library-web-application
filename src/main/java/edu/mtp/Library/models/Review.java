package edu.mtp.Library.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Review {

    private int id;

    private Book book;

    private User reviewer;

    @Size(min = 4, max = 32, message = "Поле \"Заголовок\" должно иметь от {min} до {max} символов")
    private String header;

    @Size(min = 16, max = 1024, message = "Поле \"Текст\" должно иметь от {min} до {max} символов")
    private String text;

    @Min(value = 1, message = "Поле \"Оценка\" должно быть числом в диапазоне от 1 до 5")
    @Max(value = 5, message = "Поле \"Оценка\" должно быть числом в диапазоне от 1 до 5")
    private int score = 5;

    /* Getters, setters, equals, hashCode and toString */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && score == review.score && Objects.equals(book, review.book) && Objects.equals(reviewer, review.reviewer) && Objects.equals(header, review.header) && Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, reviewer, header, text, score);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", bookId=" + book.getId() +
                ", reviewer=" + reviewer +
                ", header='" + header + '\'' +
                ", text='" + text + '\'' +
                ", score=" + score +
                '}';
    }
}
