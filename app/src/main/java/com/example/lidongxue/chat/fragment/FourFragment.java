package com.example.lidongxue.chat.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lidongxue.chat.MainActivity;
import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.activity.LoginActivity;
import com.example.lidongxue.chat.activity.MyInfoActivity;
import com.example.lidongxue.chat.activity.WebViewActivity;
import com.example.lidongxue.chat.app.AppConst;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.database.cache.UserCache;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;
import com.example.lidongxue.chat.service.ConnectionService;
import com.example.lidongxue.chat.utils.LogUtil;
import com.example.lidongxue.chat.widget.CustomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by lidongxue on 17-9-26.
 */

public class FourFragment extends Fragment {

    @BindView(R.id.ivHeader)
    ImageView person_pic;
    @BindView(R.id.show_github)
    TextView github;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.tvName)
    TextView person_name;
    private ConnectionService service;
    private List<UserBean> me;
    private User user;
    private View mExitView;
    private CustomDialog mExitDialog;
    Intent intent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("－－fourFragment－nCreateView－");
        bind();
        //return inflater.inflate(R.layout.fragment_me, container, false);
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }
    /**
     * 绑定服务
     */
    private void bind() {
        Log.d("---bind()--","服务器连接成功");
        //开启服务获得与服务器的连接
        intent = new Intent(getActivity(), ConnectionService.class);
        getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            service = binder.getService();
            user = service.getUser();
            if(user!=null){
                Log.d("---fourFragment1-绑定服务-",user.getUser_name());
                Log.d("---fourFragment2--",user.getUser_id()+"");
                // person_name.setText(user.getUser_name());
            }


        }
        //调用unbindService()解除服务　该方法不会被调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("---fourFragment３-解除服务-",user.getUser_name());
        }
    };



    private void initView(View rootView) {

        //mHeaderView=View.inflate(getActivity(),R.layout.header_rv_contacts, null);
        System.out.println("fourFragment_initView()");
        /*ImageView iv_pic=rootView.findViewById(R.id.ivHeader);
        TextView github=rootView.findViewById(R.id.show_github);
        TextView setting=rootView.findViewById(R.id.setting);*/
//        person_name.setText(user.getUser_name()); 启动服务器需要时间　不能及时获取user对象
        /*LogUtil.d("---fourFragment1--",user.getUser_id());*/
        person_name.setText(UserCache.getName());
        person_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(((MainActivity) getActivity()), MyInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                System.out.println("---点击个人图片---"+(MainActivity) getActivity());
               // intent.putExtra("person_data",user);
                startActivity(intent);

            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", AppConst.WeChatUrl.MY_GITHUB);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExitView == null) {
                    mExitView = View.inflate(getActivity(), R.layout.dialog_exit, null);
                    mExitDialog = new CustomDialog(getActivity(), mExitView, R.style.MyDialog);
                    mExitView.findViewById(R.id.tvExitAccount).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*RongIMClient.getInstance().logout();
                         UserCache.clear();*/

                            Boolean logout_user=service.logout1();
                            LogUtil.d("---退出账号--",logout_user);
                            if(logout_user){
                                UserCache.clear();
                            mExitDialog.dismiss();
                                //getActivity().unbindService(connection);
                            BaseApp.exit();
                             getActivity().finish();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            }else {
                                Toast.makeText(getActivity(),"退出失败",Toast.LENGTH_SHORT).show();
                            }

                        }
                    } );
                    mExitView.findViewById(R.id.tvExitApp).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //RongIMClient.getInstance().disconnect();
                            //Boolean logout_app=service.disConnect();
                            LogUtil.d("---退出应用--","");
                            mExitDialog.dismiss();
                            getActivity().unbindService(connection);
                            //getActivity().stopService(intent);
                           // getActivity().finish();//只关闭了当前的活动　并没有关闭所有的　（当前活动启动了两次）
                            BaseApp.exit();
                        }
                    });
                }
                mExitDialog.show();

            }
        });
    }





}
