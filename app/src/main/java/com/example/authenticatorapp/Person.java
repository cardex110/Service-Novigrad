package com.example.authenticatorapp;

public class Person {
    private String username;
    private String email;
    private String password;
    private String job;

    public Person(String username, String email, String password, String job) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.job = job;
    }

    public Person(){

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getJob() {
        return job;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
