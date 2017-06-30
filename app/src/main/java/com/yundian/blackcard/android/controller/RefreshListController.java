package com.yundian.blackcard.android.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.EmptyViewEntity;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.blackcard.android.util.EmptyViewEntityUtil;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.exception.NetworkAPIException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaowang on 16/3/30.
 */
public abstract class RefreshListController<TView extends View, TModel> extends RefreshController<TView> {
    protected int pageIndex = 0;
    protected boolean isRefresh = false;
    protected boolean isMoreData = true;
    protected boolean isLoadMoreEnable = false;
    protected IListAdapter<TModel> listAdapter;
    protected OnRefreshPageListener onRefreshPageListener;
    protected MoreViewController moreViewController;
    protected final static int MoreStatus_None = 1000;
    protected final static int MoreStatus_Load = MoreStatus_None + 1;
    protected final static int MoreStatus_Error = MoreStatus_Load + 1;
    protected final static int MoreStatus_Complete = MoreStatus_Error + 1;
    protected final static int MoreStatus_NoMoreData = MoreStatus_Complete + 1;
    private static final int TOKEN_EXPIRE = 10002;

    protected boolean hasEmptyView = false;
    protected OnItemChildViewClickListener onEmptyViewClickListener;
    protected List<EmptyViewEntity> emptyViewEntityList;

    public void setOnEmptyViewClickListener(OnItemChildViewClickListener onEmptyViewClickListener) {
        this.onEmptyViewClickListener = onEmptyViewClickListener;
    }

    public void setEmptyViewEntityList(List<EmptyViewEntity> emptyViewEntityList) {
        this.emptyViewEntityList = emptyViewEntityList;
    }


    public RefreshListController(Context context, IListAdapter<TModel> listAdapter) {
        super(context);
        this.listAdapter = listAdapter;
    }

    public IListAdapter<TModel> getListAdapter() {
        return listAdapter;
    }

    /**
     * 设置分页加载监听
     *
     * @param onRefreshPageListener
     */
    public void setOnRefreshPageListener(OnRefreshPageListener onRefreshPageListener) {
        this.onRefreshPageListener = onRefreshPageListener;
        setLoadMoreEnable(true);
    }

    /**
     * 设置是否启用下来刷新 目前自己根据设置的监听识别
     *
     * @param loadMoreEnable
     */
    protected void setLoadMoreEnable(boolean loadMoreEnable) {
        isLoadMoreEnable = loadMoreEnable;
        if (isLoadMoreEnable) {
            moreViewController = new MoreViewController(context, R.layout.president_item_loadmore);
        }
    }


    public void setListAdapter(IListAdapter<TModel> listAdapter) {
        this.listAdapter = listAdapter;
    }

    public RefreshListController(Context context) {
        super(context);
    }


    /**
     * 下接刷新
     */
    @Override
    public void onRefreshBegin() {
        if (!isRefresh) {
            pageIndex = 0;
            if (isLoadMoreEnable) {
                isMoreData = true;
                setMoreStatus(MoreStatus_None);
                onRefresh(pageIndex + 1);
            } else {
                if(onRefreshPageListener != null)
                    onRefreshPageListener.onRefresh(pageIndex+1);
                else
                    super.onRefreshBegin();
            }
        }
    }

    /***
     * 是否还有更多数据加载
     *
     * @return
     */
    protected boolean isMoreData() {
        return isMoreData;
    }

    /**
     * 加载更多
     */
    protected void refreshMore() {
        if (!isRefresh && isLoadMoreEnable && isMoreData()) {
            setPullDownRefreshEnabled(false);
            setMoreStatus(MoreStatus_Load);
            onRefresh(pageIndex + 1);
        }
    }

    /**
     * 开始加载按页加载数据
     *
     * @param pageIndex
     */
    private void onRefresh(int pageIndex) {
        if (onRefreshPageListener != null) {
            onRefreshPageListener.onRefresh(pageIndex);
            isRefresh = true;
        }
    }

    /**
     * 设置加载更多view状态
     *
     * @param status
     */
    protected void setMoreStatus(int status) {
        setPullDownRefreshEnabled(true);
        if (MoreStatus_NoMoreData == status) {
            isMoreData = false;
        }
        isRefresh = false;
        try {
            if (moreViewController != null)
                moreViewController.setStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            moreViewController.setStatus(0);
        }

    }

    /**
     * 下接刷新完成
     */
    @Override
    public void refreshComplete() {
        super.refreshComplete();
        isRefresh = false;
//        setPullDownRefreshEnabled(true);
    }

    private boolean isEmptyList(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 数据加载完成时调用
     *
     * @param list
     */
    public void refreshComplete(List<TModel> list) {
        ++pageIndex;//页码计数加1
        if (listAdapter != null) {
            if (pageIndex == 1 || !isLoadMoreEnable) {
                listAdapter.setList(list);
                if (!hasEmptyView) {
                    hasEmptyView = emptyViewEntityList != null ? true : false;
                }
                switchAdapter();
                refreshComplete();
                listAdapter.notifyDataSetChanged();
            } else if (isEmptyList(list)) {
                setMoreStatus(MoreStatus_NoMoreData);
            } else {
                setMoreStatus(MoreStatus_Complete);
                listAdapter.addList(list);
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 数据加载失败时调用
     *
     * @param ex
     */
    public void refreshError(Throwable ex) {

        if (pageIndex == 0) {
            refreshComplete();
        } else { //加载更多时出错
            setMoreStatus(MoreStatus_Error);
        }
        if (ex instanceof NetworkAPIException) {
            if (((NetworkAPIException) ex).getErrorCode() == TOKEN_EXPIRE) {
                ActivityUtil.nextLoginAndClearToken(context);
            }
        }
    }

    /**
     * 加载更多View控制器
     */
    class MoreViewController {
        @BindView(R.id.progressBar)
        protected ProgressBar progressBar;
        @BindView(R.id.textView)
        protected TextView textView;
        private View moreView;
        private int status;

        public MoreViewController(Context context, int resId) {
            moreView = LayoutInflater.from(context).inflate(resId, null, false);
            moreView.setVisibility(View.GONE);
            ButterKnife.bind(this, moreView);
            moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isRefresh && status != MoreStatus_NoMoreData) {
                        refreshMore();
                    }
                }
            });
        }

        public View getMoreView() {
            return moreView;
        }

        public void setStatus(int status) {
            moreView.setVisibility(View.VISIBLE);
            this.status = status;
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            switch (status) {
                case MoreStatus_Load:
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setText(" 加载中...");
                    break;
                case MoreStatus_Error:
                    textView.setText("  加载更多   ");
                    break;
                case MoreStatus_NoMoreData:
                    textView.setText("  已加载到底啦   ");
                    break;
                case MoreStatus_Complete:
                    textView.setVisibility(View.GONE);
                    break;
                default:
                    moreView.setVisibility(View.GONE);
            }
        }
    }

    public void setHasEmptyView(boolean hasEmptyView) {
        this.hasEmptyView = hasEmptyView;
    }


    public List<EmptyViewEntity> getEmptyViewEntityList() {
        return emptyViewEntityList != null ? emptyViewEntityList : EmptyViewEntityUtil.getInstance().getDefaultEmptyList();
    }

    public boolean isLoadMoreEnable() {
        return isLoadMoreEnable;
    }

    /**
     * 刷新适配器 检测是否空数据来不同的适配器
     */
    public abstract void switchAdapter();

}
