package com.example.lidongxue.chat.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chataidl.IMyAidlInterface;
import com.example.lidongxue.chataidl.User;

import java.util.List;

/**
 * Created by lidongxue on 17-10-21.
 */

public class SearchActivity extends BaseActivity {
    private  SearchView mSearchView;
    //由AIDL文件生成的Java类
    private IMyAidlInterface mIMyAidlInterface;
    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;
    //包含User对象地　list;
    private List<User> musers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initToolBar(true,"");
        Log.i(this.getClass().getSimpleName(), "is:"+ BaseApp.isBondService);

        Log.i(this.getClass().getSimpleName(), "is service:"+BaseApp.service);

    }

    /**
     * 按钮的点击事件，点击之后调用服务端的addUserIn方法
     *
     * @param view
     */
    public void addUser(View view) {
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
                    musers = mIMyAidlInterface.getUserList();
                    Log.e(getLocalClassName(), musers.toString());
                    for(User user0: musers){
                        System.out.println("用户名："+user0.getUser_name()+"; ---"
                                +"密码："+user0.getUser_psd()+"\n");

                    }


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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        if(mSearchView == null){
            Log.e("SearchView","Fail to get Search View.");
            return true;
        }
        // 获取搜索服务管理器
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       /* ComponentName cn = new ComponentName(this,SearchActivity.class);
        // 通过搜索管理器，从searchable activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
        SearchableInfo info = searchManager.getSearchableInfo(cn);*/
        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
       if(info == null){
            Log.e("SearchableInfo","Fail to get search info.");
        }
        // 将searchable activity的搜索信息与search view关联
        mSearchView.setSearchableInfo(info);
        //setIconifiedByDefault(false)该属性实现了不点击图标直接显示搜索框　false代表着搜索内容不覆盖图标；
        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
       // return true;

        return super.onCreateOptionsMenu(menu);
    }
}
