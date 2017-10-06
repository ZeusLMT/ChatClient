package com.example.chatclient.server;

import com.example.chatclient.App;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SenderThread implements Runnable {
    private Socket socket;
    private String msgOut;
    private PrintWriter out;

    public SenderThread(String msgOut) {
        socket = App.getSocket();
        this.msgOut = msgOut;
    }

    @Override
    public void run() {
        try {
            if (!socket.isConnected()) {
                socket.connect(new InetSocketAddress(ServerConfig.SERVER_IP, ServerConfig.SERVER_PORT));
            }

            out = new PrintWriter(socket.getOutputStream());

            out.println(msgOut);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
