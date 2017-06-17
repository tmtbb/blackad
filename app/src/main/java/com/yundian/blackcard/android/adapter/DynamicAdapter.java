package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.view.DynamicView;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;
import com.yundian.comm.listener.OnChildViewClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-17 18:25
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicAdapter extends BaseListViewAdapter<DynamicModel> {
    public DynamicAdapter(Context context) {
        super(context);
    }


    @Override
    protected BaseViewHolder<DynamicModel> getViewHolder(int position) {
        return new DynamicViewHolder(context);
    }

    protected class DynamicViewHolder extends BaseViewHolder<DynamicModel> {

        @BindView(R.id.dynamicView)
        protected DynamicView dynamicView;

        public DynamicViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_dynamic;
        }

        @Override
        protected void initListener() {
            super.initListener();
            dynamicView.setOnChildViewClickListener(new OnChildViewClickListener() {
                @Override
                public void onChildViewClick(View childView, int action, Object obj) {
                    onItemChildViewClick(childView, action);
                }
            });
        }

        @Override
        protected void update(DynamicModel data) {
            if (data != null)
                dynamicView.update(data);
        }
    }
}
