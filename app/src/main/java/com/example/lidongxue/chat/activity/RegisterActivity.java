package com.example.lidongxue.chat.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-17.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.username)
    TextInputLayout username;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.register)
    Button register;
    private EditText name;
    private EditText psd;
    private ConnectionService service;
    private boolean isBond=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initToolBar(true, getString(R.string.activity_register_button_text));


        if(!isBond){
            bindService();
        }


        register.setOnClickListener(RegisterActivity.this);
        name = username.getEditText();
        psd = password.getEditText();

    }

    /**
     * 绑定服务
     */
    public void bindService() {
        //开启服务获得与服务器的连接
        Intent intent = new Intent(this, ConnectionService.class);
        bindService(intent,connection1, BIND_AUTO_CREATE);
    }
    ServiceConnection connection1= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            service = binder.getService();
            isBond=true;
            LogUtil.d("---bindService(注册)--","service is connect");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBond=false;
        }
    };

    @Override
    public void onClick(View v) {
        Map<String, String> map;
        String n;
        String p;
        switch (v.getId()) {
            case R.id.register:
                n = name.getText().toString();
                p = psd.getText().toString();
                if (TextUtils.isEmpty(n)) {
                    showSnackBar(v, "请输入帐号");
                } else if (TextUtils.isEmpty(p)) {
                    showSnackBar(v, "请输入密码");
                } else {
                    /** attributes-->>>
                     name,first,last,email,city,state,zip,phone,url,date,misc,text,remove
                     */
                    map = new HashMap<>();
                    map.put("name", "我是name");
                    map.put("email", "我是email");
                    service.registerAccount(n, p, map);


                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isBond){
            unbindService(connection1);
        }
    }
}
