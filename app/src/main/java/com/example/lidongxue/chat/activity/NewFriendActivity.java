package com.example.lidongxue.chat.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.adapter.NewFriendAdapter1;
import com.example.lidongxue.chat.broadcast.MyReceiverAddFri;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.entity.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lidongxue on 17-10-23.
 */

public class NewFriendActivity extends BaseActivity implements AdapterView.OnItemClickListener
{
    private MyReceiverAddFri receiver1;
    //private static  MyReceiver receiver;
    public static String response;
    public static int acceptStatus;
    public static Handler handler;
    public static final String RECEIVER_USER="com.example.lidongxue.chat.newFriendActivity";
    private  ArrayList<HashMap<String, Object>> listItems;   //存放文字、图片信息
   // private SimpleAdapter listItemAdapter;                  //适配器
    private NewFriendAdapter1 adapter;
   /* @BindView(R.id.search_new_user)
    SearchView msearch_new_user;*/
    @BindView(R.id.search_newfriend_match)
    ListView msearch_newfriend_match;
    // Handler handler;
    List<Contact> contacts_list;
    Contact contact_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend_note);
        ButterKnife.bind(this);
        initToolBar(true,"新朋友");

        getContactList();


        /*//动态注册广播
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_USER);
        registerReceiver(receiver, intentFilter);*/

        /*//使用静态广播
        receiver1 = new MyReceiverAddFri();*/


        //listItemAdapter=new NewFriendAdapter(this);
       // msearch_newfriend_match.setAdapter(listItemBaseAdapter1);


       // this.setListAdapter(listItemAdapter);
        Log.i("--NewFriendActivity--","onCreate");
        listItems = new ArrayList<HashMap<String, Object>>();

      /* 用handler不能解决问题
       handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                HashMap<String, Object> map = new HashMap<String, Object>();
                if(msg.what==0x11){
                    Log.i("--NewFriendActivity--","onReceive()＝１");
                    //HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemTitle", response);     //文字
                    // map.put("Item", R.drawable.music);//图片
                    map.put("ItemStatus","接收");
                    listItems.add(map);
                    listItemBaseAdapter1=new NewFriendAdapter(NewFriendActivity.this,
                            listItems, NewFriendActivity.this);
                    msearch_newfriend_match.setAdapter(listItemBaseAdapter1);
                    msearch_newfriend_match.setOnItemClickListener(NewFriendActivity.this);
                    *//*
                    //生成适配器的Item和动态数组对应的元素
                    listItemAdapter = new SimpleAdapter(NewFriendActivity.this,listItems,//数据源
                            R.layout.list_item_1,//ListItem的XML布局实现
                            //动态数组与ImageItem对应的子项
                            new String[] {"ItemTitle", "ItemStatus"},
                            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                            new int[] {R.id.newfriend_title,R.id.newfriend_receiver_btn}
                    );
                    NewFriendActivity.this.setListAdapter(listItemAdapter);
                   *//*
                }else  if(msg.what==0x12){
                    map.put("ItemTitle", response);     //文字
                    map.put("ItemStatus","已添加");
                    listItems.add(map);
                    listItemBaseAdapter1=new NewFriendAdapter(NewFriendActivity.this,
                            listItems, (NewFriendAdapter.Callback) NewFriendActivity.this);
                    msearch_newfriend_match.setAdapter(listItemBaseAdapter1);
                    msearch_newfriend_match.setOnItemClickListener(NewFriendActivity.this);


                }else  if(msg.what==0x13){
                    map.put("ItemTitle", response);
                    map.put("ItemStatus","被拒绝");
                    listItems.add(map);
                    listItemBaseAdapter1=new NewFriendAdapter(NewFriendActivity.this,
                            listItems, (NewFriendAdapter.Callback) NewFriendActivity.this);
                    msearch_newfriend_match.setAdapter(listItemBaseAdapter1);
                    msearch_newfriend_match.setOnItemClickListener(NewFriendActivity.this);

                }else  if(msg.what==0x14){
                    Log.i("--NewFriendActivity--","onReceive()＝４");
                    map.put("ItemTitle", response);
                    map.put("ItemStatus","等待验证");
                    listItems.add(map);

                    Log.i("--NewFriendActivity--",listItems.toString());
                    listItemBaseAdapter1=new NewFriendAdapter(NewFriendActivity.this,
                            listItems, (NewFriendAdapter.Callback) NewFriendActivity.this);
                    msearch_newfriend_match.setAdapter(listItemBaseAdapter1);
                    msearch_newfriend_match.setOnItemClickListener(NewFriendActivity.this);

                }else{
                    Log.i("--NewFriendActivity--","onReceive()为空");

                }
            }
        };
      //使用静态广播
        receiver = new MyReceiver();
*/
    }

    private void getContactList() {
        User_DB dbHelper = new User_DB(this);
        contacts_list = dbHelper.getContactAll();
        adapter = new NewFriendAdapter1(this,contacts_list);
        msearch_newfriend_match.setAdapter(adapter);
        //msearch_newfriend_match.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      /*  //解除广播 动态注册广播需要取消广播注册
        unregisterReceiver(receiver);*/
        Log.i("--NewFriendActivity--","onDestroy()");
    }

    /* @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(this, "listview的item被点击了！，点击的位置是-->" + position,
          Toast.LENGTH_SHORT).show();
    }

    /*//该方法是重写NewFriendAdapter
    @Override
    public void onClick(View item, View widget, int position, int which){
        switch (which){
            case R.id.newfriend_receiver_btn:
                Log.i("--NewFriendActivity--","点击接收按钮");
                Toast.makeText(
                        NewFriendActivity.this,
                        "listview的内部的按钮被点击了！，位置是-->" + (Integer) item.getTag() + ",内容是-->"
                                + listItems.get((Integer) item.getTag()),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.newfriend_receiver_nobtn:
                Log.i("--NewFriendActivity--","点击拒绝按钮");
                break;
            default:
                break;
        }

    }*/
   /* @Override
    public void onClick(View v) {
        Toast.makeText(
               NewFriendActivity.this,
                 "listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
                     + listItems.get((Integer) v.getTag()),
                Toast.LENGTH_SHORT).show();
    }*/
   /*
   * 内部广播　用handler传值时　　该activity　必须运行，否则提示handler对象为空　报错，so广播接收值处理添加好友的状态不可取
   * *//*
   //内部静态广播　要在class前加上staitc　否则抛出异常
    public static class MyReceiver extends BroadcastReceiver {

        public MyReceiver() {

            Log.i("--NewFriendActivity--","MyReceiver()");
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("--NewFriendActivity--","onReceive()");
            //接收字符串及发送方的id
            Bundle bundle = intent.getExtras();
            response = bundle.getString("response");
            System.out.println("广播收到response:"+response);
            acceptStatus=bundle.getInt("acceptStatus");
            System.out.println("广播收到acceptStatus:"+acceptStatus);
            Message msg=new Message();
            switch (acceptStatus){
                case 1:
                    msg.what=0x11;
                    handler.sendMessage(msg);
                    break;
                case 2:
                    msg.what=0x12;
                    handler.sendMessage(msg);
                     break;
                case 3:
                    msg.what=0x13;
                    handler.sendMessage(msg);
                    break;
                case 4:
                    msg.what=0x14;
                    handler.sendMessage(msg);
                    break;
                default:
                    break;

            }
        }
    }*/
}

