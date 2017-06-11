package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.EmptyViewEntity;
import com.yundian.blackcard.android.view.DefaultEmptyView;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;
import com.yundian.comm.listener.OnChildViewClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-04-14 10:01
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ListEmptyAdapter extends BaseListViewAdapter<EmptyViewEntity> {
    public ListEmptyAdapter(Context context) {
        super(context);
    }

    public ListEmptyAdapter(Context context, List<EmptyViewEntity> list) {
        super(context, list);
    }

    @Override
    protected BaseViewHolder<EmptyViewEntity> getViewHolder(int position) {
        return new EmptyViewHolder(context);
    }

    protected class EmptyViewHolder extends BaseViewHolder<EmptyViewEntity> {
        @BindView(R.id.emptyView)
        protected DefaultEmptyView emptyView;

        public EmptyViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.president_item_empty;
        }

        @Override
        protected void initListener() {
            super.initListener();
            emptyView.setOnChildViewClickListener(new OnChildViewClickListener() {
                @Override
                public void onChildViewClick(View childView, int action, Object obj) {
                    onItemChildViewClick(childView, action, obj);
                }
            });
        }

        @Override
        protected void update(EmptyViewEntity data) {
            emptyView.update(data);
        }
    }
}
