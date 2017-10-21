package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-21.
 */

public class AddFriActivity extends BaseActivity {
    @BindView(R.id.search_user)
    SearchView msearch_user;
    @BindView(R.id.search_match)
    ListView msearch_match;
    private String[] mStrings = {"123", "456", "789"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user1);
        ButterKnife.bind(this);
       /* Toolbar toolbar=initToolBar(true, "");
        toolbar.setTitle("添加朋友");*/
        initToolBar(true,"添加朋友");
        msearch_match.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, mStrings));

        //设置ListView启动过滤
        msearch_match.setTextFilterEnabled(true);


        //设置sv默认是否自动缩小为图标
        msearch_user.setIconifiedByDefault(false);
        //设置显示收索按钮
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
}
