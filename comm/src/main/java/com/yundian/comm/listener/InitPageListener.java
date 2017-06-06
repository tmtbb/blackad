package com.yundian.comm.listener;

/**
 * Created by yaowang on 2017/5/11.
 */

public interface InitPageListener {
    int getLayoutId();
    void initView();
    void initListener();
    void initData();
}
