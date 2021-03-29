package edu.mtp.Library.models;

import javax.validation.constraints.Size;

public class Book {
    private int id;

    @Size(min = 2, max = 32, message = "Поле \"Имя\" должно иметь от 2 до 32 символов")
    private String name;

    @Size(min = 4, max = 32, message = "Поле \"Автор\" должно иметь от 4 до 32 символов")
    private String author;

    @Size(min = 8, max = 512, message = "Поле \"Описание\" должно иметь от 8 до 512 символов")
    private String description;

    private String coverUrl;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
