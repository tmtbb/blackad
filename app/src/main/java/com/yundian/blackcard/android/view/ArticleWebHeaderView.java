package com.yundian.blackcard.android.view;

import android.content.Context;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.comm.ui.view.BaseDataFrameLayout;
import com.yundian.comm.util.StringUtils;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-20 10:46
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ArticleWebHeaderView extends BaseDataFrameLayout<String> {

    @BindView(R.id.webView)
    protected WebView webView;

    private OnPageFinishListener onPageFinishListener;

    public ArticleWebHeaderView(Context context) {
        super(context);
    }

    public ArticleWebHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        super.initView();
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public void update(String data) {
        if (!TextUtils.isEmpty(data)) {
            webView.loadUrl(data);
        } else {
            if (onPageFinishListener != null) {
                onPageFinishListener.onCloseLoader();
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_article_web_header;
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webView.setVisibility(View.VISIBLE);
            if (onPageFinishListener != null) {
                onPageFinishListener.onCloseLoader();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (onPageFinishListener != null) {
                onPageFinishListener.onCloseLoader();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            if (onPageFinishListener != null) {
                onPageFinishListener.onCloseLoader();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (StringUtils.isNotEmpty(url)) {
                if (url.startsWith("http://") || url.startsWith("https://"))
                    ActivityUtil.nextWebView(context, url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public interface OnPageFinishListener {
        void onCloseLoader();
    }

    public void setOnPageFinishListener(OnPageFinishListener onPageFinishListener) {
        this.onPageFinishListener = onPageFinishListener;
    }
}
