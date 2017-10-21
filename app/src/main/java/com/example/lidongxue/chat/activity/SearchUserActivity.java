package com.example.lidongxue.chat.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lidongxue.chat.R;

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
   /* @BindView(R.id.search_user)
    SearchView msearch_user;
    @BindView(R.id.search_match)
    ListView msearch_match;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);
        initToolBar(true,"");
        search();
        handleIntent(getIntent());
    }
    private void search() {
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        if(searchView == null){
            Log.e("SearchView","Fail to get Search View.");
            return true;
        }
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

    private void doMySearch(String query) {
        // TODO 自动生成的方法存根
        Toast.makeText(this, "do search " + query,Toast.LENGTH_LONG).show();
    }


}
