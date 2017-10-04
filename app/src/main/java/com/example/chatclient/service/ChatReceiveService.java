package com.example.chatclient.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chatclient.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Zeus on 3/10/2017.
 */

public class ChatReceiveService extends IntentService {

    private Socket socket;
    private Scanner scanner;

    private InputStream inputStream;
    private PrintStream outputStream;

    public static final String RESPONSE = "server_response";

    public ChatReceiveService() {
        super("ChatReceiveService");
        Log.i("abc", "onHandleIntentCons: ");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //Mock data
        for (int i = 0; i < 15; i++){
            if (i%2 == 0) {
                String responseLine = "My name is Tuan. I am from VietName, it is very nice to meet you fuck you from Tuan@1213";
                EventBus.getDefault().post(new MessageEvent(responseLine, true));
            } else {
                String responseLine = "a from abc@1213";
                EventBus.getDefault().post(new MessageEvent(responseLine, true));
            }
        }
//        try {
//            socket = new Socket(Config.SERVER_IP, Config.SERVER_PORT);
//            inputStream = socket.getInputStream();
//            outputStream = new PrintStream(socket.getOutputStream());
//            scanner = new Scanner(inputStream);
//            Log.i("abc", "onHandleIntent: ");
//
//            //Read from server
//            String responseLine = "My name is Huy from abc@1213";
//            while (true) {
//                responseLine = scanner.nextLine();
//                Log.i("abc", "onHandleIntentRes: " + responseLine);
//
//            }
//
//        }catch (UnknownHostException e) {
//            EventBus.getDefault().post(new MessageEvent(Error.ERROR_UNKNOWN_HOST, false));
//            Log.i("abc", "UnknownHostException: " + e.toString());
//            return;
//        } catch (IOException e) {
//            System.err.println("IOException:  " + e);
//            Log.i("abc", "IOException: " + e.toString());
//        }
    }
}
