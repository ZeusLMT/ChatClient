package com.example.chatclient.screens.main;

import com.example.chatclient.event.ResponseEvent;
import com.example.chatclient.model.ChatMessage;

public interface MainContract {
    interface View {

        void showMess(ChatMessage chatMessage);

        void showError(String error);

        void logOutSuccess(String success);

        void showUserList(String users);
    }

    interface Presenter {

        void sendMessage(String message);

        void receiveMessage(ResponseEvent event, String myAccount);

        void logout(String userName);

        void getUserList();

        void loadHistory();

    }
}
