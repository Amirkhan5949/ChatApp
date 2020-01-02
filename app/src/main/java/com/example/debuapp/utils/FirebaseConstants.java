package com.example.debuapp.utils;

public class FirebaseConstants {

    public static class User{
        public static String key = "User";
        public static String email = "email";
        public static  String address="address";
        public static  String user="user";
        public static   String number="number";
    }

    public static class Message{
        public static String key = "Message";
        public static String status = "status";
        public static String message = "message";
        public static String type = "type";

        public static enum  Type{
            RECEIVE,SEND
        }
    }



}
