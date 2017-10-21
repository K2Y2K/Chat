package com.example.lidongxue.chat.rxbus.event;

/**
 * Created by lidongxue on 17-10-17.
 */

public class FriendListenerEvent {
    String requestName;//请求人的姓名
    String requestType;//请求类型
    String reciverClass;//接收的类

    public FriendListenerEvent(String requestName, String requestType, String reciverClass) {
        this.requestName = requestName;
        this.requestType = requestType;
        this.reciverClass = reciverClass;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getReciverClass() {
        return reciverClass;
    }

    public void setReciverClass(String reciverClass) {
        this.reciverClass = reciverClass;
    }
}
