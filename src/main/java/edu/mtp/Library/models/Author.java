package edu.mtp.Library.models;

import java.util.Date;
import java.util.Objects;

public class Author {

    private int id;

    private String surname;

    private String name;

    private String patronymic;

    private User publisher;

    private Date createDate;

    private User moderator;

    private boolean published;

    public String getFullName() {
        return getFullName(false);
    }

    public String getFullName(boolean excludePatronymic) {
        if (excludePatronymic || Objects.isNull(patronymic))
            return name + ' ' + surname;
        else
            return surname + ' ' + name + ' ' + patronymic;
    }

    /* Getters, setters, equals, hashCode and to String */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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
        Author author = (Author) o;
        return id == author.id && published == author.published && Objects.equals(surname, author.surname) && Objects.equals(name, author.name) && Objects.equals(patronymic, author.patronymic) && Objects.equals(publisher, author.publisher) && Objects.equals(createDate, author.createDate) && Objects.equals(moderator, author.moderator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, publisher, createDate, moderator, published);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", publisher=" + publisher +
                ", createDate=" + createDate +
                ", moderator=" + moderator +
                ", published=" + published +
                '}';
    }
}
