package com.example.chatclient.screens.login;

import android.text.TextUtils;

import com.example.chatclient.event.ResponseEvent;
import com.example.chatclient.service.MessSender;
import com.example.chatclient.service.ServerCommands;
import com.example.chatclient.service.ServerType;
import com.example.chatclient.util.ServerUtil;

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
        }
    }

    @Override
    public void validLogin(ResponseEvent event) {
        //TODO: receive message from server
        if (event.isConnectSuccess()) {
            String serverMessage = event.getServerMessage();

            //TODO: categorize
            String serverType = ServerUtil.parseType(serverMessage);
            String serverResponse = ServerUtil.parseServerResponse(serverMessage);
            switch (serverType) {
                case ServerType.ERROR:
                    view.showLoginError(serverResponse);
                    break;
                case ServerType.USERNAME:
                    view.showLoginSuccess("Log in successfully", serverResponse);
                default:
                    break;
            }
        } else {
            view.showLoginError(event.getServerMessage());
        }
    }
}
