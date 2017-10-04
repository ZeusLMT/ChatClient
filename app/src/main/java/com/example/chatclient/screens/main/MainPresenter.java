package com.example.chatclient.screens.main;

import android.util.Log;

import com.example.chatclient.event.MessageEvent;
import com.example.chatclient.model.ChatMessage;
import com.example.chatclient.model.User;
import com.example.chatclient.util.ServerUtil;

/**
 * Created by Zeus on 3/10/2017.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    public void sendMessage(String message) {
        //TODO: send message to server
    }

    @Override
    public void receiveMessage(MessageEvent event, String myAccount) {
        //TODO: receive message from server
        if (event.isConnectSuccess()) {
            String serverMessage = event.getServerMessage();
            String username = ServerUtil.parseUsername(serverMessage);
            long timestamp = ServerUtil.parseTimeStamp(serverMessage);
            String messageContent = ServerUtil.parseMessage(serverMessage);

            boolean myMessage;

            Log.i("abc", "myAccount: " + myAccount);
            if(username.equals(myAccount)) {
                myMessage = true;
            } else {
                myMessage = false;
            }

            ChatMessage chatMessage = new ChatMessage(new User(username), timestamp, messageContent, myMessage);
            view.onConnectSuccess(chatMessage);

        } else {
            view.onConnectFailure(event.getServerMessage());
        }

    }

    @Override
    public void logout(String userName) {
        view.logOutSuccess("Logout successfully");
    }
}
