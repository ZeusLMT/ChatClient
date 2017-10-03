package com.example.chatclient.model;

/**
 * Created by Zeus on 3/10/2017.
 */

public class ChatMessage {
    private User user;
    private long timestamp;
    private String message;
    private boolean myMessage;

    public ChatMessage(User user, long timestamp, String message, boolean myMessage) {
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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
