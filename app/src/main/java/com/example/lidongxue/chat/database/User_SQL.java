package com.example.lidongxue.chat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lidongxue on 17-10-9.
 */

public class User_SQL extends SQLiteOpenHelper {
    /*final String CREATE_TABLE_USER ="create table user(u_id string primary key  , "+
            "username string , "+"userpw string , "+"u_pic string , "+
            "u_region string , "+"u_status integer default 0 "+")";
    final String CREATE_TABLE_USER_CONTACT ="create table user_contact(_id integer primary key autoincrement , "+
            "u_id string , "+"u_ids string , "+"foreign key(u_id) references user )";
    final String CREATE_TABLE_USER_CONTACTS ="create table user_contacts(_id integer primary key autoincrement , "+
            "u_id string , "+"to_id string , "+"to_id_name string, "+"to_chat_bg string, "+")";
    final String CREATE_TABLE_USER_CHAT ="create table user_chat(u_id string , "
            +"to_id string , "+" chat_mes string , "+" primary key(u_id,to_id), "+
            "foreign key(u_id) references user "+")";*/
    final String CREATE_TABLE_USER=
            "create table user(user_id  INTEGER primary key autoincrement,user_name varchar,user_psd varchar,user_head_img varchar)";

    final String CREATE_TABLE_MSG_LIST=
            "create table msg_list(msg_list_id  INTEGER primary key autoincrement,user_id INTEGER,to_name varchar,last_msg varchar,last_msg_time varchar,msg_list_type INTEGER)";

    final String CREATE_TABLE_MSG=
            "create table msg(msg_id INTEGER primary key autoincrement,msg_list_id  INTEGER,from_name varchar,msg_content varchar,msg_time varchar,msg_type varchar ,from_type INTEGER)";

    //建表注意区分中文和英文下的空格
    final String CREATE_TABLE_ADD_CONTACT=
            "create table add_contact(add_contact_id  INTEGER primary key autoincrement,from_name varchar,to_name varchar,sub INTEGER , subed  INTEGER ,unsub INTEGER , unsubed INTEGER ,sub_time varchar);";

    public User_SQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public User_SQL(Context context){
        super(context,"chat.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //第一次使用数据库时自动创建用户表);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_MSG_LIST);
        sqLiteDatabase.execSQL(CREATE_TABLE_MSG);
        sqLiteDatabase.execSQL(CREATE_TABLE_ADD_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
