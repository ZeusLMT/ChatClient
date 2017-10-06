package com.example.chatclient.screens.main;

import com.example.chatclient.event.ServerEvent;
import com.example.chatclient.model.Message;
import com.example.chatclient.model.OnlineList;

public interface MainContract {
    interface View {

        void showMess(Message chatMessage);

        void showError(String error);

        void logOutSuccess(String success);

        void showUserList(OnlineList onlineList);
    }

    interface Presenter {

        void sendMessage(String message);

        void receiveMessage(ServerEvent event, String myAccount);

        void logout(String userName);

        void getUserList();

        void loadHistory();

    }
}
