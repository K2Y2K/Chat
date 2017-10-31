package com.example.lidongxue.chat.app.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.example.lidongxue.chat.activity.LoginActivity;
import com.example.lidongxue.chat.service.ConnectionService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lidongxue on 17-10-18.
 * Application基类
 */

public class BaseApp extends Application {
    public static List<Activity> activities = new LinkedList<>();

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler
    public static ConnectionService service;
    public static boolean isBondService=false;
    Intent intent1;
    public static ConnectionService serviceobj;


    @Override
    public void onCreate() {
        super.onCreate();

        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        Log.i(this.getClass().getSimpleName(),"onCreate()");
        bindService();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(this.getClass().getSimpleName(),"onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(this.getClass().getSimpleName(),"onTrimMemory()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(this.getClass().getSimpleName(),"onConfigurationChanged()");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(this.getClass().getSimpleName(),"onTerminate()");
    }

    public void bindService() {
        //开启服务获得与服务器的连接
         intent1 = new Intent(this, ConnectionService.class);
        bindService(intent1, connection1, BIND_AUTO_CREATE);
        startService(intent1);

    }
    public  ServiceConnection connection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            service = binder.getService();
            isBondService=true;
            Log.i(this.getClass().getSimpleName(),"service is connect");

            Boolean tag=service.getConnection().isAuthenticated();
            if(!tag){
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                Log.d(this.getClass().getSimpleName(),"未登录");
            }
            //添加好友申请监听
           // service.requestListener();
            Log.i(this.getClass().getSimpleName(),"添加好友申请监听zhixing");


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(this.getClass().getSimpleName(),"异常退出服务绑定");
        }
    };


    public void  unbindservice(){
        unbindService(connection1);
        stopService(intent1);

    }

    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!activities.contains(activity)) {
            activities.add(activity);//把当前Activity添加到集合中
        }
    }
    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (activities.contains(activity)) {
            activities.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }
    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity :  activities) {
            activity.finish();
        }
    }

    /**
     * 完全退出  销毁所有的Activity
     * 一般用于“退出程序”功能t
     */
    public void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
        //unbindservice();
    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        BaseApp.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        BaseApp.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        BaseApp.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        BaseApp.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        BaseApp.mHandler = mHandler;
    }
}
