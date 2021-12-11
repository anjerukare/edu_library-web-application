package edu.mtp.Library.util;

public enum Status {

    PLANNED("В планах"),
    READING_NOW("Читаю"),
    DELAYED("Отложено"),
    QUIT("Брошено"),
    READ("Прочитано");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
