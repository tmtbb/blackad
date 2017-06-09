package com.yundian.comm.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yundian.comm.adapter.viewholder.BaseViewHolder;
import com.yundian.comm.listener.OnItemChildViewClickListener;


public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected OnItemChildViewClickListener onItemChildViewClickListener;

    @Override
    public abstract T getItem(int i);

    public MyBaseAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    protected BaseViewHolder<T> getViewHolder(int position) {
        return null;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        BaseViewHolder<T> viewHolder = null;
        if (view == null) {
            viewHolder = getViewHolder(position);
            viewHolder.setOnItemChildViewClickListener(onItemChildViewClickListener);
        } else {
            viewHolder = (BaseViewHolder<T>) view.getTag();
        }
        viewHolder.update(position, getItem(position));
        return viewHolder.getRootView();
    }
}
