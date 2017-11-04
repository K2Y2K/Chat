package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.adapter.ContactsGroupchatAdapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.entity.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-21.
 */

public class AddFriActivity extends BaseActivity implements ContactsGroupchatAdapter.CallbackChecked{
    @BindView(R.id.search_user)
    SearchView msearch_user;
    @BindView(R.id.search_match)
    ListView msearch_match;
    private String[] mStrings = {"123", "456", "789"};
    private List<UserBean.UserBeanDetails> contactss;
    private List<String> contactsCheckedList=new ArrayList<>();

    Button addGroupchat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user1);
        ButterKnife.bind(this);
       /* Toolbar toolbar=initToolBar(true, "");
        toolbar.setTitle("添加朋友");*/
        //initToolBar(true,"发起群聊");
        Toolbar toolbar=initToolBar(true,"发起群聊");
        addGroupchat=toolbar.findViewById(R.id.add_groupchat);
        addGroupchat.setVisibility(View.VISIBLE);
        addGroupchat.setEnabled(false);

        addGroupchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        contactss = BaseApp.service.getAllFriends();
        //msearch_match.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, mStrings));

        msearch_match.setAdapter(new ContactsGroupchatAdapter(contactss,this,this));
        //设置ListView启动过滤
        msearch_match.setTextFilterEnabled(true);


        //设置sv默认是否自动缩小为图标
        msearch_user.setIconifiedByDefault(false);
        //设置显示搜索提交按钮
        msearch_user.setSubmitButtonEnabled(true);

        //设置sv默认显示的提示文本
        msearch_user.setQueryHint("搜索");

        //为该sv设置监听
        msearch_user.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //用户点击输入时触发
            @Override
            public boolean onQueryTextSubmit(String query) {

                System.out.println("选择的是---" + query);
              // 一般实际应用中可在这里做逻辑处理

                return false;
            }

            // 用户输入时触发
            @Override
            public boolean onQueryTextChange(String newText) {

                //如果newText不为0
                if (newText.isEmpty()) {
                    //
                    msearch_match.clearTextFilter();


                } else {

                    //使用用户输入内容对lv进行过滤
                    msearch_match.setFilterText(newText);

                }
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }


    @Override
    public void onCheckBoxClick(View item, View widget, int position, int which, boolean b) {
        if(b){
            contactsCheckedList.add(contactss.get(position).getUserIp().split("@")[0]);
            Log.i(getClass().getSimpleName(),"添加后的contactsCheckedList:"+contactsCheckedList.toString());

        }else {
            Log.i(getClass().getSimpleName(),"没有被选中");
            if(contactsCheckedList.contains(contactss.get(position).getUserIp().split("@")[0])){
                contactsCheckedList.remove(contactss.get(position).getUserIp().split("@")[0]);
                Log.i(getClass().getSimpleName(),"移除后的contactsCheckedList:"+contactsCheckedList.toString());
            }

        }
        if(!contactsCheckedList.isEmpty()){
            Log.i(getClass().getSimpleName(),"复选框被选择，列表不为空");
            addGroupchat.setEnabled(true);
        }else {
            Log.i(getClass().getSimpleName(),"复选框没被选择，列表为空");
            addGroupchat.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem add_chat = menu.findItem(R.id.search);*/
        return super.onCreateOptionsMenu(menu);
    }


}
