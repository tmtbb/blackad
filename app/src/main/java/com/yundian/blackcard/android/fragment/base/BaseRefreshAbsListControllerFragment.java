package com.yundian.blackcard.android.fragment.base;

import android.widget.AbsListView;

import com.yundian.blackcard.android.controller.RefreshAbsListController;
import com.yundian.blackcard.android.controller.RefreshListController;


/**
 * 下拉与加载更多AbsListView Fragment
 * Created by yaowang on 16/3/31.
 */
public abstract class BaseRefreshAbsListControllerFragment<TModel> extends BaseRefreshListFragment<AbsListView, TModel> {

    @Override
    protected RefreshListController<AbsListView, TModel> createRefreshController() {
        return new RefreshAbsListController<TModel>(context, createAdapter());
    }

    @Override
    public RefreshAbsListController<TModel> getRefreshController() {
        return (RefreshAbsListController<TModel>) super.getRefreshController();
    }
}
