package com.example.demo.web.records;

public record User(String username,
                   String password,
                   String email,
                   String id,
                   String firstName,
                   String lastName,
                   String phone) {
    public User(String username, String password, String email) {
        this(username, password, email,null, null, null, null);
    }

    public User withId(String id) {
        return new User(username, password, email, id, firstName, lastName, phone);
    }
}
