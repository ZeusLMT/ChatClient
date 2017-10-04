package com.example.chatclient.screens.login;

/**
 * Created by HUYTRINH on 10/4/2017.
 */

public interface LoginActivityContract {
    interface View {
        void showLoginSuccess(String success, String userName);

        void showLoginError(String error);
    }

    interface Presenter {

        void performLogin(String userName);
    }
}
