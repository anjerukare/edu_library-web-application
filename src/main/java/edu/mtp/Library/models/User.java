package edu.mtp.Library.models;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class User {

    private int id;

    @Size(min = 2, max = 16, message = "Поле \"Имя пользователя\" должно иметь от {min} до {max} символов")
    private String username;

    @Size(min = 8, max = 64, message = "Поле \"Пароль\" должно иметь от {min} до {max} символов")
    private String password;

    private Role role;

    private List<FavoriteBook> favoriteBooks = new ArrayList<>();

    private List<StatusBook> statusBooks = new ArrayList<>();

    /* Getters, setters, equals, hashCode and toString */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<FavoriteBook> getFavoriteBooks() {
        return favoriteBooks;
    }

    public List<Integer> getFavoriteBooksIds() {
        return favoriteBooks.stream()
                .map(FavoriteBook::getBook)
                .map(Book::getId)
                .collect(Collectors.toList());
    }

    public void setFavoriteBooks(List<FavoriteBook> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public List<StatusBook> getStatusBooks() {
        return statusBooks;
    }

    public List<Integer> getStatusBooksIds() {
        return statusBooks.stream()
                .map(StatusBook::getBook)
                .map(Book::getId)
                .collect(Collectors.toList());
    }

    public String getStatusNameByBookId(int id) {
        return statusBooks.stream()
                .filter(statusBook -> statusBook.getBook().getId() == id)
                .map(StatusBook::getStatus)
                .map(Status::getName)
                .findAny().orElse(null);
    }

    public void setStatusBooks(List<StatusBook> statusBooks) {
        this.statusBooks = statusBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(favoriteBooks, user.favoriteBooks) && Objects.equals(statusBooks, user.statusBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, favoriteBooks, statusBooks);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", favoriteBooks=" + favoriteBooks +
                ", statusBooks=" + statusBooks +
                '}';
    }
}
