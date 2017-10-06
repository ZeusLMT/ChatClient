package com.example.chatclient.event;

public  class ResponseEvent {
    private String serverMessage;
    private boolean connectSuccess;

    public ResponseEvent(String serverMessage, boolean connectSuccess) {
        this.serverMessage = serverMessage;
        this.connectSuccess = connectSuccess;
    }

    public String getServerMessage() {
        return serverMessage;
    }


    public boolean isConnectSuccess() {
        return connectSuccess;
    }

}
