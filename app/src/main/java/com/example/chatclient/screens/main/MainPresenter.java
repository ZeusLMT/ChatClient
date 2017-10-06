package com.example.chatclient.screens.main;

import com.example.chatclient.event.ResponseEvent;
import com.example.chatclient.model.ChatMessage;
import com.example.chatclient.service.MessSender;
import com.example.chatclient.service.ServerCommands;
import com.example.chatclient.service.ServerType;
import com.example.chatclient.util.ServerUtil;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    public void sendMessage(String message) {
        //send message to server
        MessSender messSender = new MessSender(message);
        Thread t = new Thread(messSender);
        t.start();
    }

    @Override
    public void receiveMessage(ResponseEvent event, String myAccount) {
        //receive message from server
        if (event.isConnectSuccess()) {
            String serverMessage = event.getServerMessage();

            //categorize
            String serverType = ServerUtil.parseType(serverMessage);
            String serverResponse = ServerUtil.parseServerResponse(serverMessage);

            switch (serverType) {
                case ServerType.ERROR:
                    view.showError(serverResponse);
                    break;
                case ServerType.MESS:
                    ChatMessage chatMessage = ServerUtil.parseMessage(serverResponse, myAccount);
                    view.showMess(chatMessage);
                    break;
                case ServerType.HISTORY:
                    break;
                case ServerType.USERLIST:
                    String userList = ServerUtil.parseUserList(serverResponse);
                    view.showUserList(userList);
                    break;
                case ServerType.USERNAME:

                    break;
                case ServerType.SERVER_CONNECTED:
                    view.showError(serverResponse);
                    break;
                case ServerType.QUIT:
                    view.logOutSuccess(serverResponse);
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
        MessSender messSender = new MessSender(ServerCommands.QUIT);
        Thread t = new Thread(messSender);
        t.start();
    }

    @Override
    public void getUserList() {
        MessSender messSender = new MessSender(ServerCommands.GET_USERLIST);
        Thread t = new Thread(messSender);
        t.start();
    }

    @Override
    public void loadHistory() {
        MessSender messSender = new MessSender(ServerCommands.GET_HISTORY);
        Thread t = new Thread(messSender);
        t.start();
    }

}
