package com.example.chatclient.util;

/**
 * Created by Zeus on 3/10/2017.
 */

public class ServerUtil {
    public static String parseUsername(String serverMessage) {
        String userName = serverMessage.substring(serverMessage.lastIndexOf("from") + "from ".length(), serverMessage.lastIndexOf("@"));
        return userName;
    }

    public static long parseTimeStamp(String serverMessage) {
        String timeStampString = serverMessage.substring(serverMessage.lastIndexOf("@") + 1, serverMessage.length());
        long timeStamp = Long.parseLong(timeStampString);
        return timeStamp;
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
}
