package com.example.lidongxue.chat.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.entity.MsgList;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;
import com.example.lidongxue.chat.service.ConnectionService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-23.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.ivUserPic)
    ImageView mivUserPic;

    @BindView(R.id.user_info_id)
    TextView muser_info_id;
    @BindView(R.id.user_info_type)
    TextView muser_info_type;
    /*@BindView(R.id.user_info_status)
    TextView muser_info_status;*/
    @BindView(R.id.user_add)
    Button muser_add;
    @BindView(R.id.user_delete)
    Button muser_delete;
    private ConnectionService service;
    private List<User> userinfo;
    private User user;
    Intent intent;
    String user_contacts_name;
    int start_type;
    UserBean.UserBeanDetails user_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        intent=getIntent();
       // String user_contacts_name=intent.getStringExtra("user_id");
        Bundle bundle = intent.getExtras();
        user_contacts_name= bundle.getString("user_id");
        start_type=bundle.getInt("start_type");
        initToolBar(true,user_contacts_name);


        if(start_type==1){
            muser_add.setText("发起聊天");

        }
        if(BaseApp.service!=null){
            user = BaseApp.service.getUser();
            Log.i(this.getClass().getSimpleName(), "UserInfoActivity  user.getUser_id() is service:" + user.getUser_id());
            Log.i(this.getClass().getSimpleName(), "UserInfoActivity  user.getUser_name()is service:" + user.getUser_name());
            user_info= BaseApp.service.getUserInfo(user_contacts_name);
            Log.i(this.getClass().getSimpleName(), "UserInfoActivity user_info.getUserIp() is service:" + user_info.getUserIp()
            +";user_info.getPickName():"+user_info.getPickName()+";user_info.getStatus():"+user_info.getStatus()+";user_info.getType():"+user_info.getType());
            muser_info_id.setText(user_info.getUserIp());
            muser_info_type.setText(user_info.getType().toString());
           /* if (user_info.getStatus().toString().isEmpty()) {
                muser_info_status.setText("null");
            }else{
            muser_info_status.setText(user_info.getStatus().toString());
            }*/
            muser_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MsgList msgList = new User_DB(UserInfoActivity.this).checkMsgList(user.getUser_id(), user_contacts_name);
                    Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
                    intent.putExtra("msg_list_id", msgList.getMsg_list_id());
                    intent.putExtra("to_name", user_info.getUserIp());//user_contacts_name与user_info.getUserIp()一致
                    startActivity(intent);
                }
            });
            muser_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean isdelete=BaseApp.service.deleteFriend(user_info.getUserIp());
                    Log.d(getClass().getSimpleName(),"删除好友isdelete："+isdelete);
                    if(isdelete){
                        Log.d(getClass().getSimpleName(),"删除好友成功："+isdelete);
                        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }

        /*intent=getIntent();
        userinfo= (List<User>) intent.getSerializableExtra("user_info");

        bind();

        muser_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.addFriend((userinfo.get(0)).getUser_name(),null);
            }
        });*/
    }

    /**
     * 绑定服务
     */
    private void bind() {
        Log.d("---bind()--","服务器连接成功");
        //开启服务获得与服务器的连接
        intent = new Intent(this, ConnectionService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            service = binder.getService();
            user = service.getUser();
            if(user!=null){
                Log.d("---UserInfoAct-绑定服务-",user.getUser_name());

                // person_name.setText(user.getUser_name());
            }
            //service.addFriend((userinfo.get(0)).getUser_name(),null);



        }
        //调用unbindService()解除服务　该方法不会被调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("---fourFragment３-解除服务-",user.getUser_name());
        }
    };
}
