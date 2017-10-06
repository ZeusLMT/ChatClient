package com.example.chatclient.server;

import com.example.chatclient.App;
import com.example.chatclient.event.ServerEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReceiverThread implements Runnable {

    private Socket socket;
    private BufferedReader is;

    public ReceiverThread() {
        socket = App.getSocket();
    }

    @Override
    public void run() {
        try {
            if (!socket.isConnected()) {
                socket.connect(new InetSocketAddress(ServerConfig.SERVER_IP, ServerConfig.SERVER_PORT));
            }

            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                String responseLine = is.readLine();
                if (responseLine != null) {
                    EventBus.getDefault().post(new ServerEvent(responseLine, true));
                }
            }

        } catch (UnknownHostException e) {
            EventBus.getDefault().post(new ServerEvent(ServerError.ERROR_UNKNOWN_HOST, false));
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to server");
            return;
        }

    }
}
