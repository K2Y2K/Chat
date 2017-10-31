package com.example.lidongxue.chat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.entity.Contact;

import java.util.List;

/**
 * Created by lidongxue on 17-10-30.
 */

public class NewFriendAdapter1 extends BaseAdapter {
    List<Contact> contacts_list;
    private static final String TAG = "---NewFriendAdapter1---";
    private LayoutInflater mInflater;
    private Context mContext;

    private NewFriendAdapter.Callback mCallback;
    private final int VIEW_TYPE = 2;
    private final int TYPE_1 = 0;
    private final int TYPE_2 = 1;

    public NewFriendAdapter1(Context mcontext, List<Contact> contacts_list) {
        this.mContext=mcontext;
        mInflater= LayoutInflater.from(mContext);
        this.contacts_list=contacts_list;
        Log.i(TAG, "NewFriendAdapter1"+contacts_list.toString());
    }

    @Override
    public int getCount() {
        Log.i(TAG, "NewFriendAdapter1"+contacts_list.size());
        return contacts_list.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        Contact contact=contacts_list.get(position);
        Log.i(TAG, "NewFriendAdapter1 朋友状态表里的from_name:"+contact.getFrom_name());
        Log.i(TAG, "NewFriendAdapter1 当前用户名"+BaseApp.service.getUser().getUser_name().split("@")[0]);
        if(contact.getFrom_name().equals(BaseApp.service.getUser().getUser_name().split("@")[0])){
            return TYPE_2;

        }else {
                return TYPE_1;
        }

    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        NewFriendAdapter1.ViewHolder1 holder1=null;
        NewFriendAdapter1.ViewHolder2 holder2=null;
        int type = getItemViewType(position);
        if (convertView == null) {
            mInflater = LayoutInflater.from(mContext);

            switch (type){
                case TYPE_1:
                    Log.i(TAG, "TYPE_1");
                    convertView = mInflater.inflate(R.layout.list_item_1, parent, false);
                    holder1=new NewFriendAdapter1.ViewHolder1();
                    holder1.bt01=convertView.findViewById(R.id.newfriend_receiver_btn);
                    holder1.bt02=convertView.findViewById(R.id.newfriend_receiver_nobtn);
                    holder1.user_name=convertView.findViewById(R.id.newfriend_title);
                    holder1.user_info=convertView.findViewById(R.id.newfriend_receiver_tv1);
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    Log.i(TAG, "TYPE_2");
                    convertView = mInflater.inflate(R.layout.list_item_2, parent, false);
                    holder2=new NewFriendAdapter1.ViewHolder2();
                    holder2.user_name=convertView.findViewById(R.id.newfriend_title1);
                    holder2.user_info=convertView.findViewById(R.id.newfriend_receiver_tv);
                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }
        }else{
            switch (type){
                case TYPE_1:
                    holder1 = (NewFriendAdapter1.ViewHolder1) convertView.getTag();
                    Log.i(TAG, "TYPE_1_1");
                    break;
                case TYPE_2:
                    holder2 = (NewFriendAdapter1.ViewHolder2) convertView.getTag();
                    Log.i(TAG, "TYPE_1_2");
                    break;


            }
        }
        //设置资源
        switch (type){
            case TYPE_1:
                Log.i(TAG, "--TYPE_1添加资源");
                //此种情况是对方申请添加自己为好友
                holder1.user_name.setText(contacts_list.get(position).getFrom_name());
                final ViewHolder1 finalHolder = holder1;
                if(contacts_list.get(position).getSubed()==0&&contacts_list.get(position).getUnsubed()==0) {
                    holder1.bt01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finalHolder.user_info.setVisibility(View.VISIBLE);
                            finalHolder.user_info.setText("同意添加");
                            BaseApp.service.accept(contacts_list.get(position).getFrom_name());
                            finalHolder.bt01.setVisibility(View.GONE);
                            finalHolder.bt02.setVisibility(View.GONE);
                            Log.i(TAG, "--TYPE_1 同意添加");

                        }
                    });
                    holder1.bt02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finalHolder.user_info.setVisibility(View.VISIBLE);
                            finalHolder.user_info.setText("拒绝添加");
                            BaseApp.service.refuse(contacts_list.get(position).getFrom_name());
                            finalHolder.bt01.setVisibility(View.GONE);
                            finalHolder.bt02.setVisibility(View.GONE);
                        }
                    });

                }else{
                    holder1.bt01.setVisibility(View.GONE);
                    holder1.bt02.setVisibility(View.GONE);
                    holder1.user_info.setVisibility(View.VISIBLE);
                    if(contacts_list.get(position).getSubed()==1){
                        holder1.user_info.setText("同意添加");
                        Log.i(TAG, "--TYPE_1１ 同意添加");
                    }else {
                        holder1.user_info.setText("拒绝添加");
                    }
                }
                break;
            case TYPE_2:
                Log.i(TAG, "--TYPE_2添加资源");
                holder2.user_name.setText(contacts_list.get(position).getTo_name());
                if(contacts_list.get(position).getSub()==1&&contacts_list.get(position).getSubed()==0){
                    if(contacts_list.get(position).getUnsubed()==0) {
                        holder2.user_info.setText("等待添加");
                    }else {
                        holder2.user_info.setText("对方拒绝添加");
                    }
                }else if(contacts_list.get(position).getSub()==1&&contacts_list.get(position).getSubed()==1){
                    holder2.user_info.setText("对方已同意添加");
                }
                break;
        }


        return convertView;
    }
    class ViewHolder1 {
        Button bt01;
        Button bt02;
        TextView user_name;
        TextView user_info;


    }
    class ViewHolder2 {
        TextView user_name;
        TextView user_info;

    }
}
