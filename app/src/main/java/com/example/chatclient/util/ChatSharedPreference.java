package com.example.chatclient.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class ChatSharedPreference {
    private Context context;
    private SharedPreferences sharePrefs;
    private SharedPreferences.Editor editor;
    private final String MYACCOUNT_KEY = "myAccount";

    public ChatSharedPreference(Context context) {
        this.context = context;
        this.sharePrefs = context.getSharedPreferences("share_pref", Context.MODE_PRIVATE);
        editor = sharePrefs.edit();
    }

    public void saveMyAccount(String username) {
        editor.putString(MYACCOUNT_KEY, username);
        editor.commit();
    }

    public String getMyAccount() {
        return sharePrefs.getString(MYACCOUNT_KEY, "");
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public boolean isLogIn() {
        if (!TextUtils.isEmpty(getMyAccount())) {
            return true;
        } else {
            return false;
        }
    }
}
