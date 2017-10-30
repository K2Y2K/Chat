package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.adapter.ContactAdapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.entity.MsgList;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-26.
 */

public class NewFriendGroupActivity extends BaseActivity {
    @BindView(R.id.contact_expand_list)
    ExpandableListView mcontact_expand_list;
    private List<UserBean> contact;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_group);
        ButterKnife.bind(this);
        initToolBar(true,"群组");
        mcontact_expand_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //i代表groupPosition,i１代表childPosition,l代表id

                String friendName = contact.get(i).getDetails().get(i1).getUserIp();
                Log.i(this.getClass().getSimpleName(), "NewFriendGroupActivity is service:" + user.getUser_id());
                Log.i(this.getClass().getSimpleName(), "NewFriendGroupActivity is service:" + user.getUser_name());
                Log.i(this.getClass().getSimpleName(), "NewFriendGroupActivity is service:" + friendName);
                MsgList msgList = new User_DB(NewFriendGroupActivity.this).checkMsgList(user.getUser_id(), friendName);
                Intent intent = new Intent(NewFriendGroupActivity.this, ChatActivity.class);
                intent.putExtra("msg_list_id", msgList.getMsg_list_id());
                intent.putExtra("to_name", msgList.getTo_name());
                startActivity(intent);
                return false;
            }
        });
        //mcontact_expand_list的适配器
        contact = BaseApp.service.getContact();
        mcontact_expand_list.setGroupIndicator(null);
        ContactAdapter adapter = new ContactAdapter(contact, NewFriendGroupActivity.this);
        mcontact_expand_list.setAdapter(adapter);
        user = BaseApp.service.getUser();
    }
}
