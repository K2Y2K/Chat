package com.example.lidongxue.chataidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lidongxue on 17-10-25.
 */

public class Contact implements Parcelable {
    protected Contact(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
