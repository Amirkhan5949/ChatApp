package com.example.Model;

public class User {
    String address;
    String email;
    String user;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", user='" + user + '\'' +
                '}';
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
