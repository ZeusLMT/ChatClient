package com.example.chatclient.screens.login;

import com.example.chatclient.event.ResponseEvent;

public interface LoginActivityContract {
    interface View {
        void showLoginSuccess(String success, String userName);

        void showLoginError(String error);
    }

    interface Presenter {

        void performLogin(String userName);

        void validLogin(ResponseEvent event);
    }
}
