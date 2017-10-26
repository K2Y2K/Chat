package com.example.lidongxue.chat.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by lidongxue on 17-10-25.
 */

public class MyReceiverAddFri extends BroadcastReceiver {
    public String response;
    private  int acceptStatus;
    public Handler handler;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("--NewFriendActivity--","onReceive()");
        //接收字符串及发送方的id
        Bundle bundle = intent.getExtras();
        response = bundle.getString("response");
        System.out.println("广播收到response:"+response);
        acceptStatus=bundle.getInt("acceptStatus");
        System.out.println("广播收到acceptStatus:"+acceptStatus);

        Message msg=new Message();
        switch (acceptStatus){
            case 1:
                msg.what=0x11;
                handler.sendMessage(msg);
                break;
            case 2:
                msg.what=0x12;
                handler.sendMessage(msg);
                break;
            case 3:
                msg.what=0x13;
                handler.sendMessage(msg);
                break;
            case 4:
                msg.what=0x14;
                handler.sendMessage(msg);
                break;
            default:
                break;

        }

    }
}
