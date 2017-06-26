package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.TribeListModel;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;
import com.yundian.comm.util.DisplayUtil;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 10:39
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeAdapter extends BaseListViewAdapter<TribeListModel> {

    private boolean hasTribe = false;
    private static final int TYPE_USER = 0;
    private static final int TYPE_TAG = TYPE_USER + 1;
    private static final int TYPE_RECOMM = TYPE_TAG + 1;
    private static final int MAX = TYPE_RECOMM + 1;

    public void setHasTribe(boolean hasTribe) {
        this.hasTribe = hasTribe;
    }

    public TribeAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<TribeListModel> getViewHolder(int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_USER:
                TribeViewHolder viewHolder1 = new TribeViewHolder(context);
                viewHolder1.setShapeBackground(R.drawable.text_tab_bg);
                return viewHolder1;
            case TYPE_RECOMM:
                TribeViewHolder viewHolder2 = new TribeViewHolder(context);
                viewHolder2.setShapeBackground(R.drawable.text_tab_bg2);
                return viewHolder2;
            case TYPE_TAG:
                return new TagViewHolder(context);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return MAX;
    }

    protected class TribeViewHolder extends BaseViewHolder<TribeListModel> {

        @BindView(R.id.titleText)
        protected TextView titleText;
        @BindView(R.id.dotView)
        protected View dotView;
        @BindView(R.id.contentText)
        protected TextView contentText;
        @BindView(R.id.shapeLayout)
        protected LinearLayout shapeLayout;

        public TribeViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_tribe;
        }

        @Override
        protected void initListener() {
            super.initListener();
            shapeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemChildViewClick(v, 99);
                }
            });
        }

        @Override
        protected void update(TribeListModel data) {
            if (data != null) {
                titleText.setText(data.getName());
                dotView.setVisibility(data.getVerifyNum() == 0 ? View.GONE : View.VISIBLE);
                contentText.setText(data.getDescription());
            }
        }

        public void setShapeBackground(int resId) {
            shapeLayout.setBackgroundResource(resId);
            shapeLayout.setPadding(DisplayUtil.dip2px(12, context), DisplayUtil.dip2px(12, context), DisplayUtil.dip2px(12, context), DisplayUtil.dip2px(12, context));
        }
    }

    protected class TagViewHolder extends BaseViewHolder<TribeListModel> {

        @BindView(R.id.tagLayout1)
        protected ViewGroup tagLayout1;
        @BindView(R.id.tagLayout2)
        protected ViewGroup tagLayout2;

        public TagViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_tribe_tag;
        }

        @Override
        protected void update(TribeListModel data) {
            if (hasTribe) {
                tagLayout1.setVisibility(View.VISIBLE);
                tagLayout2.setVisibility(View.GONE);
            } else {
                tagLayout1.setVisibility(View.GONE);
                tagLayout2.setVisibility(View.VISIBLE);
            }
        }
    }
}
