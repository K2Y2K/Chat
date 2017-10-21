package com.example.lidongxue.chat.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lidongxue.chat.R;

/**
 * Created by lidongxue on 17-10-21.
 */

public class SearchActivity extends BaseActivity {
    private  SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initToolBar(true,"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        if(mSearchView == null){
            Log.e("SearchView","Fail to get Search View.");
            return true;
        }
        // 获取搜索服务管理器
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       /* ComponentName cn = new ComponentName(this,SearchActivity.class);
        // 通过搜索管理器，从searchable activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
        SearchableInfo info = searchManager.getSearchableInfo(cn);*/
        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
       if(info == null){
            Log.e("SearchableInfo","Fail to get search info.");
        }
        // 将searchable activity的搜索信息与search view关联
        mSearchView.setSearchableInfo(info);
        //setIconifiedByDefault(false)该属性实现了不点击图标直接显示搜索框　false代表着搜索内容不覆盖图标；
        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
       // return true;

        return super.onCreateOptionsMenu(menu);
    }
}
