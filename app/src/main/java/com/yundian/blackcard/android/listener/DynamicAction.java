package com.yundian.blackcard.android.listener;

import android.view.View;

import com.yundian.blackcard.android.model.DynamicModel;


/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-09-28 13:20
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public interface DynamicAction {
    /**
     * 处理事件
     *
     * @param view          事件view
     * @param dynamicModel 动态
     * @param obj           额外数据
     */
    void doAction(View view, int action, DynamicModel dynamicModel, Object obj);
}
