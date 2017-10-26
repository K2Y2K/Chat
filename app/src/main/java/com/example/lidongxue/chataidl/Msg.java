package com.example.lidongxue.chataidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lidongxue on 17-10-25.
 */

public class Msg  implements Parcelable {
    public static final int SELF_MSG = 1;//自己的消息
    public static final int FRIENDS_MSG = 2;//对方的消息
    int msg_id;
    int msg_list_id;
    String from_name;
    String msg_content;
    String msg_time;
    String msg_type;
    int from_type;

    public Msg(int msg_id, int msg_list_id, String from_name, String msg_content, String msg_time, String msg_type, int from_type) {
        this.msg_id = msg_id;
        this.msg_list_id = msg_list_id;
        this.from_name = from_name;
        this.msg_content = msg_content;
        this.msg_time = msg_time;
        this.msg_type = msg_type;
        this.from_type = from_type;
    }

    protected Msg(Parcel in) {
        msg_id = in.readInt();
        msg_list_id = in.readInt();
        from_name = in.readString();
        msg_content = in.readString();
        msg_time = in.readString();
        msg_type = in.readString();
        from_type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(msg_id);
        dest.writeInt(msg_list_id);
        dest.writeString(from_name);
        dest.writeString(msg_content);
        dest.writeString(msg_time);
        dest.writeString(msg_type);
        dest.writeInt(from_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Msg> CREATOR = new Creator<Msg>() {
        @Override
        public Msg createFromParcel(Parcel in) {
            return new Msg(in);
        }

        @Override
        public Msg[] newArray(int size) {
            return new Msg[size];
        }
    };

    public static int getSelfMsg() {
        return SELF_MSG;
    }

    public static int getFriendsMsg() {
        return FRIENDS_MSG;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getMsg_list_id() {
        return msg_list_id;
    }

    public void setMsg_list_id(int msg_list_id) {
        this.msg_list_id = msg_list_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public int getFrom_type() {
        return from_type;
    }

    public void setFrom_type(int from_type) {
        this.from_type = from_type;
    }
}

