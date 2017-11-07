package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.database.User_DB;
import com.example.lidongxue.chat.database.cache.UserCache;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-18.
 * 个人信息界面
 */

public class MyInfoActivity extends BaseActivity {
    @BindView(R.id.person_pic)
    ImageView person_pic;
    @BindView(R.id.person_name)
    TextView person_name;
    private ConnectionService service;
    private List<UserBean> me;
    private User user;
    private User_DB dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initToolBar(true,"个人信息");
       /* user= (User) getIntent().getSerializableExtra("person_data");
       */
        LogUtil.d("---myinfoActivity--1数据缓存-",UserCache.getName());
        //user=dbHelper.getUser(UserCache.getName());为什么为空呢？
        person_name.setText(UserCache.getName());
        //LogUtil.d("---myinfoActivity--2db查询-",(dbHelper.getUser(UserCache.getName())).getUser_name());

        person_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开手机的图库;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivity(intent);
            }
        });
    }

}
