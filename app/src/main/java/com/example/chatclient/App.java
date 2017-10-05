package com.example.chatclient;

import android.app.Application;

import java.net.Socket;

public class App extends Application {
    private static Socket socket;

    public App() {
        socket = new Socket();
    }

    public static Socket getSocket() {
        return socket;
    }

}
