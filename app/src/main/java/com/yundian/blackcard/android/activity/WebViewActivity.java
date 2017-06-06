package com.yundian.blackcard.android.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yundian.blackcard.android.R;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView webView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initData() {
        super.initData();
        String title = getIntent().getStringExtra("title");
        String strUrl = getIntent().getStringExtra("url");
        setTitle(title);
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(false);
        webView.loadUrl(strUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }
}
