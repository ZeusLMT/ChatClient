package com.example.chatclient.screens.login;

import android.text.TextUtils;

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

            //TODO: Login server

        } else {
            view.showLoginSuccess("Log in successfully", userName);
        }
    }
}
