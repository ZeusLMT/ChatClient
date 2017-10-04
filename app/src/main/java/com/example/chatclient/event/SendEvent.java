package com.example.chatclient.event;

/**
 * Created by Zeus on 4/10/2017.
 */

public class SendEvent {
    private String message;
    private String command;

    public SendEvent(String message, String command) {
        this.message = message;
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
