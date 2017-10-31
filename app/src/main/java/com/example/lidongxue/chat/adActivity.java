package com.example.lidongxue.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lidongxue on 17-10-31.
 */

public class adActivity extends Activity implements Animation.AnimationListener,Runnable {
    @BindView(R.id.adtextview)
    TextView textView;
    @BindView(R.id.adimageview)
    ImageView imageView;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //TextView textView= (TextView) findViewById(R.id.adtextview);//初始化控件
            switch (msg.what){//按时间自动逐秒递减
                case 1:
                    textView.setText("广告倒计时：4秒");
                    break;
                case 2:
                    textView.setText("广告倒计时：3秒");
                    break;
                case 3:
                    textView.setText("广告倒计时：2秒");
                    break;
                case 4:
                    textView.setText("广告倒计时：1秒");
                    break;
                case 5:
                    textView.setText("广告倒计时：0秒");
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);
        setAnimation();
        Thread thread=new Thread(this);
        thread.start();
    }

    //渐变动画
    private void setAnimation() {
        //ImageView imageView= (ImageView) findViewById(R.id.adimageview);
        AlphaAnimation a=new AlphaAnimation(0.8f,1);
        a.setDuration(5000);//时间
        a.setAnimationListener(this);//配置监听器
        imageView.startAnimation(a);//启动动画
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //当动画结束的时候跳转
        startActivity(new Intent(adActivity.this,MainActivity.class));
        Log.d(getClass().getSimpleName(),"跳转后　后边的finish()方法继续执行");
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    //线程处理倒计时问题
    @Override
    public void run() {
        for (int i=1;i<=5;i++){
            Message message=new Message();
            try {
                Thread.sleep(950);//线程休眠时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            message.what=i;
            handler.sendMessage(message);//发送消息给处理者
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(),"onDestroy()");
    }
}
