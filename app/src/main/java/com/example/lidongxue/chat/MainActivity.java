package com.example.lidongxue.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lidongxue.chat.adapter.Myadapter;
import com.example.lidongxue.chat.fragment.FourFragment;
import com.example.lidongxue.chat.fragment.OneFragment;
import com.example.lidongxue.chat.fragment.ThreeFragment;
import com.example.lidongxue.chat.fragment.TwoFragment;
import com.example.lidongxue.chat.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener
        ,ViewPager.OnPageChangeListener {
             /**/
    MyViewPager vp;
    RadioGroup rg;
    List<Fragment> fragmentList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);


        initView();


    }
    public  void initView(){
        //把Fragment添加到集合里面去
        fragmentList = new ArrayList<>();
        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        fragmentList.add(new ThreeFragment());
        fragmentList.add(new FourFragment());
        //隐藏ActionBar
       // getSupportActionBar().hide();
        getSupportActionBar();
        //解析控件
        vp = (MyViewPager) findViewById(R.id.main_activity_content);
        rg = (RadioGroup) findViewById(R.id.main_activity_radio_group);
        //设置点击事件(多了个change，导包用RadioGroup.OnCheckedChangeListener)
        ////but.setOnClickListener(this);不明白
        rg.setOnCheckedChangeListener(MainActivity.this);
      vp.addOnPageChangeListener((ViewPager.OnPageChangeListener) MainActivity.this);
        //通过适配器把Fragment添加到主界面上
        //vp.setOffscreenPageLimit(3);
        vp.setAdapter(new Myadapter(getSupportFragmentManager(), fragmentList));
        //vp.setCurrentItem(0);
    }

    /*RadioButton   切换Fragment*/
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        //声明一个下标,并赋值
        RadioButton rgc;
        int index = 0;
        switch (checkedId) {
            case R.id.main_activity_message_rbt:
                index = 0;
                break;
            case R.id.main_activity_contacts_rbt:
                index = 1;
                break;
            case R.id.main_activity_friendcircle_rbt:
                index = 2;
                break;
            case R.id.main_activity_mine_rbt:
                index = 3;
                break;

        }
        //设置vp选项，与View_RadioButton关联起来 且关闭切换动画效果　但不知实现了不
        vp.setCurrentItem(index,false);

    }
    @Override
    public void onPageSelected(int position) {

        RadioButton radioButton = (RadioButton) rg.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infla=new MenuInflater(this);
        infla.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(this,M.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.menu_search:
                Intent intent1=new Intent(this,M.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.menu_add_one:

                break;
            case R.id.menu_add_two:

                break;

        }

        return true;
    }

}
