package com.example.lidongxue.chat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lidongxue.chat.entity.Contact;
import com.example.lidongxue.chat.entity.Msg;
import com.example.lidongxue.chat.entity.MsgList;
import com.example.lidongxue.chat.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lidongxue on 17-10-9.
 */

public class User_DB {

    private SQLiteDatabase db;
    private User_SQL dbHelper;
    public User_DB(Context context){
        dbHelper = new User_SQL(context);
        db = dbHelper.getWritableDatabase();
    }
    public void Close() {
        db.close();
        dbHelper.close();
    }
   /*根据用户名获取用户id*/
    public User getUser(String userName){
        User user = null;
        Cursor cursor = db.rawQuery("select * from user where user_name=?", new String[]{userName});
        if (cursor.moveToNext()) {
            int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
            String user_name = cursor.getString(cursor.getColumnIndex("user_name"));
            String user_psd = cursor.getString(cursor.getColumnIndex("user_psd"));
            String user_head_img = cursor.getString(cursor.getColumnIndex("user_head_img"));
            user = new User(user_id, user_name, user_psd, user_head_img);
        }
        return user;
    }


    /**
     * 登录后插入用户
     *
     * @param userName
     * @param userPsd
     * @return
     */
    public User setUser(String userName, String userPsd) {
        User user = getUser(userName);
        if (user == null) {
            ContentValues values = new ContentValues();
            values.put("user_name", userName);
            values.put("user_psd", userPsd);
            values.put("user_head_img", "");
            db.insert("user", null, values);
            user = getUser(userName);
            return user;
        } else {
            return user;
        }
    }
    /*
    * 获取添加好友信息
    * */
    public Contact getContact(String from_name, String to_name){
        Contact contact=null;
        Cursor cursor = db.rawQuery("select * from add_contact where from_name=? and to_name=?", new String[]{from_name,to_name});
        if (cursor.moveToNext()) {
            int contact_id = cursor.getInt(cursor.getColumnIndex("add_contact_id"));
            String from_name1 = cursor.getString(cursor.getColumnIndex("from_name"));
            String to_name1 = cursor.getString(cursor.getColumnIndex("to_name"));
            int sub =cursor.getInt(cursor.getColumnIndex("sub"));
            int subed =cursor.getInt(cursor.getColumnIndex("subed"));
            int unsub =cursor.getInt(cursor.getColumnIndex("unsub"));
            int unsubed =cursor.getInt(cursor.getColumnIndex("unsubed"));
            String sub_time = cursor.getString(cursor.getColumnIndex("sub_time"));
            contact = new Contact(contact_id ,from_name1,to_name1,sub,subed,unsub,unsubed,sub_time);
        }
        return contact;
    }

    /*
    * 添加好友信息
    * */
    public Contact setContact(String from_name, String to_name,int sub,int subed,int unsub,int unsubed,String sub_time){
        Contact contact=getContact(from_name,to_name);
        if(contact==null){
            ContentValues values = new ContentValues();
            values.put("from_name", from_name);
            values.put("to_name", to_name);
            values.put("sub", sub);
            values.put("subed", subed);
            values.put("unsub", unsub);
           // values.put("unsubed", unsubed);
            values.put("sub_time", sub_time);
            db.insert("add_contact", null, values);
            contact = getContact(from_name,to_name);
            return contact;
        }
        Cursor cursor=db.rawQuery("select * from add_contact",null);
        for(int i=0;i<8;i++) {
            Log.d("lwx", "" + cursor.getColumnName(i));
        }
            return contact;
    }
    /*
    * 更新添加好友状态信息
    * */

