package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-11-1.
 */

public class TimerActivity extends BaseActivity implements  Animation.AnimationListener {
    @BindView(R.id.adtextview1)
    TextView textView;
    @BindView(R.id.adimageview1)
    ImageView imageView;
    Thread thread;
    Boolean flag=true;
    Chronometer timer;
    long t=0;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //TextView textView= (TextView) findViewById(R.id.adtextview);//初始化控件
            /*switch (msg.what){//按时间自动逐秒递减
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
            }*/
            textView.setText("广告倒计时："+msg.what+"秒");

            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timer= (Chronometer) findViewById(R.id.ct_timer);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.setFormat("计时时间：(%s)");
        Log.d(getClass().getSimpleName(),"onCreate() SystemClock.elapsedRealtime():"+SystemClock.elapsedRealtime());
        Log.d(getClass().getSimpleName(),"onCreate() timer.getBase():"+timer.getBase());
        timer.start();
        ButterKnife.bind(this);
        initToolBar(true,"timer");
        setAnimation();
        /*thread=new Thread(this);
        thread.start();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getClass().getSimpleName(),"onPause()");
        flag=false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getClass().getSimpleName(),"onStop()");
        //thread.interrupt();
        //获取计时器记录的时间
        t=SystemClock.elapsedRealtime()-timer.getBase();
        Log.d(getClass().getSimpleName(),"onStop() timer.getBase():"+timer.getBase());
        Log.d(getClass().getSimpleName(),"onStop() SystemClock.elapsedRealtime():"+SystemClock.elapsedRealtime());
        Log.d(getClass().getSimpleName(),"onStop() t:"+t);
        timer.stop();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getClass().getSimpleName(),"onRestart()");
        flag=true;
        timer.setBase(SystemClock.elapsedRealtime()-t);
        Log.d(getClass().getSimpleName(),"onRestart() SystemClock.elapsedRealtime():"+SystemClock.elapsedRealtime());
        Log.d(getClass().getSimpleName(),"onRestart() timer.getBase():"+timer.getBase());
        timer.start();
    }
    //渐变动画
    private void setAnimation() {
        //ImageView imageView= (ImageView) findViewById(R.id.adimageview);
        AlphaAnimation a=new AlphaAnimation(0.8f,1);
        a.setDuration(50000);//时间
        a.setAnimationListener(this);//配置监听器
        imageView.startAnimation(a);//启动动画
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //当动画结束的时候跳转
        startActivity(new Intent(TimerActivity.this,MainActivity.class));
        Log.d(getClass().getSimpleName(),"跳转后　后边的finish()方法继续执行");
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    //线程处理倒计时问题
   /* @Override
    public void run() {
        for (int i=49;i>=0;i--){
            if(!flag){
                Log.d(getClass().getSimpleName(),"线程中断 i:"+i);

            }
            Message message=new Message();
            try {
                Thread.sleep(950);//线程休眠时间
                Log.d(getClass().getSimpleName(),"线程休眠时间");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            message.what=i;
            handler.sendMessage(message);//发送消息给处理者
        }
        //尽管线程被中断,但并没有结束运行。这行代码还是会被执行
        System.out.println("this line is also executed. thread does not stopped");


    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(),"onDestroy()");
    }
}
