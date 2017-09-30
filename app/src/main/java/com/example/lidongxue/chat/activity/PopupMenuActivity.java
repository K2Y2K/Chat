package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.lidongxue.chat.M;
import com.example.lidongxue.chat.R;

/**
 * Created by lidongxue on 17-9-29.
 */

public class PopupMenuActivity extends AppCompatActivity {
   android.support.v7.app.ActionBar actionBar;
    private EditText edit;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);　这条语句没有起作用*/
        setContentView(R.layout.test_menu);
        //获取应用程序的标题栏bar
        actionBar=getSupportActionBar();
        edit= (EditText) findViewById(R.id.txt);
        //　将应用程序设置为可点击的按钮
        /*actionBar.setDisplayShowHomeEnabled(true);
        //两种方式将应用程序图标设置为可点击的按钮，并在图标上添加向左的箭头
        // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);*/

    }
    public void showActionBar(View source){
        getSupportActionBar().show();
    }
    public  void hideActionBar(View source){
        getSupportActionBar().hide();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=new MenuInflater(this);
        inflator.inflate(R.menu.menu_chat,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.isCheckable()){
            item.setChecked(true);
        }
       switch (item.getItemId()){
           case android.R.id.home:
               Intent intent=new Intent(this,M.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               break;
       }

        return true;
    }
}
