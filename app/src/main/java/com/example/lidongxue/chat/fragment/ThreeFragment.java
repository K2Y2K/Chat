package com.example.lidongxue.chat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lidongxue.chat.R;
import com.example.lidongxue.chat.activity.DiscoveryActivity;
import com.example.lidongxue.chat.activity.TimerActivity;
import com.example.lidongxue.chat.app.base.BaseApp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-9-26.
 */

public class ThreeFragment extends Fragment {

    @BindView(R.id.lldiscovery_info)
    LinearLayout mlldiscovery_info;
    @BindView(R.id.ad_time)
    Button mad_time;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*return super.onCreateView(inflater, container, savedInstanceState);*/
        System.out.println("ThreeFragment");
        Log.i(this.getClass().getSimpleName(), "is:"+ BaseApp.isBondService);
        Log.i(this.getClass().getSimpleName(), "is service:"+BaseApp.service);
        //return inflater.inflate(R.layout.activity_reg, container, false);
        rootView = inflater.inflate(R.layout.fragment_discovery, container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mlldiscovery_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DiscoveryActivity.class);
                startActivity(intent);
            }
        });
        mad_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimerActivity.class);
                startActivity(intent);
            }
        });
    }


}
