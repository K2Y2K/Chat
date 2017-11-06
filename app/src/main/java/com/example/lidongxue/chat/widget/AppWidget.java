package com.example.lidongxue.chat.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;


/**
 * Created by lidongxue on 17-10-17.
 */

public class AppWidget extends AppWidgetProvider {
    private final  String ACTION_IMAGEVIEW="action_imageview";
    private RemoteViews remoteViews;

    //接收广播事件
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(this.getClass().getName(), "onReceive");
        if (intent == null)
            return;

        String action = intent.getAction();
        Log.d("----onReceive()---", action);
        if (action.equals(ACTION_IMAGEVIEW)) {
            // 只能通过远程对象来设置appWidget中的状态
            //系统时间调用System.currentTimeMillis()
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
           // remoteViews.setTextViewText(R.id.tv_show, ""+System.currentTimeMillis());
            remoteViews.setImageViewResource(R.id.iv_show,R.drawable.ic_preview);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            ComponentName componentName = new ComponentName(context, AppWidget.class);

            // 更新appWidget
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }
    }
    //当用户　向桌面添加ＡＰＰwidget时被调用

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(this.getClass().getName(), "onUpdate");



        // 小部件在Launcher桌面的布局
         remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);

        remoteViews.setImageViewResource(R.id.iv_show,R.drawable.ic_preview);
        ComponentName componentName = new ComponentName(context, AppWidget.class);
        // 事件
        //桌面创建小部件时　发送监听图片被点击时的广播
        // Intent intent = new Intent(ACTION_IMAGEVIEW);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_show, pendingIntent);

        // 更新AppWidget
        appWidgetManager.updateAppWidget(componentName, remoteViews);
    }

    //删除appwidget
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(this.getClass().getName(), "onDeleted");
    }

    //最后一个widget被删除时调用
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(this.getClass().getName(), "onDisabled");
    }

    //  // 第一个widget被创建时调用
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(this.getClass().getName(), "onEnabled");
    }
}
