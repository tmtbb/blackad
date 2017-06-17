package com.yundian.blackcard.android.adapter;

import android.content.Context;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.view.DynamicCommentView;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-17 22:38
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicDetailAdapter extends BaseListViewAdapter<DynamicCommentModel> {

    public DynamicDetailAdapter(Context context) {
        super(context);
    }


    @Override
    protected BaseViewHolder<DynamicCommentModel> getViewHolder(int position) {
        return new DynamicDetailViewHolder(context);
    }


    protected class DynamicDetailViewHolder extends BaseViewHolder<DynamicCommentModel> {

        @BindView(R.id.dynamicCommentView)
        protected DynamicCommentView dynamicCommentView;

        public DynamicDetailViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_dynamic_comment;
        }

        @Override
        protected void update(DynamicCommentModel data) {
            if (data != null) {
                dynamicCommentView.update(data);
            }
        }
    }
}
