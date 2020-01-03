package com.example.Model;

public class Message {

    String message;
     Boolean status ;
     String type;


    public Message() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType(){ return type;}
    public void setType(String type){ this.type=type;}
}
