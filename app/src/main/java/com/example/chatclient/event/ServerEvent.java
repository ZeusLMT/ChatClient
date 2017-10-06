package com.example.chatclient.event;

public  class ServerEvent {
    private String serverMessage;
    private boolean connectSuccess;

    public ServerEvent(String serverMessage, boolean connectSuccess) {
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
