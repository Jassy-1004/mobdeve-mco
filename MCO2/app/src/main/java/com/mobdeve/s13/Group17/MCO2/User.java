package com.mobdeve.s13.Group17.MCO2;

public class User {
    public String fullName, username, email, password, bio;

    public User(){

    }

    public String getFullName() {
        return fullName;
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

    public String getBio() {
        return bio;
    }

    public User (String fullName, String username, String email, String password, String bio){
        this.fullName=fullName;
        this.username=username;
        this.email=email;
        this.password=password;
        this.bio=bio;

    }
}
