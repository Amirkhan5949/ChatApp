package com.example.Model;

public class Username {
    String image;
    String username;
    String status;

    public Username(String image, String username, String status) {
        this.image = image;
        this.username = username;
        this.status = status;
    }

    public Username() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
