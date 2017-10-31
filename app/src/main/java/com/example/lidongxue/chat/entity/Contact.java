package com.example.lidongxue.chat.entity;

/**
 * Created by lidongxue on 17-10-25.
 */

public class Contact {
    int add_contact_id;
    String from_name;
    String to_name;
    int sub;

    int subed;
    int unsub;
    int unsubed;
    String sub_time;

    public Contact(int add_contact_id, String from_name, String to_name, int sub, int subed, int unsub, int unsubed, String sub_time) {
        this.add_contact_id = add_contact_id;
        this.from_name = from_name;
        this.to_name = to_name;
        this.sub = sub;
        this.subed = subed;
        this.unsub = unsub;
        this.unsubed = unsubed;
        this.sub_time = sub_time;
    }

    public int getAdd_contact_id() {
        return add_contact_id;
    }

    public void setAdd_contact_id(int add_contact_id) {
        this.add_contact_id = add_contact_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }

    public int getUnsub() {
        return unsub;
    }

    public void setUnsub(int unsub) {
        this.unsub = unsub;
    }

    public int getSubed() {
        return subed;
    }

    public void setSubed(int subed) {
        this.subed = subed;
    }

    public int getUnsubed() {
        return unsubed;
    }

    public void setUnsubed(int unsubed) {
        this.unsubed = unsubed;
    }

    public String getSub_time() {
        return sub_time;
    }

    public void setSub_time(String sub_time) {
        this.sub_time = sub_time;
    }
}
