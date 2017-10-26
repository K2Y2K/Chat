package com.example.lidongxue.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.entity.MsgList;

import java.util.List;

/**
 * Created by lidongxue on 17-10-26.
 */

public class MessageAdapter extends BaseAdapter{
    private List<MsgList> msgAllList;
    private Context mcontext;
    public MessageAdapter(List<MsgList> msgAllList, Context mcontext) {
        this.msgAllList=msgAllList;
        this.mcontext=mcontext;
    }
    @Override
    public int getCount() {
        return msgAllList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgAllList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_msg_item, null);
        TextView name = (TextView) view.findViewById(R.id.friends_name);
        TextView msg = (TextView) view.findViewById(R.id.friends_msg);
        String from = msgAllList.get(position).getTo_name().split("@")[0];
        name.setText(from);
        msg.setText(msgAllList.get(position).getLast_msg());
        return view;
    }

}