    public boolean updateContact(String from_name, String to_name,int sub,int subed,int unsub,int unsubed,String sub_time){
        ContentValues values = new ContentValues();
        values.put("sub", sub);
        values.put("subed", subed);
        values.put("unsub", unsub);
        values.put("unsubed", unsubed);
        values.put("sub_time", sub_time);
        db.update("add_contact", values, "from_name=? and to_name=?", new String[]{from_name, to_name });
        return true;
    }
    //获取新朋友状态
    public List<Contact> getContactAll(){
        List<Contact> contact_list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from add_contact order by sub_time desc",
                new String[]{});
        if(cursor!=null){
            while (cursor.moveToNext()) {
                int addcontact_id = cursor.getInt(cursor.getColumnIndex("add_contact_id"));
                String from_name = cursor.getString(cursor.getColumnIndex("from_name"));
                String to_name = cursor.getString(cursor.getColumnIndex("to_name"));
                int sub = cursor.getInt(cursor.getColumnIndex("sub"));
                int subed = cursor.getInt(cursor.getColumnIndex("subed"));
                int unsub = cursor.getInt(cursor.getColumnIndex("unsub"));
                int unsubed = cursor.getInt(cursor.getColumnIndex("unsubed"));
                String sub_time = cursor.getString(cursor.getColumnIndex("sub_time"));
                Contact contact=new Contact(addcontact_id,from_name,to_name,sub,subed,unsub,unsubed,sub_time);
                contact_list.add(contact);
            }
            return contact_list;
        }else {
        return null;
    }
    }
    /**
     * 获取聊天列表
     *
     * @param userId
     * @return
     */
    public List<MsgList> getMsgAllList(int userId) {
        List<MsgList> list = new ArrayList<>();
        //按聊天时间顺序　倒排序
        Cursor cursor = db.rawQuery("select * from msg_list where user_id=? order by last_msg_time desc",
                new String[]{userId + ""});
        while (cursor.moveToNext()) {
            int msg_list_id = cursor.getInt(cursor.getColumnIndex("msg_list_id"));
            int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
            String to_name = cursor.getString(cursor.getColumnIndex("to_name"));
            String last_msg = cursor.getString(cursor.getColumnIndex("last_msg"));
            String last_msg_time = cursor.getString(cursor.getColumnIndex("last_msg_time"));
            int msg_list_type = cursor.getInt(cursor.getColumnIndex("msg_list_type"));
            MsgList msgList = new MsgList(msg_list_id, user_id, to_name, last_msg, last_msg_time, msg_list_type);
            list.add(msgList);
        }
        return list;
    }


    /**
     * 获取对应的人的聊天列表
     *
     * @param userId
     * @return
     */
    public MsgList getMsgList(int userId, String toName) {
        MsgList msgList = null;
        try {
            Cursor cursor = db.rawQuery("select * from msg_list where user_id=? and to_name=? ", new String[]{userId + "", toName});
            while (cursor.moveToNext()) {
                int msg_list_id = cursor.getInt(cursor.getColumnIndex("msg_list_id"));
                int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
                String to_name = cursor.getString(cursor.getColumnIndex("to_name"));
                String last_msg = cursor.getString(cursor.getColumnIndex("last_msg"));
                String last_msg_time = cursor.getString(cursor.getColumnIndex("last_msg_time"));
                int msg_list_type = cursor.getInt(cursor.getColumnIndex("msg_list_type"));
                msgList = new MsgList(msg_list_id, user_id, to_name, last_msg, last_msg_time, msg_list_type);
            }
        } catch (Exception e) {
            Log.e("error", "getMsgList: " + e.toString());
        }

        return msgList;
    }
    /**
     * 插入一条和某某聊天的消息list
     *
     * @param userId
     * @param to_name
     * @param last_msg
     * @param last_msg_time
     * @param msg_list_type
     */
    private MsgList insertMsgList(int userId, String to_name, String last_msg, String last_msg_time, int msg_list_type) {
        db.execSQL("insert into msg_list(user_id,to_name,last_msg,last_msg_time,msg_list_type) values(?,?,?,?,?)",
                new Object[]{userId, to_name, last_msg, last_msg_time, msg_list_type});
        MsgList msgList = getMsgList(userId, to_name);
        return msgList;
    }
    /**
     * 检查是否有和对应人聊天的msg_list_id
     *
     * @param userId
     * @param toName
     * @return
     */
    public MsgList checkMsgList(int userId, String toName) {
        MsgList msgList = null;
        msgList = getMsgList(userId, toName);
        if (msgList == null) {
            insertMsgList(userId, toName, "", "", 1);
            msgList = getMsgList(userId, toName);
        }
        return msgList;
    }
    /**
     * 更新消息列表表中最后一条消息，用户消息列表显示
     *
     * @param userId
     * @param msg_list_id
     * @param last_msg
     * @param last_msg_time
     */
    private void updateMsgList(int userId, int msg_list_id, String last_msg, String last_msg_time) {
//        db.execSQL("update msg_list where user_id=? and to_name=? set last_msg=? and last_msg_time=?", new Object[]{userId});

        ContentValues values = new ContentValues();
        values.put("last_msg", last_msg);
        values.put("last_msg_time", last_msg_time);
        db.update("msg_list", values, "user_id=? and msg_list_id=?", new String[]{userId + "", msg_list_id + ""});
    }
    /**
     * 插入消息
     *
     * @param msg_list_id
     * @param from_name
     * @param msg_content
     * @param msg_time
     * @param msg_type
     */
    private void insertMsg(int msg_list_id, String from_name, String msg_content, String msg_time, String msg_type, int from_type) {
        ContentValues values = new ContentValues();
        values.put("msg_list_id", msg_list_id);
        values.put("from_name", from_name);
        values.put("msg_content", msg_content);
        values.put("msg_time", msg_time);
        values.put("msg_type", msg_type);
        values.put("from_type", from_type);
        db.insert("msg", null, values);
    }
    /**
     * 插入一条消息消息
     *
     * @param user_id
     * @param to_name
     * @param msg_content
     * @param msg_time
     */
    public void insertOneMsg(int user_id, String to_name, String msg_content, String msg_time, String send_name, int from_type) {
        MsgList msgList = null;
        msgList = getMsgList(user_id, to_name);
        if (msgList == null) {
            msgList = insertMsgList(user_id, to_name, msg_content, msg_time, from_type);
        } else {
            updateMsgList(user_id, msgList.getMsg_list_id(), msg_content, msg_time);
        }
        insertMsg(msgList.getMsg_list_id(), send_name, msg_content, msg_time, "text", from_type);
    }
    /*
    * 删除某列表的所有聊天信息
    * */
    public void deleteAllMsg(int user_id,String to_name){
        int msg_list_id=getMsgListID(user_id,to_name);
        Log.d("deleteAllMsg 查询列表数：","msg_list_id is:"+msg_list_id);
        if(msg_list_id!=-1) {
            db.execSQL("delete from msg where msg_list_id=?", new String[]{msg_list_id + ""});
            Log.d("deleteAllMsg 删除所有的消息"," ");
        }
    }
    public  int getMsgListID(int user_id,String to_name){
        int msg_list_id=-1;
        List<String>  msg_list_ids=new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from msg_list where user_id=? and to_name=?",
                new String[]{user_id + "",to_name});
        if(cursor!=null){
            Log.d("getMsgListID 查询列表数：",cursor.getCount()+"");
            while (cursor.moveToNext()){
                 msg_list_id = cursor.getInt(cursor.getColumnIndex("msg_list_id"));
                msg_list_ids.add(String.valueOf(msg_list_id));
            }
            Log.d("msg_list_ids 查询列表数：",msg_list_ids.size()+"");
        }
        return  msg_list_id;

    }

    /**
     * 获取和某某聊天的消息
     *
     * @param msg_list_id
     * @param page
     */
    public List<Msg> getAllMsg(int msg_list_id, int page) {
        List<Msg> list = new ArrayList<>();
        List<Msg> resultList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from msg where msg_list_id=?", new String[]{msg_list_id + ""});
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                int msg_id = cursor.getInt(cursor.getColumnIndex("msg_id"));
                String from_name = cursor.getString(cursor.getColumnIndex("from_name"));
                String msg_content = cursor.getString(cursor.getColumnIndex("msg_content"));
                String msg_time = cursor.getString(cursor.getColumnIndex("msg_time"));
                String msg_type = cursor.getString(cursor.getColumnIndex("msg_type"));
                int from_type = cursor.getInt(cursor.getColumnIndex("from_type"));
                Msg msg = new Msg(msg_id, msg_list_id, from_name, msg_content, msg_time, msg_type, from_type);
                list.add(msg);
            }
        }else {
            list=null;
        }
//        for (int i = 10 * (page - 1); i < 10 * (page); i++) {
//            if (i >= list.size()) {
//                break;
//            }
//            resultList.add(list.get(i));
//        }
        return list;
    }


}
