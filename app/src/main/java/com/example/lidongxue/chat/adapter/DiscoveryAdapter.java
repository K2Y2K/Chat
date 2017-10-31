package com.example.lidongxue.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chataidl.UserPic;

import java.util.List;

/**
 * Created by lidongxue on 17-10-31.
 */

public class DiscoveryAdapter extends BaseAdapter {
    List<UserPic> userpicsList;
    LayoutInflater mInflater;

    public DiscoveryAdapter(Context mContext, List<UserPic> userpic) {
        userpicsList=userpic;
        mInflater=LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return userpicsList.size();
    }

    @Override
    public Object getItem(int i) {
        return userpicsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        view=mInflater.inflate(R.layout.list_discovery,parent, false);
        holder.fromname=view.findViewById(R.id.dis_from_name);
        holder.piccontent=view.findViewById(R.id.dis_pic_content);
        holder.pictime=view.findViewById(R.id.dis_pic_time);
        holder.pic=view.findViewById(R.id.dis_pic);

        holder.fromname.setText(userpicsList.get(position).getFrom_name());
        holder.pictime.setText(userpicsList.get(position).getPic_time());
        holder.piccontent.setText(userpicsList.get(position).getPic_content());
        holder.pic.setImageBitmap(byteToBitmap(userpicsList.get(position).getPic_bigmap()));

      /*  ByteArrayInputStream bais =  new ByteArrayInputStream(userpicsList.get(position).getPic_bigmap());
        holder.pic.setImageDrawable(Drawable.createFromStream(bais,null));*/


        return view;
    }
      class ViewHolder{
          TextView fromname;
          TextView piccontent;
          ImageView pic;
          TextView pictime;
    }

    private Bitmap byteToBitmap(byte[] pic){

        Bitmap imagebitmap= BitmapFactory.decodeByteArray(pic, 0, pic.length);
        return imagebitmap;
        //iv.setImageBitmap(imagebitmap);
        /*方法二
        * ByteArrayInputStream bais =  new ByteArrayInputStream(pic);
        *  iv.setImageDrawable(Drawable.createFromStream(bais, "photo"));
        *
        * */

    }
}
