package com.example.chatclient.screens;

import com.example.chatclient.MessageEvent;
import com.example.chatclient.model.ChatMessage;

/**
 * Created by Zeus on 3/10/2017.
 */

public interface MainContract {
    interface View {

        void onConnectSuccess(ChatMessage chatMessage);

        void onConnectFailure(String error);
    }

    interface Presenter {

        void sendMessage(String message);

        void receiveMessage(MessageEvent event, String myAccount);

    }
}
