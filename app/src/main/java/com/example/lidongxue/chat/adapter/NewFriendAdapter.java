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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lidongxue on 17-10-23.
 */

public class NewFriendAdapter extends BaseAdapter {
    private static final String TAG = "---NewFriendAdapter---";
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<HashMap<String, Object>> listIteminfo;
    private Callback mCallback;
    private final int VIEW_TYPE = 3;
    private final int TYPE_1 = 0;
    private final int TYPE_2 = 1;
    /**
         * 自定义接口，用于回调按钮点击事件到Activity
         *

     */
    public interface Callback {
       // public void click(View v);
        void onClick(View item, View widget, int position, int which);
   }

    public NewFriendAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public NewFriendAdapter(Context mContext, ArrayList<HashMap<String, Object>> listIteminfo, Callback mCallback) {
        this.mContext= mContext;
        this.listIteminfo = listIteminfo;
        this.mCallback = mCallback;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {

        Log.i(TAG, "getCount");
        return listIteminfo.size();
    }

    @Override
    public Object getItem(int position) {

        Log.i(TAG, "getItem");
        return listIteminfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "getItemId");
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        int p = position;
        if (p == 0)
            return TYPE_1;
        else
            return TYPE_2;

    }

    @Override
    public int getViewTypeCount() {
        //return super.getViewTypeCount();
        return VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder1 holder1=null;
        ViewHolder2 holder2=null;
        int type = getItemViewType(position);
        if (convertView == null) {
            mInflater = LayoutInflater.from(mContext);

            switch (type){
                case TYPE_1:
                    convertView = mInflater.inflate(R.layout.list_item_1, parent, false);
                    holder1=new ViewHolder1();
                    holder1.bt01=convertView.findViewById(R.id.newfriend_receiver_btn);
                    holder1.bt02=convertView.findViewById(R.id.newfriend_receiver_nobtn);
                    holder1.user_name=convertView.findViewById(R.id.newfriend_title);
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    convertView = mInflater.inflate(R.layout.list_item_2, parent, false);
                    holder2=new ViewHolder2();
                    holder2.user_name=convertView.findViewById(R.id.newfriend_title1);
                    holder2.user_info=convertView.findViewById(R.id.newfriend_receiver_tv);
                    break;
                default:
                    break;
            }
        }else{
                switch (type){
                    case TYPE_1:
                        holder1 = (ViewHolder1) convertView.getTag();
                        break;
                    case TYPE_2:
                        holder2 = (ViewHolder2) convertView.getTag();
                        break;


                }
            }
            //设置资源
            switch (type){
                case TYPE_1:
                    final View view = convertView;
                    final int p = position;
                    final int one = holder1.bt01.getId();
                    holder1.user_name.setText(listIteminfo.get(position).get("ItemTitle").toString());
                    holder1.bt01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCallback.onClick(view, parent, p, one);

                        }
                    });
                   // holder1.bt01.setTag(position);
                    final int two = holder1.bt02.getId();
                    holder1.bt02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCallback.onClick(view, parent, p, two);
                        }
                    });
                    //holder1.bt02.setTag(position);

                    break;
                case TYPE_2:
                    holder2.user_name.setText(listIteminfo.get(position).get("ItemTitle").toString());
                    holder2.user_info.setText(listIteminfo.get(position).get("ItemStatus").toString());
                    break;
            }
            /*convertView = mInflater.inflate(R.layout.list_item_1, null);
            holder = new ViewHolder();
            holder.bt01 = (Button) convertView.findViewById(R.id.newfriend_receiver_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int arg = position;
        //bt01点击
        holder.bt01.setOnClickListener(new ImageView.OnClickListener(){
            public void onClick(View v) {


            }
        });*/

        return convertView;
    }



    class ViewHolder1 {
        Button bt01;
        Button bt02;
        TextView user_name;


    }
    class ViewHolder2 {
        TextView user_name;
        TextView user_info;

    }
}
