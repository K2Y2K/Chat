package com.example.lidongxue.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidongxue.chat.R;

/**
 * Created by lidongxue on 17-9-26.
 */

public class TwoFragment extends Fragment {
    private View mHeaderView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("OneFragment_onCreateView");
        return inflater.inflate(R.layout.header_rv_contacts, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("OneFragment_onCreate");
        initView();
    }

    private void initView() {
        //mHeaderView=View.inflate(getActivity(),R.layout.header_rv_contacts, null);
        System.out.println("OneFragment_initView()");
    }
}
