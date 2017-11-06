package com.example.lidongxue.chataidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lidongxue on 17-10-31.
 */

public class UserPic implements Parcelable{
    int pic_id;
    String from_name;
    String pic_content;
    byte[] pic_bigmap;
    String pic_time;

    public UserPic() {

    }

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getPic_content() {
        return pic_content;
    }

    public void setPic_content(String pic_content) {
        this.pic_content = pic_content;
    }

    public byte[] getPic_bigmap() {
        return pic_bigmap;
    }

    public void setPic_bigmap(byte[] pic_bigmap) {
        this.pic_bigmap = pic_bigmap;
    }

    public String getPic_time() {
        return pic_time;
    }

    public void setPic_time(String pic_time) {
        this.pic_time = pic_time;
    }

    public static Creator<UserPic> getCREATOR() {
        return CREATOR;
    }

    protected UserPic(Parcel in) {
        pic_id = in.readInt();
        from_name = in.readString();
        pic_content = in.readString();
        pic_bigmap = in.createByteArray();
        pic_time = in.readString();
    }
    public UserPic(int picid, String fromname, String piccontent, byte[] pic, String pictime) {
        this.pic_id=picid;
        this.from_name=fromname;
        this.pic_content=piccontent;
        this.pic_bigmap=pic;
        this.pic_time=pictime;
    }

    public static final Creator<UserPic> CREATOR = new Creator<UserPic>() {
        @Override
        public UserPic createFromParcel(Parcel in) {
            return new UserPic(in);
        }

        @Override
        public UserPic[] newArray(int size) {
            return new UserPic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pic_id);
        parcel.writeString(from_name);
        parcel.writeString(pic_content);
        parcel.writeByteArray(pic_bigmap);
        parcel.writeString(pic_time);
    }
}
