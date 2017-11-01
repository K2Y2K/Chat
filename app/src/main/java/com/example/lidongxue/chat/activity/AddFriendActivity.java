package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.database.cache.UserCache;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-9-30.
 */

public class AddFriendActivity extends BaseActivity {
    @BindView(R.id.llSearchUser)
    LinearLayout mLlSearchUser;
    @BindView(R.id.tvAccount)
    TextView mTvAccount;
    @BindView(R.id.llSearch_phone)
    LinearLayout mllSearch_phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        initToolBar(true,getString(R.string.menu_add_one));
        mTvAccount.setText(UserCache.getName() + "");
        mLlSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToActivity(SearchUserActivity.class);
            }
        });
        mllSearch_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToActivity(SearchPhoneContactActivity.class);
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
}
