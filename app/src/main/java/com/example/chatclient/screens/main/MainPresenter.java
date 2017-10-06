package com.example.chatclient.screens.main;

import com.example.chatclient.event.ServerEvent;
import com.example.chatclient.model.Message;
import com.example.chatclient.model.OnlineList;
import com.example.chatclient.server.SenderThread;
import com.example.chatclient.server.ServerCommands;
import com.example.chatclient.server.ServerType;
import com.example.chatclient.util.ServerUtil;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    public void sendMessage(String message) {
        //TODO: send message to server
        SenderThread senderThread = new SenderThread(message);
        Thread t = new Thread(senderThread);
        t.start();
    }

    @Override
    public void receiveMessage(ServerEvent event, String myAccount) {
        //TODO: receive message from server
        if (event.isConnectSuccess()) {
            String serverMessage = event.getServerMessage();

            //TODO: categorize
            String serverType = ServerUtil.parseType(serverMessage);
            String serverResponse = ServerUtil.parseServerResponse(serverMessage);

            switch (serverType) {
                case ServerType.ERROR:
                    view.showError(serverResponse);
                    break;
                case ServerType.MESS:
                    Message chatMessage = ServerUtil.parseMessage(serverResponse, myAccount);
                    view.showMess(chatMessage);
                    break;
                case ServerType.USERLIST:
                    OnlineList onlineList = ServerUtil.parseUserList(serverResponse);
                    view.showUserList(onlineList);
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
        SenderThread senderThread = new SenderThread(ServerCommands.QUIT);
        Thread t = new Thread(senderThread);
        t.start();
    }

    @Override
    public void getUserList() {
        SenderThread senderThread = new SenderThread(ServerCommands.GET_USERLIST);
        Thread t = new Thread(senderThread);
        t.start();
    }

    @Override
    public void loadHistory() {
        SenderThread senderThread = new SenderThread(ServerCommands.GET_HISTORY);
        Thread t = new Thread(senderThread);
        t.start();
    }
}
