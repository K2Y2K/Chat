package com.example.lidongxue.chat.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.rxbus.RxBus;
import com.example.lidongxue.chat.rxbus.event.HandleEvent;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by lidongxue on 17-10-17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.username)
    TextInputLayout username;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.reg)
    TextView reg;

     private EditText user;
     private EditText psd;
    private ConnectionService service;
    private Subscription subscribe;
    private boolean isBond=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        ButterKnife.bind(this);
        LogUtil.d("---Activity生命周期--","onCreate(登录)");
        initToolBar(false, "登录");
        login.setOnClickListener(this);
        reg.setOnClickListener(this);
        user = username.getEditText();
        psd = password.getEditText();

        if(!isBond){
            bindService();
        }
        loginResult();



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("---Activity生命周期--","onPause(登录)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("---Activity生命周期--","onStop(登录)");
    }



    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 绑定服务
     */
    public void bindService() {
        //开启服务获得与服务器的连接
        Intent intent = new Intent(this, ConnectionService.class);
        bindService(intent, connection1, BIND_AUTO_CREATE);

    }
    ServiceConnection connection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            service = binder.getService();
            isBond=true;
            LogUtil.d("---bindService(登录)--","service is connect");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //主动解除绑定　该方法不执行

        }
    };
    /**
     * 观察登录状态
     */
    public void loginResult() {
        subscribe = RxBus.getInstance().toObserverable(HandleEvent.class).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HandleEvent>() {
                    @Override
                    public void call(HandleEvent userBean) {
                        if (userBean.getReceiveClass().equals("LoginActivity") && (Boolean) userBean.getMessage()) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();//跳转后销毁活动
                        } else if (userBean.getReceiveClass().equals("LoginActivity") && !(Boolean) userBean.getMessage()) {
                            if(userBean.getFlag()==1){
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }else if(userBean.getFlag()==2){
                                Toast.makeText(LoginActivity.this, "服务器连接超时", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.login:
                final String username = user.getText().toString();
                final String password = psd.getText().toString();
                LogUtil.d("---R.id.login--",username+"   "+password);
                if (TextUtils.isEmpty(username)) {
                    showSnackBar(v, "请输入帐号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showSnackBar(v, "请输入密码");
                    return;
                }
                service.login(username, password);
                break;
            case R.id.reg:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

        }
    }

    @Override
    protected void onDestroy() {
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        if(isBond){
            unbindService(connection1);
        }
        super.onDestroy();
        LogUtil.d("---Activity生命周期--","onDestroy(登录)");

    }
}





