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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.activity.NewFriendActivity;
import com.example.lidongxue.chat.activity.NewFriendGroupActivity;
import com.example.lidongxue.chat.activity.UserInfoActivity;
import com.example.lidongxue.chat.adapter.ContactsAdapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.widget.CustomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.contact_list)
    ListView mcontact_list;

    private ConnectionService service;
    private List<UserBean> contact;
    List<UserBean.UserBeanDetails> contacts;
    private User user;
    private View mExitView;
    private CustomDialog mExitDialog;
    Intent intent;
    private View rootView1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("OneFragment_onCreateView");
       // bind();
        rootView1 = inflater.inflate(R.layout.header_rv_contacts, container, false);
        ButterKnife.bind(this, rootView1);

        return rootView1;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName(), "is:"+BaseApp.isBondService);
        Log.i(this.getClass().getSimpleName(), "is service:"+BaseApp.service);
        if(BaseApp.service!=null){
            initView(rootView1);
        }

    }

    private void initView(View rootView) {
        System.out.println("twoFragment_initView()");
        mllNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), NewFriendActivity.class);
                intent.putExtra("request", ((MainActivity) getActivity()).getRequestName());
                intent.putExtra("acceptstatus", ((MainActivity) getActivity()).getAcceptStatus());
                mtvNewFriendUnread.setVisibility(View.GONE);
                startActivity(intent);
            }
        });
        mllGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewFriendGroupActivity.class);
                intent.putExtra("request", ((MainActivity) getActivity()).getRequestName());
                intent.putExtra("acceptstatus", ((MainActivity) getActivity()).getAcceptStatus());
                mtvNewFriendUnread.setVisibility(View.GONE);
                startActivity(intent);

            }
        });


        contacts = BaseApp.service.getAllFriends();
        Log.i(this.getClass().getSimpleName(), "contacts is :"+contacts);
        Log.i(this.getClass().getSimpleName(), "getActivity() is :"+getActivity()+";"+getContext()+";");
        ContactsAdapter contactsAdapter=new ContactsAdapter(contacts,getActivity());
        //ContactsAdapter contactsAdapter=new ContactsAdapter(contacts,getContext());
        mcontact_list.setAdapter(contactsAdapter);

        mcontact_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                intent.putExtra("user_id", contacts.get(position).getUserIp());
                intent.putExtra("start_type",1);

                startActivity(intent);
            }
        });


    }



    /**
     * 绑定服务
     *//*
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
*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("twoFragment_onCreate");
    }


}
