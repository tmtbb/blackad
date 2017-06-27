package com.yundian.blackcard.android.manager;

import com.yundian.blackcard.android.model.UserInfo;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-27 10:40
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CurrentUserManager {


    public static CurrentUserManager sInstance;
    private UserInfo userInfo;

    private CurrentUserManager() {
    }

    public static CurrentUserManager getInstance() {
        if (sInstance == null) {
            synchronized (CurrentUserManager.class) {
                if (sInstance == null) {
                    sInstance = new CurrentUserManager();
                }
            }
        }
        return sInstance;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
