package com.example.chatclient.util;

import com.example.chatclient.model.Message;
import com.example.chatclient.model.OnlineList;
import com.example.chatclient.model.User;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ServerUtil {
    public static String parseUsername(String serverMessage) {
        String userName = serverMessage.substring(serverMessage.lastIndexOf("from") + "from ".length(), serverMessage.lastIndexOf("@"));
        return userName;
    }

    public static String parseTimeStamp(String serverMessage) {
        String timeStampString = serverMessage.substring(serverMessage.lastIndexOf("@") + 1, serverMessage.length());
        long timeStamp = Long.parseLong(timeStampString);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        return dateFormat.format(timeStamp);
    }

    public static String parseMessage(String serverMessage) {
        String message = serverMessage.substring(0, serverMessage.lastIndexOf("from") - 1);
        return message;
    }

    public static String parseType(String serverResponse) {
        return serverResponse.substring(0, serverResponse.indexOf("."));
    }

    public static String parseServerResponse(String serverResponse) {
        return serverResponse.substring(serverResponse.indexOf(".") + 1, serverResponse.length());
    }

    public static OnlineList parseUserList(String serverResponse) {
        String[] users = serverResponse.split(",");
        String userList = "";
        OnlineList onlineList = new OnlineList();
        for (int i = 0; i < users.length; i++) {
            onlineList.add(new User(users[i]));
        }
        return onlineList;
    }

    public static Message parseMessage(String serverResponse, String myAccount) {
        String username = parseUsername(serverResponse);
        String timestamp = parseTimeStamp(serverResponse);
        String messageContent = parseMessage(serverResponse);

        boolean myMessage;

        if(username.equals(myAccount)) {
            myMessage = true;
        } else {
            myMessage = false;
        }

        return new Message(new User(username), timestamp, messageContent, myMessage);
    }


}
