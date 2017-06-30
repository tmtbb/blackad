package com.yundian.blackcard.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.blackcard.android.view.LoaderLayout;
import com.yundian.comm.listener.InitPageListener;
import com.yundian.comm.listener.OnErrorListener;
import com.yundian.comm.networkapi.exception.NetworkAPIException;
import com.yundian.comm.util.LogUtils;
import com.yundian.comm.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by yaowang on 2017/5/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements InitPageListener, OnErrorListener {

    private static final int TOKEN_EXPIRE = 10002;
    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @Nullable
    @BindView(R.id.toolbar_title)
    protected TextView mToolbarTitle;
    @Nullable
    @BindView(R.id.toolbar_subtitle)
    protected TextView mToolbarSubTitle;
    @Nullable
    @BindView(R.id.toolbar_rightimage)
    protected ImageView mToolbarRightImage;
    @Nullable
    @BindView(R.id.toolbar_lefttitle)
    protected TextView mToolbarLeftTitle;
    protected LoaderLayout loaderLayout;
    protected View rootView;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        context = this;
        setContentView(rootView);

        onInit();
        initStatusBar();

        initView();
        initListener();
        initData();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    public void setSubTitle(CharSequence subTitle) {
        if (mToolbarSubTitle != null) {
            mToolbarSubTitle.setText(subTitle);
            mToolbarSubTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftTitle(CharSequence subTitle) {
        if (mToolbarLeftTitle != null) {
            mToolbarLeftTitle.setText(subTitle);
            mToolbarLeftTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setRightImage(int resId) {
        if (mToolbarRightImage != null) {
            mToolbarRightImage.setImageResource(resId);
            mToolbarRightImage.setVisibility(View.VISIBLE);
        }
    }

    protected boolean isShowBackButton() {
        return false;
    }

    public void onInit() {
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        if (toolbar != null && isShowBackButton()) {
            toolbar.setNavigationIcon(R.mipmap.back_icon);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void initData() {

    }

    protected void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏颜色
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    protected void onShowError(Throwable ex) {
        if (!this.isFinishing()) {
            ToastUtils.show(this, ex.getLocalizedMessage());
        }
        LogUtils.showException(ex);
        closeLoader();
        if (ex instanceof NetworkAPIException) {
            if (((NetworkAPIException) ex).getErrorCode() == TOKEN_EXPIRE) {
                ActivityUtil.nextLoginAndClearToken(context);
            }
        }
    }

    protected void showToast(String string) {
        ToastUtils.show(this, string);
    }

    protected void showToast(CharSequence charSequence) {
        showToast(charSequence.toString());
    }

    @Override
    public void onError(Throwable ex) {
        onShowError(ex);
    }


    public void showLoader() {
        showLoader(null);
    }

    public void showLoader(String msgContent) {
        try {
            if (loaderLayout == null) {
                loaderLayout = new LoaderLayout(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                loaderLayout.setLayoutParams(params);
                if (rootView != null) {
                    ((FrameLayout) rootView.getParent()).addView(loaderLayout);
                }
            }
            loaderLayout.setVisibility(View.VISIBLE);
            if (msgContent != null) {
                loaderLayout.setMsgContent(msgContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoader(int resId) {
        showLoader(getString(resId));
    }

    public void closeLoader() {
        if (loaderLayout != null)
            loaderLayout.setVisibility(View.GONE);
    }

    protected void next(Class clazz) {
        startActivity(new Intent(context, clazz));
    }

    protected void nextForResult(Class clazz, int requestCode) {
        startActivityForResult(new Intent(context, clazz), requestCode);
    }

    public void openSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        view.requestFocus();
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    public void closeSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        view.requestFocus();
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
