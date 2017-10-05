package com.example.chatclient.model;

public class ChatMessage {
    private User user;
    private String timestamp;
    private String message;
    private boolean myMessage;

    public ChatMessage(User user, String timestamp, String message, boolean myMessage) {
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
        this.myMessage = myMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMyMessage() {
        return myMessage;
    }

    public void setMyMessage(boolean myMessage) {
        this.myMessage = myMessage;
    }
}
