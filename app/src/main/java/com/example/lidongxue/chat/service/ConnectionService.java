package com.example.lidongxue.chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.database.cache.UserCache;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;
import com.example.lidongxue.chat.rxbus.RxBus;
import com.example.lidongxue.chat.rxbus.event.FriendListenerEvent;
import com.example.lidongxue.chat.rxbus.event.HandleEvent;
import com.example.lidongxue.chat.utils.LogUtil;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.disco.packet.DiscoverInfo;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lidongxue on 17-10-17.
 */

public class ConnectionService extends Service {
    public static final String SERVER_NAME = "127.0.0.1";//openfire对应的主机名,即域名
    public static final String SERVER_IP = "10.10.11.109";//ip 10.0.2.2（模拟器访问主机的ＩＰ）
    public static final String SERVER_IP1 = "127.0.0.1";
    public static final int PORT = 5222;//端口
    private XMPPTCPConnection connection;
    private User_DB dbHelper;
    private User user;//用户信息
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("service is Binded!");
        return mBinder;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbHelper = new User_DB(this);
        System.out.println("service is onStartCommand!");
        return super.onStartCommand(intent, flags, startId);
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public ConnectionService getService() {

            LogUtil.d("---getService()--","service连接成功，该方法被调用");
            return ConnectionService.this;
        }

    }
    public ConnectionService getServices() {

        LogUtil.d("---getServices()--","ConnectionService创建，该方法被调用");
        return ConnectionService.this;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service is Created!");
        new Thread(){
            @Override
            public void run() {
                getConnection();
                LogUtil.d("---onCreate()--","service 启动了getConnection()");

            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("service is Unbinded!");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println("service is Rebinded!");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        System.out.println("service is Destroyed!");
        super.onDestroy();
    }

    /**
     * 获得与服务器的连接
     *
     * @return
     */
    public XMPPTCPConnection getConnection() {
        try {
            if (connection == null) {
                XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                        .setHost(SERVER_IP)//服务器IP地址
                        //服务器端口
                        .setPort(PORT)
                        //设置登录状态
                        .setSendPresence(false)
                        //服务器名称
                        .setServiceName(SERVER_NAME)
                        //是否开启安全模式
                        .setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled)
                        //是否开启压缩
                        .setCompressionEnabled(false)

                        //开启调试模式
                        .setDebuggerEnabled(true).build();
                //setSASLAuthenticationEnabled( true)
                //SASLAuthentication.supportSASLMechanism("PLAIN");
                connection = new XMPPTCPConnection(config);

                LogUtil.d("---getConnection(1)--","connection配置");
                connection.connect();
                LogUtil.d("---getConnection(2)--","connect()成功");
                System.out.println("获取登录状态１"+connection.isAuthenticated());
                System.out.println("获取登录用户名1"+connection.getUser());
                System.out.println("获取服务器名1"+connection.getServiceName());
                System.out.println("获取登录用户登录状态１"+connection.getConfiguration().isSendPresence());

                System.out.println("获取登录密码１"+connection.getConfiguration().getPassword());
                System.out.println("获取登录资源１"+connection.getConfiguration().getResource());

            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public boolean disConnect(){
        if(isConnected()){
            connection.disconnect();
            LogUtil.d("---disConnect()--",connection.isConnected());
            return true;
        }
        return false;

    }

    /**
     * 是否连接成功
     *
     * @return
     */
    public boolean isConnected() {

        if (connection == null) {
            LogUtil.d("---isConnected()--","connection对象为空");
            return false;
        }
        if (!connection.isConnected()){
            LogUtil.d("---isConnected()--",connection.isConnected());
            return false;
        }
       /* if (!connection.isConnected()) {

            try {
                connection.connect();
                return true;
            } catch (Exception e) {
                return false;
            }
        }*/
        return true;
    }
    /**
     * 获取用户信息
     *
     * @return
     */
    public User getUser() {

        /*AccountManager manager = AccountManager.getInstance(connection);
        Set<String> set;
        set=manager.getAccountAttributes();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String str = it.next();
            System.out.println("---------"+str);
        }
*/

       /* LogUtil.d("---getUser()--",user.getUser_id());*/
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
    /**
     * 初始化聊天消息监听
     */
    public void initListener() {
        ChatManager manager = ChatManager.getInstanceFor(connection);
        //设置信息的监听
        final ChatMessageListener messageListener = new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                //当消息返回为空的时候，表示用户正在聊天窗口编辑信息并未发出消息
                if (!TextUtils.isEmpty(message.getBody())) {
                    try {
                        JSONObject object = new JSONObject(message.getBody());
                        String type = object.getString("type");
                        String data = object.getString("data");
                        LogUtil.d("TAG", data);
                        message.setFrom(message.getFrom().split("/")[0]);
                        message.setBody(data);
                        dbHelper.insertOneMsg(user.getUser_id(), message.getFrom(), data, System.currentTimeMillis() + "", message.getFrom(), 2);
                        RxBus.getInstance().post(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ChatManagerListener chatManagerListener = new ChatManagerListener() {

            @Override
            public void chatCreated(Chat chat, boolean arg1) {
                chat.addMessageListener(messageListener);
            }
        };
        manager.addChatListener(chatManagerListener);
    }


    /**
     * 登录
     *
     * @param userName
     * @param password
     */
    public void login(final String userName, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //getConnection();
                boolean connec = isConnected();

                LogUtil.d("---login(1)--", connec);
                System.out.println("获取登录状态3" + connection.isAuthenticated());

                if (!connec) {
                    try {
                        connection.connect();
                        LogUtil.d("---login(2)--", isConnected());
                        System.out.println("获取登录状态4" + connection.isAuthenticated());
                    } catch (SmackException e) {
                        RxBus.getInstance().post(new HandleEvent("LoginActivity", false, 2));
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }
                    //return;
                }
                //如果登录权限为true 即已有用户登录
                if (connection.isAuthenticated()) {
                    //将状态设置成在线
                    Presence presence = new Presence(Presence.Type.available);

                    try {
                        connection.sendStanza(presence);
                        user = dbHelper.setUser(userName + " @10.0.2.2", password);//插入数据库
                        UserCache.save(userName + " @10.0.2.2", password);
                        /*getOfflineMessage();//一上线获取离线消息
                        initListener();//登录成功开启消息监听*/
                        RxBus.getInstance().post(new HandleEvent("LoginActivity", true));
                        System.out.println("获取登录用户名5" + connection.getUser());
                        System.out.println("获取登录状态5" + connection.isAuthenticated());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        System.out.println("获取登录状态6" + connection.isAuthenticated());
                        ConnectionService.this.connection.login(userName, password);//登录

                        System.out.println("获取登录状态" + connection.isAuthenticated());
                        System.out.println("获取登录用户名1" + connection.getUser());
                        System.out.println("获取登录用户名2" + connection.getConfiguration().getUsername());
                        System.out.println("获取登录用户登录状态" + connection.getConfiguration().isSendPresence());

                        System.out.println("获取登录密码" + connection.getConfiguration().getPassword());
                        System.out.println("获取登录资源" + connection.getConfiguration().getResource());
                        System.out.println("获取服务器名" + connection.getServiceName());

                        user = dbHelper.setUser(userName + " @10.0.2.2", password);//插入数据库
                        UserCache.save(userName + " @10.0.2.2", password);
                        getOfflineMessage();//一上线获取离线消息
                        initListener();//登录成功开启消息监听
                        RxBus.getInstance().post(new HandleEvent("LoginActivity", true));

                    } catch (Exception e) {
                        e.printStackTrace();
                        RxBus.getInstance().post(new HandleEvent("LoginActivity", false, 1));
                    }
                }
            }
        }).start();
    }

    /**
     * 一上线获取离线消息
     * 设置登录状态为在线
     */
    private void getOfflineMessage() {
        OfflineMessageManager offlineManager = new OfflineMessageManager(connection);
        try {
            List<Message> list = offlineManager.getMessages();
            for (Message message : list) {
                message.setFrom(message.getFrom().split("/")[0]);
                JSONObject object = new JSONObject(message.getBody());
                String type = object.getString("type");
                String data = object.getString("data");
                //保存离线信息
                dbHelper.insertOneMsg(user.getUser_id(), message.getFrom(), data, System.currentTimeMillis() + "", message.getFrom(), 2);
            }
            //删除离线消息
            offlineManager.deleteMessages();
            //将状态设置成在线
            Presence presence = new Presence(Presence.Type.available);

            connection.sendStanza(presence);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获得所有联系人
     */
    public List<UserBean> getContact() {
        List<UserBean> list = new ArrayList<>();
        if (connection != null) {
            Roster roster = Roster.getInstanceFor(connection);
            Collection<RosterGroup> groups = roster.getGroups();
            List<UserBean.UserBeanDetails> detail = new ArrayList<>();
            for (RosterGroup group : groups) {
                UserBean userBean = new UserBean();
                userBean.setGroupName(group.getName());
                List<RosterEntry> entries = group.getEntries();
                for (RosterEntry entry : entries) {
                    UserBean.UserBeanDetails user = new UserBean.UserBeanDetails();
                    user.setUserIp(entry.getUser());
                    user.setPickName(entry.getName());
                    user.setType(entry.getType());
                    user.setStatus(entry.getStatus());
                    detail.add(user);
                    userBean.setDetails(detail);
                }
                list.add(userBean);
            }
        }
        return list;
    }
    /**
     * 获取账户所有属性信息
     * @return
     */
    public Set getAccountAttributes() {
        if(isConnected()) {
            try {
                return AccountManager.getInstance(connection).getAccountAttributes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }
    /**
     * 获取当前登录用户的所有好友信息
     * @return
     */
    public Set getAllFriends() {
        if(isConnected()) {
            return Roster.getInstanceFor(connection).getEntries();
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 获取指定好友用户信息
     *
     * @param user 用户名
     * @return
     */

    public RosterEntry getUserInfo(String user) {
        if (isConnected()) {
            return Roster.getInstanceFor(connection).getEntry(user);
        } else {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
    }
    /**
     * 查询用户
     *
     * //@param connection
     * //@param serverDomain
     * @param userName1
     * @return
     * @throws XMPPException
     */
    public List<User> searchUsers(String userName1) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        List<User> results = new ArrayList<User>();

        System.out.println("查询开始..............." + connection.getHost()
                +"  "+ connection.getServiceName());
        UserSearchManager usm = new UserSearchManager(connection);
        Form searchForm = usm.getSearchForm("search.10.10.11.109");
        Form answerForm = searchForm.createAnswerForm();
        answerForm.setAnswer("Username", true);
        answerForm.setAnswer("search", userName1);
        ReportedData data = usm.getSearchResults(answerForm, "search.127.0.0.1");
        Iterator<ReportedData.Row> it = (Iterator<ReportedData.Row>) data.getRows();
        ReportedData.Row row = null;
        User user1 = null;
        String ansS="";
        while (it.hasNext()) {
            user1 = new User();
            row = it.next();
            user1.setUser_name(row.getValues("Username").toString());
            user1.setUser_name(row.getValues("search").toString());
           // user1.setUser_name(row.getValues("userName").next().toString());
            ansS=row.getValues("search").toString()+"\n";

          //  System.out.println(row.getValues("userAccount").next());
            System.out.println("---Username--"+row.getValues("Username"));
            System.out.println("---search--"+row.getValues("search"));
            System.out.println("---Username--"+ansS);
            results.add(user1);
            // 若存在，则有返回,UserName一定非空，其他两个若是有设，一定非空
        }
        return results;
    }

    /**
     * 添加好友
     *
     * @param account   帐号
     * @param nickName  昵称
     * @param groupName 组名
     */

    public boolean addFriend(String account, String nickName, String[] groupName) {
        try {
            Roster.getInstanceFor(connection).
                    createEntry(account + "@" + SERVER_IP, nickName,  new String[] {String.valueOf(groupName)});
            Log.e("TAG", account + "@" + SERVER_IP + "/smack");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 添加好友 无分组
     *
     * @param account   帐号
     * @param nickName  昵称
     *
     */
    public boolean addFriend(String account, String nickName) {

        if(isConnected()){
        try {
            Roster roster = Roster.getInstanceFor(connection);
           // .createEntry(account + "@" + SERVER_IP, "", groupName);
            roster.createEntry(account.trim() + "@" + SERVER_IP1, nickName, null);
            Log.e("TAG", account + "@" + SERVER_IP1 + "/smack");
            /*Presence presence = new Presence(Presence.Type.subscribe);
            presence.setTo(account.trim() + "@" + SERVER_IP);
           // presence.setFrom();
            //presence.setTo(userId);
            try {
                connection.sendStanza(presence);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }*/
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        }throw new NullPointerException("服务器连接失败，请先连接服务器");

    }
    /*
    * 删除好友*/
    public boolean deleteFriend(String username){
        try {
            Roster roster = Roster.getInstanceFor(connection);
            RosterEntry entry = roster.getEntry(username);
            roster.removeEntry(entry);
            return true;

        }catch (Exception e){

        }
        return false;
    }




    /**
     * 好友信息监听
     */
    public void requestListener() {
        //条件过滤
        StanzaFilter filter = new AndFilter();
        StanzaListener listener = new StanzaListener() {
            @Override
            public void processPacket(Stanza packet) {
                //Presence p = (Presence) packet;
                DiscoverInfo p = (DiscoverInfo) packet;
                Log.e("TAG", "-1-" + p.getFrom() + "--" + p.getType());
                if (p.getType().toString().equals(Presence.Type.subscribe)) {
                    RxBus.getInstance().post(new FriendListenerEvent(p.getFrom(), "subscribe", "MainActivity"));
                    Log.e("TAG", "-2-" + p.getFrom() + "--" + p.getType());
                } else if (p.getType().toString().equals("subscribed")) {
                    RxBus.getInstance().post(new FriendListenerEvent(p.getFrom(), "subscribed", "MainActivity"));
                } else if (p.getType().toString().equals("unsubscribe")) {
                    RxBus.getInstance().post(new FriendListenerEvent(p.getFrom(), "unsubscribe", "MainActivity"));
                }
                else if (p.getType().equals(Presence.Type.unsubscribed)){
                } else if (p.getType().equals(Presence.Type.unavailable)) {
                    System.out.println("好友下线！");
                } else {
                    System.out.println("好友上线！");
                }
            }
        };
        connection.addAsyncStanzaListener(listener, filter);
    }

    /**
     * 拒绝好友申请
     *
     * @param userId 用户id
     */
    public void refuse(String userId) {
        Presence presence = new Presence(Presence.Type.unsubscribe);
        presence.setTo(userId);
        try {
            connection.sendStanza(presence);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 接收好友申请
     *
     * @param userId 用户id
     */
    public void accept(String userId) {
        Presence presence = new Presence(Presence.Type.subscribed);
        presence.setTo(userId);
        try {
            connection.sendStanza(presence);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个新用户
     *
     * @param username 用户名
     * @param password 密码
     * @param attr     一些用户资料
     * @see AccountManager
     */
    public void registerAccount(final String username, final String password, final Map<String, String> attr) {
       new Thread(){
           @Override
           public void run() {
               getConnection();
               isConnected();
               LogUtil.d("---registerAccount--",isConnected());
               AccountManager manager = AccountManager.getInstance(connection);
               try {
                   if (attr == null) {
                       manager.createAccount(username, password);
                   } else {
                       manager.createAccount(username, password, attr);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }.start();

    }
    /**
     * 退出登录
     *
     * @return code
     * @code true 退出成功
     * @code false 退出失败
     */
    public boolean logout() {
        try {
            new Thread(){
                @Override
                public void run() {
                    Presence presence = new Presence(Presence.Type.available);
                    try {
                        System.out.println("获取登录状态２"+connection.isAuthenticated());
                        connection.disconnect(presence);
                        LogUtil.d("---logout()--","退出成功");
                        System.out.println("获取登录状态２"+connection.isAuthenticated());
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                        LogUtil.d("---logout()--","退出失败１");
                    }

                    //connection.instantShutdown();


                }
            }.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("---logout()--","退出失败 ２");
            return false;
        }
    }
    public boolean logout1() {
        try {
            new Thread(){
                @Override
                public void run() {
                    /*Presence presence = new Presence(Presence.Type.available);
                    try {
                        System.out.println("获取登录状态２"+connection.isAuthenticated());
                        connection.disconnect(presence);
                        LogUtil.d("---logout()--","退出成功");
                        System.out.println("获取登录状态２22"+connection.isAuthenticated());
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                        LogUtil.d("---logout()--","退出失败１");
                    }*/
                    System.out.println("获取登录状态２"+connection.isAuthenticated());

                    connection.instantShutdown();
                    LogUtil.d("---logout()--","退出成功");
                    System.out.println("获取登录状态22"+connection.isAuthenticated());

                }
            }.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("---logout()--","退出失败 ２");
            return false;
        }
    }
    /**
     * 修改密码
     *
     * @param newPassword 　新密码
     * @return code
     * @code true 修改成功
     * @code false 修改失败
     */
    public boolean changePassword(String newPassword) {
        try {
            AccountManager manager = AccountManager.getInstance(connection);
            manager.changePassword(newPassword);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除当前登录的用户信息(从服务器上删除当前用户账号)
     * @return
     */
    public boolean deleteUser() {
        if (!isConnected()) {
            return false;
        }
        try {
            AccountManager manager = AccountManager.getInstance(connection);
            manager.deleteAccount();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}