package com.example.lookbook1;

public class Contact {
    private int id;
    private String name;
    private String birthday;
    private String email;

    public Contact(int id, String name, String birthday, String email) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }
}
