package com.example.chatclient.service;

import android.util.Log;

import com.example.chatclient.App;
import com.example.chatclient.event.ResponseEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Zeus on 3/10/2017.
 */

public class MessReceiver implements Runnable {

    private Socket socket;
    private BufferedReader is;

    public MessReceiver() {
        socket = App.getSocket();
    }

    @Override
    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true) {
                String responseLine = is.readLine();
                EventBus.getDefault().post(new ResponseEvent(responseLine, true));
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
