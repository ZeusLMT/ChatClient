package com.example.chatclient.util;

import com.example.chatclient.model.ChatMessage;
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

    public static String parseUserList(String serverResponse) {
        String[] users = serverResponse.split(",");
        String userList = "";

        for (int i = 0; i < users.length; i++) {
            userList = userList + (i+1) + ". " + users[i] + "\n";
        }
        return userList;
    }

    public static ChatMessage parseMessage(String serverResponse, String myAccount) {
        String username = parseUsername(serverResponse);
        String timestamp = parseTimeStamp(serverResponse);
        String messageContent = parseMessage(serverResponse);

        boolean myMessage;

        if(username.equals(myAccount)) {
            myMessage = true;
        } else {
            myMessage = false;
        }

        return new ChatMessage(new User(username), timestamp, messageContent, myMessage);
    }

//    public static List<ChatMessage> parseChatMessList(String serverResponse, String myAccount) {
//        String[] chatMessList = serverResponse.split(",");
//        List<ChatMessage> chatMessageList = new ArrayList<>();
//        for (int i = 0; i < chatMessList.length; i++) {
//            String username = parseUsername(chatMessList[i]);
//            String timestamp = parseTimeStamp(chatMessList[i]);
//            String messageContent = parseMessage(chatMessList[i]);
//
//            boolean myMessage;
//
//            if(username.equals(myAccount)) {
//                myMessage = true;
//            } else {
//                myMessage = false;
//            }
//
//            ChatMessage chatMessage = new ChatMessage(new User(username), timestamp, messageContent, myMessage);
//            chatMessageList.add(chatMessage);
//        }
//
//        return chatMessageList;
//    }
}
