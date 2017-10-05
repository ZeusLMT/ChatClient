package com.example.chatclient.screens.main;

import android.util.Log;

import com.example.chatclient.event.ResponseEvent;
import com.example.chatclient.event.SendEvent;
import com.example.chatclient.model.ChatMessage;
import com.example.chatclient.model.User;
import com.example.chatclient.service.MessSender;
import com.example.chatclient.service.ServerCommands;
import com.example.chatclient.service.ServerType;
import com.example.chatclient.util.ServerUtil;

import org.greenrobot.eventbus.EventBus;

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
        MessSender messSender = new MessSender(message);
        Thread t = new Thread(messSender);
        t.start();
    }

    @Override
    public void receiveMessage(ResponseEvent event, String myAccount) {
        //TODO: receive message from server
        if (event.isConnectSuccess()) {
            String serverMessage = event.getServerMessage();
            Log.i("abc", "userlist: " + serverMessage);

            //TODO: categorize
            String serverType = ServerUtil.parseType(serverMessage);
            String serverResponse = ServerUtil.parseServerResponse(serverMessage);

            switch (serverType) {
                case ServerType.ERROR:
                    view.showError(serverResponse);
                    break;
                case ServerType.MESS:
                    String username = ServerUtil.parseUsername(serverResponse);
                    String timestamp = ServerUtil.parseTimeStamp(serverResponse);
                    String messageContent = ServerUtil.parseMessage(serverResponse);

                    boolean myMessage;

                    Log.i("abc", "myAccount: " + myAccount);
                    Log.i("abc", "username: " + username);

                    if(username.equals(myAccount)) {
                        myMessage = true;
                    } else {
                        myMessage = false;
                    }

                    ChatMessage chatMessage = new ChatMessage(new User(username), timestamp, messageContent, myMessage);
                    view.showMess(chatMessage);
                    break;
                case ServerType.HISTORY:
                    break;
                case ServerType.USERLIST:
                    view.showUserList(serverResponse);
                    break;
                case ServerType.USERNAME:
                    break;
                case ServerType.SERVER_CONNECTED:
                    view.showError(serverResponse);
                    break;
                case ServerType.QUIT:
                    //TODO show goodbye
                    break;
                default:
                    break;
            }
        } else {
            view.showError(event.getServerMessage());
        }

    }

    @Override
    public void logout(String userName) {
        //logout server
        EventBus.getDefault().post(new SendEvent("", ServerCommands.QUIT));
        view.logOutSuccess("Logout successfully");
    }

    @Override
    public void getUserList() {
        MessSender messSender = new MessSender(ServerCommands.GET_USERLIST);
        Thread t = new Thread(messSender);
        t.start();
    }
}
