package com.mobdeve.s13.Group17.MCO2;

/*
MCO4
GROUP 17
CHUA, JASMIN
SHI, KAYE
TAN, HAILY
*/
public class User {

    private long id;
    public String fullName, username, email, password, bio;

    public User(){

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {this.fullName = fullName;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {this.username = username;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password;}

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {this.bio = bio;}

    public User (String fullName, String username, String email, String password, String bio){
        this.fullName=fullName;
        this.username=username;
        this.email=email;
        this.password=password;
        this.bio=bio;
        this.id = id;
    }
}
