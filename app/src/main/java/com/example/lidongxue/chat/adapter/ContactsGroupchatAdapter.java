package com.example.lidongxue.chat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.entity.bean.UserBean;

import java.util.List;


/**
 * Created by lidongxue on 17-11-4.
 */

public class ContactsGroupchatAdapter extends BaseAdapter {
    private final CallbackChecked mCallbackChecked;
    private Context mcontext;
    private List<UserBean.UserBeanDetails> mmcontacts;
    private LayoutInflater mInflater;


    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     *

     */
    public interface CallbackChecked {
        // public void click(View v);
        void onCheckBoxClick(View item, View widget, int position, int which,boolean b);
    }
    public ContactsGroupchatAdapter(List<UserBean.UserBeanDetails> mmcontacts, Context mcontext,
                                    CallbackChecked mCallbackChecked) {
        this.mmcontacts=mmcontacts;
        this.mcontext=mcontext;
        mInflater = LayoutInflater.from(mcontext);
        this.mCallbackChecked=mCallbackChecked;


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
    public View getView(final int position, View view, final ViewGroup parent) {
        ContactsGroupchatAdapter.ViewHolder holder=new ContactsGroupchatAdapter.ViewHolder();
        view=mInflater.inflate(R.layout.list_add_groupchat,parent, false);
        //holder.contacts_pic=view.findViewById(R.id.contacts_pic);
        holder.contacts_name=view.findViewById(R.id.contacts_name);
        holder.contacts_name.setText(mmcontacts.get(position).getUserIp().split("@")[0]);
        holder.contacts_name_checked=view.findViewById(R.id.contacts_name_checked);
        final  int one=holder.contacts_name_checked.getId();

        final View finalView1 = view;
        holder.contacts_name_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //mCallbackChecked.onCheckBoxClick(compoundButton,parent,position,one,b);
                mCallbackChecked.onCheckBoxClick(finalView1,parent,position,one,b);

            }
        });
        return   view;
    }
    class ViewHolder{
        ImageView contacts_pic;
        TextView contacts_name;
        CheckBox contacts_name_checked;
    }
}
