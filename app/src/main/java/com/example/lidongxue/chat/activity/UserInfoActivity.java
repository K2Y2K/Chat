package com.example.lidongxue.chat.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.entity.User;
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
    @BindView(R.id.user_add)
    Button muser_add;
    private ConnectionService service;
    private List<User> userinfo;
    private User user;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        intent=getIntent();
        String user_contacts_name=intent.getStringExtra("user_id");

        initToolBar(true,user_contacts_name);

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
