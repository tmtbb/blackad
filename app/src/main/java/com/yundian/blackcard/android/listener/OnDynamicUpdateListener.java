package com.yundian.blackcard.android.listener;


import com.yundian.blackcard.android.model.DynamicModel;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-09-30 12:03
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public interface OnDynamicUpdateListener {

    void onDynamicUpdate(int action, DynamicModel dynamicModel);
}
