package com.example.chatclient.service;

import com.example.chatclient.App;
import com.example.chatclient.Config;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by HUYTRINH on 10/4/2017.
 */

public class MessSender implements Runnable {
    private Socket socket;
    private String msgOut;
    private PrintWriter out;

    public MessSender(String msgOut) {
        socket = App.getSocket();
        this.msgOut = msgOut;
    }

    @Override
    public void run() {
        try {
            if (!socket.isConnected()) {
                socket.connect(new InetSocketAddress(Config.SERVER_IP, Config.SERVER_PORT));
            }

            out = new PrintWriter(socket.getOutputStream());

            out.println(msgOut);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
