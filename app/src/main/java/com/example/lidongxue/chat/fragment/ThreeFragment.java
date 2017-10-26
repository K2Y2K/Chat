package com.example.lidongxue.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.app.base.BaseApp;

/**
 * Created by lidongxue on 17-9-26.
 */

public class ThreeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*return super.onCreateView(inflater, container, savedInstanceState);*/
        System.out.println("ThreeFragment");
        Log.i(this.getClass().getSimpleName(), "is:"+ BaseApp.isBondService);

        Log.i(this.getClass().getSimpleName(), "is service:"+BaseApp.service);
        return inflater.inflate(R.layout.activity_reg, container, false);
    }


}
