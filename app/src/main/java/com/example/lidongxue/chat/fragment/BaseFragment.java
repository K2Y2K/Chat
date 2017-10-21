package com.example.lidongxue.chat.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-18.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        View rootView = inflater.inflate(provideContentViewId(), container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }



    public void initView(View rootView) {

    }
    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }
    public void init() {

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }
    public void initData() {

    }

    public void initListener() {

    }

}
