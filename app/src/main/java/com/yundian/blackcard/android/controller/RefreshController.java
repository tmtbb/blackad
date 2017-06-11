package com.yundian.blackcard.android.controller;

import android.content.Context;
import android.view.View;


import com.yundian.blackcard.android.R;
import com.yundian.comm.controller.BaseController;
import com.yundian.comm.listener.OnRefreshListener;
import com.yundian.comm.util.DisplayUtil;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;


/**
 * Created by yaowang on 16/3/30.
 */
public class RefreshController<TContentView extends View> extends BaseController implements PtrHandler {

    @BindView(R.id.refreshFrameLayout)
    protected PtrFrameLayout refreshFrameLayout;
    @BindView(R.id.contentView)
    protected TContentView contentView;
    private OnRefreshListener onRefreshListener;
    private boolean isAutoPullDownRefresh = true;

    /**
     * 设置下拉刷新监听
     *
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public RefreshController(Context context) {
        super(context);
    }


    /**
     * 设置是否启用下拉刷新
     *
     * @param enabled
     */
    public void setPullDownRefreshEnabled(boolean enabled) {
        refreshFrameLayout.setEnabled(enabled);
    }

    /**
     * 设置是否生命周期initData自动下拉刷新
     *
     * @param autoPullDownRefresh
     */
    public void setAutoPullDownRefresh(boolean autoPullDownRefresh) {
        isAutoPullDownRefresh = autoPullDownRefresh;
    }

    @Override
    public void initView() {
        super.initView();
        if (refreshFrameLayout != null) {
            refreshFrameLayout.setHeaderView(getHeaderView());
            refreshFrameLayout.setPtrHandler(this);
            refreshFrameLayout.setResistance(1.7f);
            refreshFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
            refreshFrameLayout.setDurationToClose(200);
            refreshFrameLayout.setDurationToCloseHeader(100);
            refreshFrameLayout.setPullToRefresh(false);
            refreshFrameLayout.setKeepHeaderWhenRefresh(true);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initData() {
        super.initData();
        if (isAutoPullDownRefresh) {
            refreshBegin();
        }
    }

    /**
     * check刷新位置
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, contentView, header);
    }

    /**
     * 开发刷新
     */
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        onRefreshBegin();
    }

    /**
     * 开发刷新
     */
    protected void onRefreshBegin() {
        if (onRefreshListener != null)
            onRefreshListener.onRefresh();
    }

    /**
     * 手动调用下拉刷新
     */
    public void refreshBegin() {
        if (refreshFrameLayout != null) {
            refreshFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshFrameLayout.autoRefresh(true, 500);
                }
            }, 200);
        }

    }

    /**
     * 刷新完成
     */
    public void refreshComplete() {
        if (refreshFrameLayout != null) {
            refreshFrameLayout.refreshComplete();
        }
    }


    /**
     * 刷新错误
     *
     * @param ex
     */
    public void refreshError(Throwable ex) {
        refreshComplete();
    }

    /**
     * 下接刷新样式view
     *
     * @return
     */
    protected View getHeaderView() {
        MaterialHeader header = new MaterialHeader(context);
        int colors[] = context.getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dip2px(15, context), 0, DisplayUtil.dip2px(10, context));
        header.setPtrFrameLayout(refreshFrameLayout);
        refreshFrameLayout.addPtrUIHandler(header);
        refreshFrameLayout.setPinContent(true);
        return header;
    }

    public PtrFrameLayout getRefreshFrameLayout() {
        return refreshFrameLayout;
    }

    /**
     * 下接刷新显示的ContentView
     *
     * @return
     */
    public TContentView getContentView() {
        return contentView;
    }


}
