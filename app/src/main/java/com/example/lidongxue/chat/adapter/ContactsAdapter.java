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
import com.example.lidongxue.chat.entity.bean.UserBean;

import java.util.List;

/**
 * Created by lidongxue on 17-9-30.
 */

public class ContactsAdapter extends BaseAdapter {
    private  Context mcontext;
    private List<UserBean.UserBeanDetails> mmcontacts;
    private LayoutInflater mInflater;

    public ContactsAdapter(List<UserBean.UserBeanDetails> mmcontacts, Context mcontext) {
        this.mmcontacts=mmcontacts;
        this.mcontext=mcontext;
        mInflater = LayoutInflater.from(mcontext);


    }

    @Override
    public int getCount() {
        Log.i(this.getClass().getSimpleName(),mmcontacts.size()+"");
        return mmcontacts.size();
    }

    @Override
    public Object getItem(int i) {
        return mmcontacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
            view=mInflater.inflate(R.layout.list_contacts,parent, false);
            //holder.contacts_pic=view.findViewById(R.id.contacts_pic);
        holder.contacts_name=view.findViewById(R.id.contacts_name);
        holder.contacts_name.setText(mmcontacts.get(position).getUserIp());
      return   view;
    }
    class ViewHolder{
        ImageView contacts_pic;
        TextView contacts_name;
    }
}
