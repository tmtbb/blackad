package com.yundian.blackcard.android.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yundian.blackcard.android.R;
import com.yundian.comm.util.StringUtils;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    public static final String EXTRA_KEY_TITLE = "extra_title";

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
    public void initView() {
        super.initView();
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(false);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void initData() {
        super.initData();

        String title = getIntent().getStringExtra(EXTRA_KEY_TITLE);
        String strUrl = getIntent().getDataString();

        if (StringUtils.isEmpty(title)) {
            title = strUrl;
        }
        setTitle(title);
        webView.loadUrl(strUrl);
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolbarLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            setLeftTitle("关闭");

        } else {
            super.onBackPressed();
        }
    }

    private void onLoadSuccess() {
        closeLoader();
    }

    private void onLoading() {
        showLoader();
    }

    private void onLoadFail() {

    }


    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

            // 如果网页中含有标题，以网页标题为准
            if (StringUtils.isNotEmpty(title))
                setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress >= 60) {
                onLoadSuccess();
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            onLoading();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        }


        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            if (request.isForMainFrame()) {// 或者： if(request.getUrl().toString() .equals(getUrl()))
                // 在这里显示自定义错误页
                onLoadFail();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            // 避免在sdk>=23时，两个方法同时执行，造成重复设置
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return;
            }

            onLoadFail();
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);

            if (request.isForMainFrame()) {
                onLoadFail();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (StringUtils.isNotEmpty(url)) {
                view.loadUrl(url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {//监听结束用onProgressChanged效果更好
            super.onPageFinished(view, url);
            onLoadSuccess();
        }
    }


}
