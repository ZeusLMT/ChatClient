package com.example.chatclient.service;

import com.example.chatclient.App;
import com.example.chatclient.Config;
import com.example.chatclient.event.ResponseEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessReceiver implements Runnable {

    private Socket socket;
    private BufferedReader is;

    public MessReceiver() {
        socket = App.getSocket();
    }

    @Override
    public void run() {
        try {
            if (!socket.isConnected()) {
                socket.connect(new InetSocketAddress(Config.SERVER_IP, Config.SERVER_PORT));
            }

            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                String responseLine = is.readLine();
                if (responseLine != null) {
                    EventBus.getDefault().post(new ResponseEvent(responseLine, true));
                }
            }

        } catch (UnknownHostException e) {
            EventBus.getDefault().post(new ResponseEvent(Error.ERROR_UNKNOWN_HOST, false));
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to server");
            return;
        }

    }
}
