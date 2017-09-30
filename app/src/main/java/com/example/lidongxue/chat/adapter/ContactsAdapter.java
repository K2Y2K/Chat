package com.example.lidongxue.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.lidongxue.chat.R;

/**
 * Created by lidongxue on 17-9-30.
 */

public class ContactsAdapter extends BaseAdapter {
    Context context;
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      return   layoutInflater.inflate(R.layout.header_rv_contacts, null);

    }
}
