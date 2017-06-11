package com.yundian.blackcard.android.manager;

import com.yundian.blackcard.android.model.UserDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-10 09:44
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class UserDetailManager {

    private static UserDetailManager userDetailManager;
    private List<OnUserDetailUpdateListener> listenerList = new ArrayList<>();

    private UserDetailManager() {

    }

    public synchronized static UserDetailManager getInstance() {
        if (userDetailManager == null) {
            userDetailManager = new UserDetailManager();
        }
        return userDetailManager;
    }


    public void registerUserDetailUpdateListener(OnUserDetailUpdateListener userDetailUpdateListener) {
        if (userDetailUpdateListener != null && !listenerList.contains(userDetailUpdateListener)) {
            listenerList.add(userDetailUpdateListener);
        }
    }

    public void unregisterUserDetailUpdateListener(OnUserDetailUpdateListener userDetailUpdateListener) {
        if (userDetailUpdateListener != null && listenerList.contains(userDetailUpdateListener)) {
            listenerList.remove(userDetailUpdateListener);
        }
    }

    public void notifyUserDetailChanged(UserDetailModel userDetailModel) {
        if( userDetailModel != null ) {
            for (OnUserDetailUpdateListener listener : listenerList) {
                listener.onUserDetailUpdate(userDetailModel);
            }
        }
    }

    public interface OnUserDetailUpdateListener {
        void onUserDetailUpdate(UserDetailModel model);
    }
}
