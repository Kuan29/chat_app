package com.example.lab2022_2sub;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {



    public Map<String,Boolean> users = new HashMap<>(); //채팅방의 유저들
    public Map<String,Comment> comments = new HashMap<>();//채팅방의 대화내용


    public static class Comment {

        public String uid;
        public String name;
        public String message;
        public String time;
    }

}
