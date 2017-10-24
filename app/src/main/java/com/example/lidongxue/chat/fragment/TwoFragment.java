package com.example.lidongxue.chat.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.activity.ChatActivity;
import com.example.lidongxue.chat.activity.NewFriendActivity;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.entity.MsgList;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.widget.CustomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by lidongxue on 17-9-26.
 */

public class TwoFragment extends Fragment {

    @BindView(R.id.llNewFriend)
    LinearLayout mllNewFriend;
    @BindView(R.id.tvNewFriendUnread)
    TextView mtvNewFriendUnread;
    @BindView(R.id.llGroup)
    LinearLayout mllGroup;
    @BindView(R.id.tvNewGroupUnread)
    TextView mtvNewGroupUnread;
    @BindView(R.id.llcontact_list)
    LinearLayout mllcontact_list;
    @BindView(R.id.contact_expand_list)
    ExpandableListView mcontact_expand_list;
    private ConnectionService service;
    private List<UserBean> contact;
    private User user;
    private View mExitView;
    private CustomDialog mExitDialog;
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("OneFragment_onCreateView");
        bind();
        View rootView1 = inflater.inflate(R.layout.header_rv_contacts, container, false);
        ButterKnife.bind(this, rootView1);
        initView(rootView1);
        return rootView1;
    }
    private void initView(View rootView) {
        System.out.println("twoFragment_initView()");
        mllNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).jumpToActivity(NewFriendActivity.class);
                mtvNewFriendUnread.setVisibility(View.GONE);
            }
        });
        mllGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mcontact_expand_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //i代表groupPosition,i１代表childPosition,l代表id
                String friendName = contact.get(i).getDetails().get(i1).getUserIp();
                MsgList msgList = new User_DB(getActivity()).checkMsgList(user.getUser_id(), friendName);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("msg_list_id", msgList.getMsg_list_id());
                intent.putExtra("to_name", msgList.getTo_name());
                startActivity(intent);
                return false;
            }
        });
    }
    /**
     * 绑定服务
     */
    private void bind() {
        Log.d("---bind()--","服务器连接成功");
        //开启服务获得与服务器的连接
        intent = new Intent(getActivity(), ConnectionService.class);
        getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            service = binder.getService();
            user = service.getUser();
            if(user!=null){
                Log.d("---twoFragment1-绑定服务-",user.getUser_name());
                Log.d("---twoFragment2--",user.getUser_id()+"");
            }
        }
        //调用unbindService()解除服务　该方法不会被调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("---twoFragment３-解除服务-",user.getUser_name());
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("twoFragment_onCreate");
    }


}
