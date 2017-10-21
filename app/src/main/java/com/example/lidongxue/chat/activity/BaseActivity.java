package com.example.lidongxue.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lidongxue on 17-10-17.
 * TODO 基类
 */

public class BaseActivity extends AppCompatActivity {
    private BaseApp application;
    private BaseActivity oContext;

    public static List<Activity> llactivities=new ArrayList<>();
    //启动活动进入onCreate()－>onStart() －>onResume()
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (application == null) {
            // 得到Application对象
            application = (BaseApp) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
        LogUtil.d("---Activity生命周期--","onCreate(基活动)");
        llactivities.add(this);
    }
    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(oContext);// 调用BaseApp的添加Activity方法
    }
    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_(oContext);// 调用BaseApp的销毁单个Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用BaseApp的销毁所有Activity方法
    }
    /* 把Toast定义成一个方法  可以重复使用，使用时只需要传入需要提示的内容即可*/
    public void show_Toast(String text) {
        Toast.makeText(oContext, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("---Activity生命周期--","onStart(基活动)");
    }

    //暂停后会进入恢复活动 onPause()－>onResume()
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("---Activity生命周期--","onPause(基活动)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("---Activity生命周期--","onResume(基活动)");
    }

    //停止（）后会进入重启活动onStop()－>onRestart()－>onStart()－>onResume()
    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("---Activity生命周期--","onStop(基活动)");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("---Activity生命周期--","onRestart(基活动)");
    }

    //退出程序onPause() －> onStop()－> onDestroy()
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("---Activity生命周期--","onDestroy(基活动)");
    }

    /**
     * 显示底部提示框
     *
     * @param view 点击的按钮
     * @param msg  显示的内容
     */
    public void showSnackBar(View view, String msg) {
        final Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        //snackBar.getView().setBackgroundColor(0xfff44336);
        snackBar.setAction("删除", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //消息上的按钮被点击的事件
                snackBar.dismiss();
            }
        }).show();
    }

    /**
     * 初始化ToolBar
     *
     * @param title  ToolBar左边的标题
     * @param isBack 是否显示左边返回箭头
     * @return Toolbar
     */
    public Toolbar initToolBar(boolean isBack, String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView tv = (TextView) findViewById(R.id.tool_bar_title);
        tv.setText(title);
        if (isBack) {
            //设置应用程序图标转换成可点击图标　并添加向左箭头 监听事件是关闭当前活动
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        return toolbar;
    }


    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void jumpToWebViewActivity(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        jumpToActivity(intent);
    }


    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void jumpToActivityAndClearTop(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
