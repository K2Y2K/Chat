package com.example.lidongxue.chat.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.adapter.DiscoveryAdapter;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chataidl.IMyAidlInterface;
import com.example.lidongxue.chataidl.UserPic;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-31.
 */

public class DiscoveryActivity extends BaseActivity {
    @BindView(R.id.discovery_list)
    ListView mdiscovery_list;
    private List<UserPic> userpic;
    //由AIDL文件生成的Java类
    private IMyAidlInterface mIMyAidlInterface;
    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;
    DiscoveryAdapter discoveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        ButterKnife.bind(this);
        initToolBar(true,"朋友圈");

    }
    /**
     * 按钮的点击事件，点击之后调用服务端的addUserPicIn方法
     *
     *
     */
    public void addUserPic() {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mIMyAidlInterface == null) return;
        UserPic userPic1=new UserPic();
        byte[] pic=drawableToBitmap(R.drawable.cj1);
        userPic1.setFrom_name(BaseApp.service.getUser().getUser_name());
        userPic1.setPic_bigmap(pic);
        userPic1.setPic_content("nihao");
        userPic1.setPic_time(getDate());


        try {
            Log.e(getLocalClassName(), "1"+userPic1.toString());
            mIMyAidlInterface.addUserPic(userPic1);
            Log.e(getLocalClassName(), userPic1.toString());
            Log.e(getLocalClassName(), userPic1.getFrom_name());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private byte[] drawableToBitmap(int pic){
        //将图片转化为位图
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), pic);
        int size=bitmap.getWidth()*bitmap.getHeight()/4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos=new ByteArrayOutputStream(size);
        //ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //缩放法压缩
        /*Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);*/

        //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos);

        //将字节数组输出流转化为字节数组byte[]
        byte[] imagedata=baos.toByteArray();
        return  imagedata;
        //关闭字节数组输出流
        //baos.close();
        /*方法二把图片压缩存储
        * Drawable appIcon;
        * ImageView iv = (ImageView) v.findViewById(R.id.appicon);
        * iv.setImageDrawable(appIcon);
        * ByteArrayOutputStream baos = new ByteArrayOutputStream();
        * ((BitmapDrawable) iv.getDrawable()).getBitmap().compress(
        * CompressFormat.PNG, 100, baos);//压缩为PNG格式,100表示跟原图大小一样
        * */
    }
    /**
     * 发送消息时，获取当前时间
     *
     * @return 当前时间
     */
    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }


    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.example.lidongxue.chataidl.aidl");
        intent.setPackage("com.example.lidongxue.chataidl");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {

            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(getLocalClassName(), "service connected");
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            mBound = true;

            if (mIMyAidlInterface != null) {
                try {
                    //addUserPic();
                    userpic = mIMyAidlInterface.getUserPicAllList();
                    Log.e(getLocalClassName(), userpic.toString());
                    for(UserPic userpic0: userpic){
                        System.out.println("图片id："+userpic0.getPic_id()+"; ---"
                                +"内容："+userpic0.getPic_content()+"; ---"
                                +"发送者："+userpic0.getFrom_name()+"; ---"
                                +"图片："+userpic0.getPic_bigmap()+"; ---"
                                +"时间："+userpic0.getPic_time()+"\n");
                    }
                    discoveryAdapter=new DiscoveryAdapter(DiscoveryActivity.this,userpic);
                    mdiscovery_list.setAdapter(discoveryAdapter);


                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getLocalClassName(), "service disconnected");
            mBound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* getMenuInflater().inflate(R.menu.menu_main, menu);*/
        MenuInflater infla=new MenuInflater(this);
        infla.inflate(R.menu.menu_addmoment,menu);
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
                Intent intent=new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.menu_addmoment:
                addUserPic();

               /* Intent intent3=new Intent(this,AddFriActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);*/
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
