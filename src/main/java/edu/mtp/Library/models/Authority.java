package edu.mtp.Library.models;

import java.util.Objects;

public class Authority {

    private Long userId;
    private String authority;

    public Authority(long userId, String authority) {
        this.userId = userId;
        this.authority = authority;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority1 = (Authority) o;
        return userId == authority1.userId && Objects.equals(authority, authority1.authority);
    }
}
