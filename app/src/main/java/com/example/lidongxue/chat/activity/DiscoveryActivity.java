package com.example.lidongxue.chat.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.adapter.DiscoveryAdapter;
import com.example.lidongxue.chataidl.IMyAidlInterface;
import com.example.lidongxue.chataidl.User;
import com.example.lidongxue.chataidl.UserPic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-31.
 */

public class DiscoveryActivity extends BaseActivity {
    @BindView(R.id.discovery_list)
    ListView mdiscovery_list;
    private List<UserPic> userpic;
    //由AIDL文件生成的Java类
    private IMyAidlInterface mIMyAidlInterface;
    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        ButterKnife.bind(this);
        initToolBar(true,"朋友圈");

    }
    /**
     * 按钮的点击事件，点击之后调用服务端的addUserIn方法
     *
     * @param view
     */
    public void addUserPic(View view) {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mIMyAidlInterface == null) return;
        User user1 = new User();
        user1.setUser_name("8888");
        user1.setUser_psd("88888");

        try {
            mIMyAidlInterface.addUser(user1);
            Log.e(getLocalClassName(), user1.toString());
            Log.e(getLocalClassName(), user1.getUser_name());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.example.lidongxue.chataidl.aidl");
        intent.setPackage("com.example.lidongxue.chataidl");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(getLocalClassName(), "service connected");
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            mBound = true;

            if (mIMyAidlInterface != null) {
                try {
                    userpic = mIMyAidlInterface.getUserPicAllList();
                    Log.e(getLocalClassName(), userpic.toString());
                    for(UserPic userpic0: userpic){
                        System.out.println("图片id："+userpic0.getPic_id()+"; ---"
                                +"内容："+userpic0.getPic_content()+"; ---"
                                +"发送者："+userpic0.getFrom_name()+"; ---"
                                +"图片："+userpic0.getPic_bigmap()+"; ---"
                                +"时间："+userpic0.getPic_time()+"\n");
                    }
                    mdiscovery_list.setAdapter(new DiscoveryAdapter(DiscoveryActivity.this,userpic));


                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getLocalClassName(), "service disconnected");
            mBound = false;
        }
    };

}
