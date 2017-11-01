package com.example.lidongxue.chat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lidongxue.chat.activity.AddFriActivity;
import com.example.lidongxue.chat.activity.AddFriendActivity;
import com.example.lidongxue.chat.activity.BaseActivity;
import com.example.lidongxue.chat.activity.LoginActivity;
import com.example.lidongxue.chat.activity.SearchActivity;
import com.example.lidongxue.chat.adapter.Myadapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.fragment.FourFragment;
import com.example.lidongxue.chat.fragment.OneFragment;
import com.example.lidongxue.chat.fragment.ThreeFragment;
import com.example.lidongxue.chat.fragment.TwoFragment;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.utils.LogUtil;
import com.example.lidongxue.chat.view.MyViewPager;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class MainActivity extends BaseActivity
        implements RadioGroup.OnCheckedChangeListener
        ,ViewPager.OnPageChangeListener {
             /**/
    MyViewPager vp;
    RadioGroup rg;
    private  List<Fragment> fragmentList;

    private Subscription subscription;
    //private ConnectionService service;
    public String requestName = "";//请求的用户
    public int acceptStatus=0;
    private XMPPTCPConnection connection;
    private User mUser;//当前登录用户
    private boolean isError = false;//标志是否加入聊天室出错
    private Handler handler = new Handler();
    private boolean isBond=false;
    private Boolean tag;

    public String getRequestName() {
        return requestName;
    }
    public int getAcceptStatus(){
        return acceptStatus;
    }

    public String logo_name="Chat";
    public void setLogo_name(String logo_name){
        this.logo_name=logo_name;
    }
    public String getLogo_name(){
        return logo_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LogUtil.d("---Activity生命周期--","onCreate(主程序)");
        if(!isBond){
            bindService();
        }
        Log.i(this.getClass().getSimpleName(), "is:"+BaseApp.isBondService);

        Log.i(this.getClass().getSimpleName(), "is service:"+BaseApp.service);
        initView();

    }

    /**
     * 初始化布局
     */
    public  void initView(){
        //把Fragment添加到集合里面去
        fragmentList = new ArrayList<>();
        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        fragmentList.add(new ThreeFragment());
        fragmentList.add(new FourFragment());
        //隐藏ActionBar
        // getSupportActionBar().hide();
        /*getSupportActionBar();*/
        Toolbar toolbar=initToolBar(false, "");
        toolbar.setTitle(getLogo_name());



        //解析控件
        vp = (MyViewPager) findViewById(R.id.main_activity_content);
        rg = (RadioGroup) findViewById(R.id.main_activity_radio_group);
        //设置点击事件(多了个change，导包用RadioGroup.OnCheckedChangeListener)
        ////but.setOnClickListener(this);不明白
        rg.setOnCheckedChangeListener(MainActivity.this);
        vp.addOnPageChangeListener((ViewPager.OnPageChangeListener) MainActivity.this);
        //通过适配器把Fragment添加到主界面上
        //vp.setOffscreenPageLimit(3);
        vp.setAdapter(new Myadapter(getSupportFragmentManager(), fragmentList));
        //vp.setCurrentItem(0);

    }


    /*RadioButton   切换Fragment*/
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        //声明一个下标,并赋值
        RadioButton rgc;
        int index = 0;
        switch (checkedId) {
            case R.id.main_activity_message_rbt:
                index = 0;
                break;
            case R.id.main_activity_contacts_rbt:
                index = 1;
                break;
            case R.id.main_activity_friendcircle_rbt:
                index = 2;
                break;
            case R.id.main_activity_mine_rbt:
                index = 3;
                break;

        }
        //设置vp选项，与View_RadioButton关联起来 且关闭切换动画效果　但不知实现了不
        vp.setCurrentItem(index,false);

    }
    @Override
    public void onPageSelected(int position) {

        RadioButton radioButton = (RadioButton) rg.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* getMenuInflater().inflate(R.menu.menu_main, menu);*/
        MenuInflater infla=new MenuInflater(this);
        infla.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.menu_search:
                Intent intent1=new Intent(this,SearchActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.menu_add_one:
                Intent intent2=new Intent(this,AddFriendActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.menu_add_two:
                Intent intent3=new Intent(this,AddFriActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                break;

        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * 绑定服务
     */
    public void bindService() {

        //开启服务获得与服务器的连接
        Intent intent = new Intent(this, ConnectionService.class);
        bindService(intent,connection1 , BIND_AUTO_CREATE);
//       为何报错？System.out.println("获得当前登录用户名"+mUser.getUser_name());
        //startService(intent);
       startService(intent);
        // RequestListener();
    }

    public ServiceConnection connection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            //service = binder.getService();
            BaseApp.service = binder.getService();
            isBond=true;

            Log.d("---bindService(主程序)--","service is connect"+BaseApp.service);
            //getConnection()会判断是否连接过
            //connection = service.getConnection();
            connection= BaseApp.service.getConnection();
            //service.connection这是开放了该变量的访问权限

            Boolean tag=connection.isAuthenticated();
            if(!tag){
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                Log.d("---bindService(主程序)--","未登录");
            }else {
                //添加好友申请监听
                //service.requestListener();
                 BaseApp.service.requestListener();
                //添加聊天室邀请监听
                //setGroupInviteListener();
                //mUser = service.getUser();
                mUser = BaseApp.service.getUser();
                Log.d("---getUser(主程序)--",mUser.getUser_name());}
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBond=false;
        }
    };

    /*private void setGroupInviteListener() {
        MultiUserChatManager
                .getInstanceFor(connection)
                .addInvitationListener(new InvitationListener() {
                    @Override
                    public void invitationReceived(XMPPConnection conn, final MultiUserChat room, final String inviter, final String reason, final String password, Message message) {
                        //进入聊天室对话框
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showGroupInviteDialog(room.getRoom(), inviter, reason, password);
                            }
                        });
                    }
                });
    }
    private void showGroupInviteDialog(final String room, String inviter, String reason, final String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(inviter.substring(0, inviter.indexOf("@")) +
                "邀请您加入" + room.substring(0, room.indexOf("@")))
                .setMessage(reason)
                .setPositiveButton("接受", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        joinChatRoom(room.substring(0, room.indexOf("@")), mUser.getUser_name(), password);
                        if (!isError) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, GroupChatActivity.class);
                            intent.putExtra("GroupName", room.substring(0, room.indexOf("@")));
                            intent.putExtra("JID", room);
                            startActivity(intent);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    *//**
     * 加入一个群聊聊天室
     *
     * @param roomName 聊天室名字
     * @param nickName 用户在聊天室中的昵称
     * @param password 聊天室密码
     * @return
     *//*
    public MultiUserChat joinChatRoom(String roomName, String nickName, String password) {

        try {
            if (!service.isConnected()) {
                Toast.makeText(this, "服务器连接失败，请先连接服务器", Toast.LENGTH_SHORT).show();
                return null;
            }
            // 使用XMPPConnection创建一个MultiUserChat窗口
            MultiUserChat muc = MultiUserChatManager.getInstanceFor(connection).
                    getMultiUserChat(roomName + "@conference." + connection.getServiceName());
            // 聊天室服务将会决定要接受的历史记录数量
            DiscussionHistory history = new DiscussionHistory();
            history.setMaxChars(0);
            // history.setSince(new Date());
            // 用户加入聊天室
            muc.join(nickName, password);
            isError = false;
            return muc;
        } catch (XMPPException | SmackException e) {
            isError = true;
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "加入失败" + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    *//**
     * 观察请求状态
     *//*
    public void RequestListener() {
        Log.i("--MainActivity-reqN1-","subscribe-"+requestName);

        subscription = RxBus.getInstance().toObserverable(FriendListenerEvent.class).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FriendListenerEvent>() {
                    @Override
                    public void call(FriendListenerEvent friendListenerEvent) {
                        requestName = friendListenerEvent.getRequestName();
                        Log.i("--MainActivity-reqN2-","subscribe"+requestName);
                        if ("MainActivity".equals(friendListenerEvent.getReciverClass())) {
                            if ("subscribe".equals(friendListenerEvent.getRequestType())) {
                                acceptStatus=1;
                                Log.i("--MainActivity-reqN3-","subscribe"+requestName);
                                //收到好友请求
                               // showDialog("好友申请", "账号为" + requestName + "发来一条好友申请");
                                *//*Intent intent = new Intent();
                                intent.putExtra("acceptStatus",1);
                                intent.putExtra("response", requestName);
                                intent.setAction(NewFriendActivity.RECEIVER_USER);
                                sendBroadcast(intent);*//*
                                Log.i("--MainActivity-reqN4-","subscribe"+requestName);
                            } else if ("subscribed".equals(friendListenerEvent.getRequestType())) {
                                acceptStatus=2;
                                //通过好友请求
                                *//*Intent intent = new Intent();
                                intent.putExtra("acceptStatus",2);
                                intent.putExtra("response", requestName);
                                intent.setAction(NewFriendActivity.RECEIVER_USER);
                                sendBroadcast(intent);*//*
                              //  showDialog("通过了好友请求", "账号为" + requestName + "通过了您的好友请求");
                                Log.i("--MainActivity-reqN-","subscribed"+requestName);
                            } else if ("unsubscribed".equals(friendListenerEvent.getRequestType())) {
                                acceptStatus=3;
                                //拒绝好友请求
                               *//* Intent intent = new Intent();
                                intent.putExtra("acceptStatus",3);
                                intent.putExtra("response", requestName);
                                intent.setAction(NewFriendActivity.RECEIVER_USER);
                                sendBroadcast(intent);*//*
                               // showDialog("拒绝了好友请求", "账号为" + requestName + "拒绝了您的好友请求并且将你从列表中移除");
                                Log.i("--MainActivity-reqN-","unsubscribe"+requestName);
                            }
                        }
                    }
                });
    }
    public void showDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(content).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BaseApp.service.accept(requestName);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BaseApp.service.refuse(requestName);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
*/

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("---Activity生命周期--","onStart(主程序)");

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("---Activity生命周期--","onResume(主程序)");
       // RequestListener();
    }
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("---Activity生命周期--","onPause(主程序)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("---Activity生命周期--","onStop(主程序)");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("---Activity生命周期--","onRestart(主程序)");
        Log.i(this.getClass().getSimpleName(), "is:"+BaseApp.isBondService);
        Log.i(this.getClass().getSimpleName(), "is service:"+BaseApp.service);
        Log.i(this.getClass().getSimpleName(), "获取一下connection:"+connection);
        Log.i(this.getClass().getSimpleName(), "是否登录过:"+BaseApp.service.getConnection().isAuthenticated());


        if(!connection.isAuthenticated()){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            Log.d("---bindService(主程序)--","未登录");
        }
    }
    @Override
    protected void onDestroy() {
        /*if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }*/
        if(isBond){
            unbindService(connection1);
        }

        super.onDestroy();
        LogUtil.d("---Activity生命周期--","onDestroy(主程序)");
    }

}
