package com.racing.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lottery.R;


/**
 * Created by Administrator on 2017/2/16.
 */
public class JiHuaFragment extends Fragment {
    private View rootView;
    private TextView tv_header_title, tv_left;
    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.jihua, null);
        InitView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_header_title.setText("计划预测");
        WebSettings webSettings = webview.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }

        });
        webview.loadUrl("http://jihua.zse6.com");
    }

    private void InitView(View rootView) {
        tv_header_title = (TextView) rootView.findViewById(R.id.tv_header_title);
        tv_left = (TextView) rootView.findViewById(R.id.tv_left);
        tv_left.setVisibility(View.GONE);
        webview = (WebView) rootView.findViewById(R.id.webview);
    }
}
