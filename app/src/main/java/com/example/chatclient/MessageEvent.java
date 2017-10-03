package com.example.chatclient;

/**
 * Created by Zeus on 3/10/2017.
 */

public  class MessageEvent {
    private String serverMessage;
    private boolean connectSuccess;

    public MessageEvent(String serverMessage, boolean connectSuccess) {
        this.serverMessage = serverMessage;
        this.connectSuccess = connectSuccess;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public boolean isConnectSuccess() {
        return connectSuccess;
    }

    public void setConnectSuccess(boolean connectSuccess) {
        this.connectSuccess = connectSuccess;
    }
}
