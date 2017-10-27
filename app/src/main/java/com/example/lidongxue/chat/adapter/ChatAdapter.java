package com.example.lidongxue.chat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.entity.Msg;

import java.util.List;

/**
 * Created by lidongxue on 17-10-26.
 */

public class ChatAdapter extends BaseAdapter {
    private static final String TAG = "---ChatAdapter---" ;
    private  final int SELF_MSG = 1;//自己发送的消息
    private   final int FRIENDS_MSG = 2;//收到对方的消息
    private static final int ITEMCOUNT = 2;// 消息类型的总数
    private List<Msg> msgList;
    private LayoutInflater inflater;
    public ChatAdapter(List<Msg> msgList, Context mcontext) {
        this.msgList = msgList;
        inflater = LayoutInflater.from(mcontext);
        Log.i(this.getClass().getSimpleName(), "执行适配器的构造方法" +msgList);
    }

    @Override
    public int getCount() {
        Log.i(this.getClass().getSimpleName(), "适配器获取消息的条数" +msgList.size());
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
     */
    @Override
    public int getItemViewType(int position) {
        Msg msgEntity=msgList.get(position);
        Log.i(this.getClass().getSimpleName(), "适配器判断消息的发送者类型" +msgEntity.getFrom_type());
        if(msgEntity.getFrom_type()==1){//自己发送的消息
            return SELF_MSG;
        }else{
            return FRIENDS_MSG;
        }

    }

    @Override
    public int getViewTypeCount() {
        return ITEMCOUNT;
    }

    /**
     * Item类型的总数
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1=null;
        ViewHolder2 holder2=null;

        int type = getItemViewType(position);
        if(convertView==null){

            switch (type){
                case SELF_MSG:
                    Log.i(TAG, "SELF_MSG");
                    convertView=inflater.inflate(R.layout.item_chattext_send,parent, false);
                    holder1=new ViewHolder1();
                    holder1.my_name=convertView.findViewById(R.id.send_name);
                    holder1.my_text=convertView.findViewById(R.id.send_content);
                    holder1.my_time=convertView.findViewById(R.id.send_Time);
                    convertView.setTag(holder1);
                    break;
                case FRIENDS_MSG:
                    Log.i(TAG, "FRIENDS_MSG");
                    convertView=inflater.inflate(R.layout.item_chattext_receive,parent, false);
                    holder2=new ViewHolder2();
                    holder2.your_name=convertView.findViewById(R.id.receive_name);
                    holder2.your_text=convertView.findViewById(R.id.receive_content);
                    holder2.your_time=convertView.findViewById(R.id.receive_Time);
                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }
        }else {
            switch (type){
                case SELF_MSG:
                    holder1 = (ViewHolder1) convertView.getTag();
                    Log.i(TAG, "TSELF_MSG_1");
                    break;
                case FRIENDS_MSG:
                    holder2 = (ViewHolder2) convertView.getTag();
                    Log.i(TAG, "FRIENDS_MSG_1");
                    break;


            }
        }
        //设置资源
        switch (type){
            case SELF_MSG:
                Log.i(TAG, "SELF_MSG资源设置");

                holder1.my_name.setText(msgList.get(position).getFrom_name());
                holder1.my_text.setText(msgList.get(position).getMsg_content());
                holder1.my_time.setText(msgList.get(position).getMsg_time());

                break;
            case FRIENDS_MSG:
                Log.i(TAG, "FRIENDS_MSG资源设置");
                holder2.your_name.setText(msgList.get(position).getFrom_name());
                holder2.your_text.setText(msgList.get(position).getMsg_content());
                holder2.your_time.setText(msgList.get(position).getMsg_time());

                break;
            default:
                break;
        }

        return convertView;
    }
    class ViewHolder1 {
        ImageView my_pic;
        TextView my_name;
        TextView my_text;
        TextView my_time;


    }
    class ViewHolder2 {
        ImageView your_pic;
        TextView your_name;
        TextView your_text;
        TextView your_time;

    }
}
