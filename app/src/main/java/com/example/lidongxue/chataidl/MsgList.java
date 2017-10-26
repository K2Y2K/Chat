package com.example.lidongxue.chataidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lidongxue on 17-10-25.
 */

public class MsgList implements Parcelable {
    int msg_list_id;
    int user_id;
    String to_name;
    String last_msg;
    String last_img_time;
    int msg_list_type;

    public MsgList(int msg_list_id, int user_id, String to_name, String last_msg, String last_img_time, int msg_list_type) {
        this.msg_list_id = msg_list_id;
        this.user_id = user_id;
        this.to_name = to_name;
        this.last_msg = last_msg;
        this.last_img_time = last_img_time;
        this.msg_list_type = msg_list_type;
    }

    protected MsgList(Parcel in) {
        msg_list_id = in.readInt();
        user_id = in.readInt();
        to_name = in.readString();
        last_msg = in.readString();
        last_img_time = in.readString();
        msg_list_type = in.readInt();
    }

    public static final Creator<MsgList> CREATOR = new Creator<MsgList>() {
        @Override
        public MsgList createFromParcel(Parcel in) {
            return new MsgList(in);
        }

        @Override
        public MsgList[] newArray(int size) {
            return new MsgList[size];
        }
    };

    public int getMsg_list_id() {
        return msg_list_id;
    }

    public void setMsg_list_id(int msg_list_id) {
        this.msg_list_id = msg_list_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }

    public String getLast_img_time() {
        return last_img_time;
    }

    public void setLast_img_time(String last_img_time) {
        this.last_img_time = last_img_time;
    }

    public int getMsg_list_type() {
        return msg_list_type;
    }

    public void setMsg_list_type(int msg_list_type) {
        this.msg_list_type = msg_list_type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(msg_list_id);
        parcel.writeInt(user_id);
        parcel.writeString(to_name);
        parcel.writeString(last_msg);
        parcel.writeString(last_img_time);
        parcel.writeInt(msg_list_type);
    }
}

