package com.yundian.blackcard.android.fragment.base;


import com.yundian.blackcard.android.controller.RefreshController;
import com.yundian.comm.listener.OnRefreshListener;

/**
 * 下拉与加载更多列表Fragment
 * Created by yaowang on 16/3/31.
 */
public abstract  class BaseRefreshFragment extends BaseControllerFragment {

    private static final String RefreshController = "RefreshController";

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        registerController(RefreshController,createRefreshController(),true);
    }

    protected RefreshController createRefreshController() {
        return new RefreshController(context);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        getRefreshController().setOnRefreshListener(onRefreshListener);
    }

    public RefreshController getRefreshController() {
       return (RefreshController)getController(RefreshController);
    }
}
