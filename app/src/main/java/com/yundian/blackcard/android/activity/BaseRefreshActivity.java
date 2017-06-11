package com.yundian.blackcard.android.activity;


import com.yundian.blackcard.android.controller.RefreshController;
import com.yundian.comm.listener.OnRefreshListener;

/**
 * 下新刷新任何view Activity
 * Created by yaowang on 16/3/31.
 */
public abstract  class BaseRefreshActivity extends BaseControllerActivity {

    private static final String RefreshController = "RefreshController";

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        registerController(RefreshController,createRefreshController(),true);
    }

    protected RefreshController createRefreshController() {
        return new RefreshController(this);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        getRefreshController().setOnRefreshListener(onRefreshListener);
    }

    public RefreshController getRefreshController() {
       return (RefreshController)getController(RefreshController);
    }
}
