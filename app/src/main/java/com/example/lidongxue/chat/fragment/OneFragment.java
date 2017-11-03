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

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.activity.ChatActivity;
import com.example.lidongxue.chat.adapter.MessageAdapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.entity.MsgList;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.rxbus.RxBus;

import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by lidongxue on 17-9-26.
 */

public class OneFragment extends Fragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.msg_list)
    ListView mymsg_list;
    private MessageAdapter adapter;
    private List<MsgList> msgAllList=new ArrayList<>();
    private User user;
    private Subscription subscription;

    private View rootView1;
    private String[][] msg_count;
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
            if(BaseApp.service.getConnection().isAuthenticated()) {
                Log.i(this.getClass().getSimpleName(), "onResume()is:" + BaseApp.isBondService);
                Log.i(this.getClass().getSimpleName(), "onResume() is service:" + BaseApp.service);
                user = BaseApp.service.getUser();
                if (user != null) {
                    Log.i(this.getClass().getSimpleName(), "onResume() is service:" + user.getUser_id());
                    Log.i(this.getClass().getSimpleName(), "onResume() is service:" + user.getUser_name());

                    getList(user.getUser_id());
                    newMsgnote();
                    Log.d(getClass().getSimpleName(),"aa:"+msgAllList.toString());
                    adapter = new MessageAdapter(msgAllList, getContext());
                    mymsg_list.setSelection(msgAllList.size()-1);
                }
            }else{

            }
        }
    }

    /**
     * 获取消息列表
     *
     * @param userId
     */
    public void getList(int userId) {

        User_DB dbHelper = new User_DB(getActivity());
        List<MsgList> msgAllList1 = dbHelper.getMsgAllList(userId);
        Log.d(getClass().getSimpleName(),"a1:"+msgAllList1.toString());
        if(msgAllList1!=null){
            Log.d(getClass().getSimpleName(),"getList");
            msgAllList.clear();
            msgAllList.addAll(msgAllList1);
            /*if(adapter==null){
                Log.d(getClass().getSimpleName(),"1:"+msgAllList.toString());
                adapter = new MessageAdapter(msgAllList, getContext());
                mymsg_list.setAdapter(adapter);
            }else {
                Log.d(getClass().getSimpleName(),"2:"+msgAllList.toString());
                adapter.notifyDataSetChanged();
            }*/
            Log.d(getClass().getSimpleName(),"a2:"+msgAllList.toString());
            adapter = new MessageAdapter(msgAllList, getContext());
            mymsg_list.setAdapter(adapter);
            mymsg_list.setSelection(msgAllList.size()-1);
        }

        mymsg_list.setOnItemClickListener(this);
    }
    /**
     * 观察新消息
     */
    public void newMsgnote() {
        Log.i(this.getClass().getSimpleName(), "执行newMsgnote()方法" );

        subscription = RxBus.getInstance().toObserverable(Message.class).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Message>() {
                    @Override
                    public void call(Message message) {
                        Log.i(this.getClass().getSimpleName(), "newMsgnote()消息message:"+message);
                        MainActivity ma=new MainActivity();
                        String logoname="Chat()";
                        ma.setLogo_name(logoname);
                        if (message.getFrom()!=null) {
                            Log.i(this.getClass().getSimpleName(), "newMsgnote()收到新消息:"+message.getFrom());
                            Log.i(this.getClass().getSimpleName(), "newMsgnote()收到新消息:"+message.getBody());
                            getList(user.getUser_id());
                        }
                    }
                });
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

