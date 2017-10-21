package com.example.lidongxue.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.lidongxue.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-18.
 * 内置浏览器界面
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.webview)
    public WebView mWebView;
    private Intent mIntent;
    private Bundle mExtras;
    private String mUrl;
    private String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        WebSettings settings = mWebView.getSettings();
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumLogicalFontSize() + 8);
        settings.setAllowFileAccess(false);
        try {
            mIntent = getIntent();
            mExtras = mIntent.getExtras();
            if (mExtras == null) {
                finish();

            }
            mUrl = mExtras.getString("url");
            if (TextUtils.isEmpty(mUrl)) {
                finish();

            }
            mTitle = mExtras.getString("title");
        } catch (Exception e) {
            e.printStackTrace();
            finish();

        }
        mWebView.loadUrl(mUrl);
        initToolBar(true,mWebView.getTitle());



    }
}
