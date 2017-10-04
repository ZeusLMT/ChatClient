package com.example.chatclient.screens.login;

import android.text.TextUtils;

import com.example.chatclient.service.MessSender;
import com.example.chatclient.service.ServerCommands;

/**
 * Created by HUYTRINH on 10/4/2017.
 */

public class LoginPresenter implements LoginActivityContract.Presenter {
    private LoginActivityContract.View view;

    public LoginPresenter(LoginActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void performLogin(String userName) {
        if (TextUtils.isEmpty(userName)) {
            view.showLoginError("User name should not be empty");

        } else {
            //TODO: Login server
            MessSender messSender = new MessSender(ServerCommands.SET_USERNAME + userName);
            Thread t = new Thread(messSender);
            t.start();
            view.showLoginSuccess("Log in successfully", userName);
        }
    }
}
