package com.example.lidongxue.chat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.activity.ChatActivity;
import com.example.lidongxue.chat.adapter.MessageAdapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.entity.MsgList;
import com.example.lidongxue.chat.entity.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lidongxue on 17-9-26.
 */

public class OneFragment extends Fragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.msg_list)
    ListView mymsg_list;
    private MessageAdapter adapter;
    private List<MsgList> msgAllList;
    private User user;


    private View rootView1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("OneFragment");
        rootView1 = inflater.inflate(R.layout.fragment_recent_message, container, false);
        ButterKnife.bind(this, rootView1);
        Log.i(this.getClass().getSimpleName(), "is:"+ BaseApp.isBondService);
        Log.i(this.getClass().getSimpleName(), "is service:"+BaseApp.service);
        return rootView1;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(BaseApp.service!=null){
            Log.i(this.getClass().getSimpleName(), "onResume()is:"+ BaseApp.isBondService);
            Log.i(this.getClass().getSimpleName(), "onResume() is service:"+BaseApp.service);
            user = BaseApp.service.getUser();
            Log.i(this.getClass().getSimpleName(), "onResume() is service:"+user.getUser_id());
            Log.i(this.getClass().getSimpleName(), "onResume() is service:"+user.getUser_name());
            getList(user.getUser_id());
        }
    }


    /**
     * 获取消息列表
     *
     * @param userId
     */
    public void getList(int userId) {
        User_DB dbHelper = new User_DB(getActivity());
        msgAllList = dbHelper.getMsgAllList(userId);
        adapter = new MessageAdapter(msgAllList, getContext());
        mymsg_list.setAdapter(adapter);
        mymsg_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("msg_list_id", msgAllList.get(position).getMsg_list_id());
        intent.putExtra("to_name", msgAllList.get(position).getTo_name());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }
}

