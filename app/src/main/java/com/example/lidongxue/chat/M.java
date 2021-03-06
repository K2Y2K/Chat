package com.example.lidongxue.chat;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lidongxue.chat.activity.AddFriendActivity;
import com.example.lidongxue.chat.activity.BaseActivity;
import com.example.lidongxue.chat.activity.GroupChatActivity;
import com.example.lidongxue.chat.adapter.Myadapter;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.fragment.FourFragment;
import com.example.lidongxue.chat.fragment.OneFragment;
import com.example.lidongxue.chat.fragment.ThreeFragment;
import com.example.lidongxue.chat.fragment.TwoFragment;
import com.example.lidongxue.chat.rxbus.RxBus;
import com.example.lidongxue.chat.rxbus.event.FriendListenerEvent;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.view.MyViewPager;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class M extends BaseActivity
        implements RadioGroup.OnCheckedChangeListener
        ,ViewPager.OnPageChangeListener {
    /**/
    MyViewPager vp;
    RadioGroup rg;
    private  List<Fragment> fragmentList;

    private Subscription subscription;
    private ConnectionService service;
    private String requestName;//请求的用户
    private XMPPTCPConnection connection;
    private User mUser;//当前登录用户
    private boolean isError = false;//标志是否加入聊天室出错
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        initView();
        new Thread(){
            @Override
            public void run() {
                bindService();
            }
        }.start();


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
        initToolBar(false, "chat");


        //解析控件
        vp = (MyViewPager) findViewById(R.id.main_activity_content);
        rg = (RadioGroup) findViewById(R.id.main_activity_radio_group);
        //设置点击事件(多了个change，导包用RadioGroup.OnCheckedChangeListener)
        ////but.setOnClickListener(this);不明白
        rg.setOnCheckedChangeListener(M.this);
        vp.addOnPageChangeListener((ViewPager.OnPageChangeListener) M.this);
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
                Intent intent1=new Intent(this,M.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.menu_add_one:
                Intent intent2=new Intent(this,AddFriendActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.menu_add_two:

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
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
                service = binder.getService();
                Log.d("---bindService(主程序)--","service is connect");
                connection = service.getConnection();
                //添加好友申请监听
                service.requestListener();
                //添加聊天室邀请监听
                setGroupInviteListener();
                mUser = service.getUser();
                Log.d("---getUser(主程序)--",mUser.getUser_name());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
        startService(intent);
        RequestListener();
    }
    private void setGroupInviteListener() {
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
                            intent.setClass(M.this, GroupChatActivity.class);
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
    /**
     * 加入一个群聊聊天室
     *
     * @param roomName 聊天室名字
     * @param nickName 用户在聊天室中的昵称
     * @param password 聊天室密码
     * @return
     */
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
            Toast.makeText(M.this, "加入失败" + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    /**
     * 观察请求状态
     */
    public void RequestListener() {
        subscription = RxBus.getInstance().toObserverable(FriendListenerEvent.class).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FriendListenerEvent>() {
                    @Override
                    public void call(FriendListenerEvent friendListenerEvent) {
                        requestName = friendListenerEvent.getRequestName();
                        if ("MainActivity".equals(friendListenerEvent.getReciverClass())) {
                            if ("subscrib".equals(friendListenerEvent.getRequestType())) {
                                //收到好友请求
                                showDialog("好友申请", "账号为" + requestName + "发来一条好友申请");
                            } else if ("subscribed".equals(friendListenerEvent.getRequestType())) {
                                //通过好友请求
                                showDialog("通过了好友请求", "账号为" + requestName + "通过了您的好友请求");
                            } else if ("unsubscribe".equals(friendListenerEvent.getRequestType())) {
                                //拒绝好友请求
                                showDialog("拒绝了好友请求", "账号为" + requestName + "拒绝了您的好友请求并且将你从列表中移除");
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
                service.accept(requestName);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                service.refuse(requestName);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

}
