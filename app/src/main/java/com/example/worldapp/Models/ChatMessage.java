package com.example.worldapp.Models;

import java.util.Date;

public class ChatMessage {
    private String mMessageUser;
    private String mMessageText;
    private long mMessageTime;

    public ChatMessage(String mMessageUser, String mMessageText) {
        this.mMessageUser = mMessageUser;
        this.mMessageText = mMessageText;
        this.mMessageTime = new Date().getTime();
    }

    public ChatMessage() {
    }
}
