package com.example.lidongxue.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lidongxue.chat.fragment.FourFragment;
import com.example.lidongxue.chat.fragment.OneFragment;
import com.example.lidongxue.chat.fragment.ThreeFragment;
import com.example.lidongxue.chat.fragment.TwoFragment;
import com.example.lidongxue.chat.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class main extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, View.OnClickListener {
    MyViewPager vp;
    RadioGroup rg;
    List<Fragment> fragmentList;
    //MoreWindow mMoreWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main1);

        //把Fragment添加到集合里面去
        fragmentList = new ArrayList<>();
        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        fragmentList.add(new ThreeFragment());
        fragmentList.add(new FourFragment());
        //隐藏ActionBar
        getSupportActionBar().hide();
        //解析控件
        vp = (MyViewPager) findViewById(R.id.main_activity_content);
        rg = (RadioGroup) findViewById(R.id.main_activity_radio_group);
        //设置点击事件(多了个change，导包用RadioGroup.OnCheckedChangeListener)

        ////but.setOnClickListener(this);不明白


        rg.setOnCheckedChangeListener(this);
        vp.addOnPageChangeListener(this);
        //通过适配器把Fragment添加到主界面上
        //vp.setOffscreenPageLimit(3);
        vp.setAdapter(new Myadapter(getSupportFragmentManager(), fragmentList));
        //vp.setCurrentItem(0);


    }



    /*RadioButton   切换Fragment*/
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        //声明一个下标,并赋值
        int index = -1;
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        RadioButton radioButton = (RadioButton) rg.getChildAt(position);
        radioButton.setChecked(true);

    }
    /*@Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                rg.check(R.id.main_activity_message_rbt);
                break;
            case 1:
                rg.check(R.id.main_activity_contacts_rbt);
                break;
            case 2:
                rg.check(R.id.main_activity_friendcircle_rbt);
                break;
            case 3:
                rg.check(R.id.main_activity_mine_rbt);
                break;
        }
    }*/


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }


    class Myadapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;

        public Myadapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return (fragmentList == null) ? 0 : fragmentList.size();

        }

    }

}
