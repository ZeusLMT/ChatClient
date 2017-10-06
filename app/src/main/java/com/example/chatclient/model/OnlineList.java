package com.example.chatclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUYTRINH on 10/6/2017.
 */

public class OnlineList implements Parcelable {
    private List<User> userList;

    public OnlineList() {
        userList = new ArrayList<>();
    }

    public void add(User user) {
        userList.add(user);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.userList);
    }

    protected OnlineList(Parcel in) {
        this.userList = new ArrayList<User>();
        in.readList(this.userList, User.class.getClassLoader());
    }

    public static final Parcelable.Creator<OnlineList> CREATOR = new Parcelable.Creator<OnlineList>() {
        @Override
        public OnlineList createFromParcel(Parcel source) {
            return new OnlineList(source);
        }

        @Override
        public OnlineList[] newArray(int size) {
            return new OnlineList[size];
        }
    };
}
