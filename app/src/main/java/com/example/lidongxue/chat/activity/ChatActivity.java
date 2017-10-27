package com.example.lidongxue.chat.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.adapter.ChatAdapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.entity.Msg;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.rxbus.RxBus;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lidongxue on 17-10-23.
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener{
    @BindView(R.id.chat_view_list)
    ListView mchat_view_list;
    @BindView(R.id.chat_content)
    EditText mchat_content;
    @BindView(R.id.chat_send)
    Button mchat_send;
    private int msg_list_id;
    private String to_name;
    private ChatAdapter adapter;
    private User_DB helper;
    private List<Msg> msgList = new ArrayList<>();
    private Subscription subscription;
    private XMPPTCPConnection xmpptcpConnection;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        msg_list_id = getIntent().getIntExtra("msg_list_id", -1);
        to_name = getIntent().getStringExtra("to_name");
        helper=new User_DB(this);
        initToolBar(true, to_name);
        if(BaseApp.service!=null) {
            Log.i(this.getClass().getSimpleName(), "charActivity is:" + BaseApp.isBondService);
            Log.i(this.getClass().getSimpleName(), "charActivity is service:" + BaseApp.service);
            user = BaseApp.service.getUser();
            Log.i(this.getClass().getSimpleName(), "charActivity is service:" + user.getUser_id());
            Log.i(this.getClass().getSimpleName(), "charActivity is service:" + user.getUser_name());
            xmpptcpConnection=BaseApp.service.getConnection();
            mchat_content.setOnTouchListener(this);
            mchat_send.setOnClickListener(this);

            getMsg();
            newMsg();
            /*adapter = new ChatAdapter(msgList, this);
            mchat_view_list.setAdapter(adapter);*/
            mchat_view_list.setSelection(msgList.size() - 1);
        }


    }
    /**
     * 重数据库中获取聊天记录
     */
    public void getMsg() {
        msgList.clear();
        List<Msg> list１ = helper.getAllMsg(msg_list_id, -1);
        if(list１!=null){
            Log.i(this.getClass().getSimpleName(), "聊天记录不为空1" + list１);
            msgList.addAll(list１);
            if (adapter == null) {
                Log.i(this.getClass().getSimpleName(), "适配器对象为空" + adapter);
                adapter = new ChatAdapter(msgList, this);
                mchat_view_list.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
                Log.i(this.getClass().getSimpleName(), "适配器对象不为空" + adapter);
            }
            mchat_view_list.setSelection(msgList.size() - 1);
        }


    }
    /**
     * 发送一条消息
     *
     * @param msg     消息内容
     * @param to_name 好友userJid
     * @return
     */
    public boolean sendMsg(String type, String msg, String to_name) {
        boolean isSend;
        String json = toJson(msg, type);
        try {
            ChatManager manager = ChatManager.getInstanceFor(xmpptcpConnection);
            //得到与另一个帐号的连接，这里是一对
            //格式为lee@10.10.11.109;服务器ip
            Chat chat = manager.createChat(to_name, null);
            chat.sendMessage(json);
            Log.i(this.getClass().getSimpleName(), "charActivity is service:" +
                    user.getUser_id()+";"+ System.currentTimeMillis()+";"+getDate()+";"+user.getUser_name()+";");
            helper.insertOneMsg(user.getUser_id(), to_name, msg, getDate() + "", user.getUser_name(), 1);
            isSend = true;
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
            isSend = false;
        }
        return isSend;
    }

    /**
     * 打包成json字符串
     *
     * @param msg
     * @param type text>>文本,voice>>语音,image>>图片
     */
    private String toJson(String msg, String type) {
        try {
            JSONObject object = new JSONObject();
            object.put("type", type);
            object.put("data", msg);
            Log.i(this.getClass().getSimpleName(), "toJson is :" +object.toString());
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 观察新消息
     */
    public void newMsg() {
        Log.i(this.getClass().getSimpleName(), "执行newMsg()方法" );

        subscription = RxBus.getInstance().toObserverable(Message.class).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Message>() {
                    @Override
                    public void call(Message message) {
                        if (message.getFrom().equals(to_name)) {
                            Log.i(this.getClass().getSimpleName(), "newMsg()收到新消息");
                            getMsg();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_send:
                //发送消息
                String msg = mchat_content.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    boolean b = sendMsg("text", msg, to_name);
                    if (b) {
                        msgList.add(new Msg(user.getUser_name(), msg,  getDate() + "", "text", 1));
                        adapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
                        mchat_content.setText("");
                        // 发送一条消息时，ListView显示选择最后一项
                        mchat_view_list.setSelection(msgList.size() - 1);
                    }
                }
                break;
            default:
                break;
        }

    }
    /**
     * 发送消息时，获取当前事件
     *
     * @return 当前时间
     */
    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
    @Override
    protected void onDestroy() {

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
