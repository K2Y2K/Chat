package com.example.lidongxue.chat.rxbus.event;

/**
 * Created by lidongxue on 17-10-17.
 */

public class HandleEvent {
    String receiveClass;
    Object message;
    String time;
    String type;
    int flag;

    public HandleEvent(String receiveClass, String message) {
        this.receiveClass = receiveClass;
        this.message = message;
    }

    public HandleEvent(String receiveClass, boolean message) {
        this.receiveClass = receiveClass;
        this.message = message;
    }
    public HandleEvent(String receiveClass, boolean message,int flag) {
        this.receiveClass = receiveClass;
        this.message = message;
        this.flag=flag;
    }

    public HandleEvent(String message) {
        this.message = message;
    }

    public String getReceiveClass() {
        return receiveClass;
    }

    public void setReceiveClass(String receiveClass) {
        this.receiveClass = receiveClass;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
