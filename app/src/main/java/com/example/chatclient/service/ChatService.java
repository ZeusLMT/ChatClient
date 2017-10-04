package com.example.chatclient.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chatclient.Config;
import com.example.chatclient.event.ResponseEvent;
import com.example.chatclient.event.SendEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Zeus on 3/10/2017.
 */

public class ChatService extends IntentService {

    private Socket socket;
    private BufferedWriter os;
    private BufferedReader is;

    public ChatService() {
        super("ChatService");
        Log.i("abc", "onHandleIntentCons: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        
        try {
            socket = new Socket(Config.SERVER_IP, Config.SERVER_PORT);
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            EventBus.getDefault().post(new ResponseEvent(Error.ERROR_UNKNOWN_HOST, false));
            Log.i("abc", "UnknownHostException: " + e.toString());
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to server");
            return;
        }

        try {

            os.write(":user Tr");
            os.newLine();
            os.flush();
            os.write("sadsadsadsa");
            os.newLine();
            os.flush();
            os.write("sadsadsadsa");
            os.newLine();
            os.flush();
            os.write("sadsadsadsa");
            os.newLine();
            os.flush();
            os.write("sadsadsadsa");
            os.newLine();
            os.flush();

            String responseLine;
            while((responseLine = is.readLine()) != null) {
                Log.i("abc", "onHandleIntent: " + responseLine);
                EventBus.getDefault().post(new ResponseEvent(responseLine, true));
            }
//            os.close();
//            is.close();
//            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
            Log.i("abc", "IOException: " + e.toString());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendEvent event) {
        Log.i("abc", "onMessageEvent: " + event.getCommand() + event.getMessage());
//        try {
//            String command = event.getCommand();
//            String mess =  event.getMessage();
//            Log.i("abc", "onMessageEvent: " + command + mess);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
