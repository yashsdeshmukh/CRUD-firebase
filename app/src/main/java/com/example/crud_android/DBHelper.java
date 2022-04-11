package com.example.crud_android;

public class DBHelper {
    private String userName;
    private String email;
    private String number;

    public DBHelper(String userName, String email, String number) {
        this.userName = userName;
        this.email = email;
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }
}
