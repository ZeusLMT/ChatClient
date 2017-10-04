package com.example.chatclient.screens.main;

import com.example.chatclient.event.MessageEvent;
import com.example.chatclient.model.ChatMessage;

/**
 * Created by Zeus on 3/10/2017.
 */

public interface MainContract {
    interface View {

        void onConnectSuccess(ChatMessage chatMessage);

        void onConnectFailure(String error);

        void logOutSuccess(String success);
    }

    interface Presenter {

        void sendMessage(String message);

        void receiveMessage(MessageEvent event, String myAccount);

        void logout(String userName);

    }
}
