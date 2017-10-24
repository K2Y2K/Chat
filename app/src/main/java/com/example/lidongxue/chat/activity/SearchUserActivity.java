package com.example.lidongxue.chat.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.entity.User;
import com.example.lidongxue.chat.entity.bean.UserBean;
import com.example.lidongxue.chat.service.ConnectionService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-20.
 */

public class SearchUserActivity extends BaseActivity {
    @BindView(R.id.llSearch)
    LinearLayout mLlSearch;
    @BindView(R.id.rlNoResultTip)
    RelativeLayout mrlNoResultTip;
    @BindView(R.id.tvMsg)
    TextView mtvMsg;
    SearchView searchView;

   private ConnectionService service;
    private List<UserBean> me;
    private User user;


    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);
        bind();
        initToolBar(true,"");
       // handleIntent(getIntent())该方法是实现searchview查询结果的显示　
        // 通过Intent传播搜索内容，调用该方法显示doMySearch()的结果；
        // handleIntent(getIntent());


    }
    /**
     * 绑定服务
     */
    private void bind() {
        Log.d("---bind()--","服务器连接成功");
        //开启服务获得与服务器的连接
        intent = new Intent(this, ConnectionService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) iBinder;
            service = binder.getService();
            user = service.getUser();
            if(user!=null){
                Log.d("---searchUser-绑定服务-",user.getUser_name());

                // person_name.setText(user.getUser_name());
            }


        }
        //调用unbindService()解除服务　该方法不会被调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("---fourFragment３-解除服务-",user.getUser_name());
        }
    };
    private void searchUser(final String query) {

        new Thread(){
            @Override
            public void run() {
                //Boolean  isUser= service.addFriend(query,null,null);
                if(query!=null) {
                   /* try {
                        Log.e("----suc-searchUser--",query);
                        List<User> result_user = service.searchUsers(query);
                        if (result_user != null) {

                            Intent intent0 = new Intent(SearchUserActivity.this, UserInfoActivity.class);
                            intent0.putExtra("user_info", (Serializable) result_user);
                            startActivity(intent0);
                        } else {
                            mrlNoResultTip.setVisibility(View.VISIBLE);
                            mLlSearch.setVisibility(View.GONE);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    Boolean  isUser= service.addFriend(query,null);
                    if(isUser){
                        /*Intent intent0 = new Intent(SearchUserActivity.this, MainActivity.class);
                        //intent0.putExtra("user_info", (Serializable) result_user);
                        intent0.putExtra("user_info", query);
                        intent0.setAction("com.example.lidongxue.chat.newFriendActivity");
                        sendBroadcast(intent0);*/
                        Intent intent = new Intent();
                        intent.putExtra("acceptStatus",4);
                        intent.putExtra("response", query);
                        intent.setAction(NewFriendActivity.RECEIVER_USER);
                        sendBroadcast(intent);
                        Log.e("----suc-searchUser--",query);

                    }else{
                        mrlNoResultTip.setVisibility(View.VISIBLE);
                        mLlSearch.setVisibility(View.GONE);
                    }
                }else {
                    Log.e("----err-searchUser--","query 为空");
                }

            }
        }.start();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
       /* if(searchView == null){
            Log.e("SearchView","Fail to get Search View.");
            return true;
        }*/
        // 获取搜索服务管理器
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName cn = new ComponentName(this,SearchUserActivity.class);
        // 通过搜索管理器，从searchable activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
        SearchableInfo info = searchManager.getSearchableInfo(cn);
        if(info == null){
            Log.e("SearchableInfo","Fail to get search info.");
        }
        // 将searchable activity的搜索信息与search view关联
        //SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(info);



        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setQueryHint("搜索");
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                System.out.println("选择的是---" + query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                //如果newText不为0
                if (!newText.isEmpty()) {
                    //
                    mLlSearch.setVisibility(View.VISIBLE);
                    mtvMsg.setText(newText);
                    mLlSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            searchUser(newText);
                        }
                    });

                } else {
                    //使用用户输入内容对lv进行过滤
                   // mtvMsg.setFilterText(newText);
                }
                return false;
            }
        });
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {//activity重新置顶
        setIntent(intent);
        handleIntent(intent);
        //super.onNewIntent(intent);
    }
    // 对searchable activity的调用仍是标准的intent，我们可以从intent中获取信息，即要搜索的内容
    private void handleIntent(Intent intent) {
        if(intent == null)
            return;
        //如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }
    //doMySearch()是对搜索内容的处理
    private void doMySearch(String query) {
        // TODO 自动生成的方法存根
        Toast.makeText(this, "do search " + query,Toast.LENGTH_LONG).show();
    }


}
