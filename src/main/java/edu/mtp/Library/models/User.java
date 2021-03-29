package edu.mtp.Library.models;

import javax.validation.constraints.Size;

public class User {
    private Long id;

    @Size(min = 2, max = 16, message = "Поле \"Имя пользователя\" должно иметь от 2 до 16 символов")
    private String username;

    @Size(min = 8, max = 64, message = "Поле \"Пароль\" должно иметь от 8 до 64 символов")
    private String password;

    private boolean enabled;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
