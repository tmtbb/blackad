package com.yundian.blackcard.android.util;

import com.yundian.blackcard.android.listener.OnDynamicUpdateListener;
import com.yundian.blackcard.android.model.DynamicModel;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-09-28 13:30
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicActionObservable {


    public static DynamicActionObservable sInstance;

    public static DynamicActionObservable getInstance() {
        if (sInstance == null) {
            synchronized (DynamicActionObservable.class) {
                if (sInstance == null) {
                    sInstance = new DynamicActionObservable();
                }
            }
        }
        return sInstance;
    }


    protected List<OnDynamicUpdateListener> onUpdateListenerList = new ArrayList<OnDynamicUpdateListener>();


    public void registerUpdateListener(OnDynamicUpdateListener onUpdateListener) {
        if (!onUpdateListenerList.contains(onUpdateListener)) {
            onUpdateListenerList.add(onUpdateListener);
        }
    }

    public void unregisterUpdateListener(OnDynamicUpdateListener onUserUpdateListener) {
        int i = onUpdateListenerList.indexOf(onUserUpdateListener);
        if (i >= 0) {
            onUpdateListenerList.remove(i);
        }
    }

    public void notifyUpdate(int action, DynamicModel t) {
        for (OnDynamicUpdateListener onUserUpdateListener : onUpdateListenerList) {
            onUserUpdateListener.onDynamicUpdate(action, t);
        }
    }

}
