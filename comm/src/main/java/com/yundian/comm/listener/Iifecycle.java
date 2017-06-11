package com.yundian.comm.listener;

/**
 * Created by yaowang on 16/3/31.
 */
public interface Iifecycle {
    /**
     * 初始化VIEW
     */
    void initView();

    /**
     * 初始化事件监听
     */
    void initListener();

    /**
     * 初始化数据
     */
    void initData();
    void onStart();
    void onStop();
    void onResume() ;
    void onPause();
    /**
     * 撤消
     */
    void onDestroy();
}
