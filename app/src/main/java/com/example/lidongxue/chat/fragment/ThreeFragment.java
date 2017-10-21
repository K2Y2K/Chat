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

public class ThreeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*return super.onCreateView(inflater, container, savedInstanceState);*/
        System.out.println("ThreeFragment");
        return inflater.inflate(R.layout.activity_reg, container, false);
    }


}
