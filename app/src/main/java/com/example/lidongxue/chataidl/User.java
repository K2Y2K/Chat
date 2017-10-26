package com.example.lidongxue.chataidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lidongxue on 17-10-25.
 */

public class User implements Parcelable {
    int user_id;
    String user_name;
    String user_psd;
    String user_head_img;

    public User() {
    }

    public User(int user_id, String user_name, String user_psd, String user_head_img) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_psd = user_psd;
        this.user_head_img = user_head_img;
    }

    public User(Parcel in) {
        user_id = in.readInt();
        user_name = in.readString();
        user_psd = in.readString();
        user_head_img = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_psd() {
        return user_psd;
    }

    public void setUser_psd(String user_psd) {
        this.user_psd = user_psd;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(user_id);
        parcel.writeString(user_name);
        parcel.writeString(user_psd);
        parcel.writeString(user_head_img);
    }



}