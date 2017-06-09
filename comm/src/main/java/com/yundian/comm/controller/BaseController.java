package com.yundian.comm.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.yundian.comm.listener.Iifecycle;


/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-07-14 08:37
 * @describe : activity 或 fragment 额外控制器
 * @for your attention : none
 * @revise : none
 */
public abstract class BaseController implements Iifecycle {
    protected Context context;

    public BaseController(Context context) {
        this.context = context;
    }

    /**
     * 初始化VIEW
     */
    public void initView() {

    }

    /**
     * 初始化事件监听
     */
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * activity 或 fragment onStart
     */
    public void onStart() {

    }

    /**
     * activity 或 fragment onStop
     */
    public void onStop() {

    }

    /**
     * activity 或 fragment onResume
     */
    public void onResume() {

    }

    /**
     * activity 或 fragment onPause
     */
    public void onPause() {
    }

    /**
     * activity 或 fragment onDestroy
     */
    public void onDestroy() {

    }

    protected void showToast(@NonNull CharSequence content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
    }
}
