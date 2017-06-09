package com.yundian.blackcard.android.controller;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yundian.blackcard.android.adapter.ListEmptyAdapter;
import com.yundian.blackcard.android.model.EmptyViewEntity;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.base.IListAdapter;


/**
 * Created by yaowang on 16/3/30.
 */
public  class RefreshAbsListController<TModel> extends RefreshListController<AbsListView,TModel> {
    protected AbsListView.OnScrollListener onScrollListener;
    public RefreshAbsListController(Context context, IListAdapter<TModel> listAdapter) {
        super(context, listAdapter);
    }


    @Override
    public void initData() {
        this.setAbsListViewAdapter(listAdapter);
        super.initData();
    }


    private void setAbsListViewAdapter(IListAdapter adapter) {
        if( getContentView() instanceof ExpandableListView) {
            ((ExpandableListView) getContentView()).setAdapter((ExpandableListAdapter)adapter);
        }
        else
            getContentView().setAdapter((ListAdapter) adapter);
    }

    @Override
    public void initListener() {
        super.initListener();
//        getContentView().setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true) {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (onScrollListener != null)
//                    onScrollListener.onScrollStateChanged(view, scrollState);
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//                        if (isLoadMoreEnable)
//                            refreshMore();
//                    }
//                }
//                super.onScrollStateChanged(view, scrollState);
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (onScrollListener != null)
//                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//                super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//            }
//        });
        getContentView().setOnScrollListener(new AbsListView.OnScrollListener() {

//            private int previousFirstVisibleItem = 0;
//            private long previousEventTime = 0;
//            private double speed = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null)
                    onScrollListener.onScrollStateChanged(view, scrollState);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        if (isLoadMoreEnable)
                            refreshMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (previousFirstVisibleItem != firstVisibleItem){
//                    long currTime = System.currentTimeMillis();
//                    long timeToScrollOneElement = currTime - previousEventTime;
//                    speed = ((double) 1 / timeToScrollOneElement) * 1000;
//                    previousFirstVisibleItem = firstVisibleItem;
//                    previousEventTime = currTime;
//                    if(speed > 16){
//                        ImageLoader.getInstance().pause();
//                    }else {
//                        ImageLoader.getInstance().resume();
//                    }
//                    LogUtil.e("speed------------->"+speed);
//                }
                if (onScrollListener != null)
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }


    /**
     * 监听AbsListView 滚动及位置判断是否自动加载更多
     * @param enableLoadMore
     */
    @Override
    public void setLoadMoreEnable(boolean enableLoadMore) {
        super.setLoadMoreEnable(enableLoadMore);
        if( enableLoadMore ) {
            if( getContentView() instanceof ListView) {
                ((ListView)getContentView()).addFooterView(moreViewController.getMoreView());
            }
        }
        else {
            if( getContentView() instanceof ListView) {
                ((ListView)getContentView()).removeFooterView(moreViewController.getMoreView());
            }
        }
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public void switchAdapter() {
        if ( hasEmptyView &&
                ( listAdapter.getList() == null || listAdapter.getList().size() == 0)  ) {
            IListAdapter<EmptyViewEntity> emptyAdapter = null;
            if (getContentView() instanceof AbsListView) {
                emptyAdapter = new ListEmptyAdapter(context);
            }
            if(emptyAdapter!=null)
                emptyAdapter.setList(getEmptyViewEntityList());
            if (emptyAdapter instanceof BaseListViewAdapter) {
                ((BaseListViewAdapter)emptyAdapter).setOnItemChildViewClickListener(onEmptyViewClickListener);
            }
            setAbsListViewAdapter(emptyAdapter);
        }
        else if( getContentView().getAdapter() != listAdapter) {
            setAbsListViewAdapter(listAdapter);
        }
    }


}
