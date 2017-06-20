package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-19 17:58
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ArticleListAdapter extends BaseListViewAdapter<ArticleModel> {
    public ArticleListAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<ArticleModel> getViewHolder(int position) {
        return new ArticleViewHolder(context);
    }

    protected class ArticleViewHolder extends BaseViewHolder<ArticleModel> {


        @BindView(R.id.titleText)
        protected TextView titleText;
        @BindView(R.id.createTimeText)
        protected TextView createTimeText;
        @BindView(R.id.contentText)
        protected TextView contentText;
        @BindView(R.id.imageView)
        protected ImageView imageView;

        public ArticleViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_article;
        }

        @Override
        protected void update(ArticleModel data) {
            if (data != null) {
                titleText.setText(data.getTitle());
                createTimeText.setText(TimeUtil.formatDate(data.getCreateTime()));
                contentText.setText(data.getSummary());
                if (TextUtils.isEmpty(data.getCoverUrl())) {
                    imageView.setVisibility(View.GONE);
                } else {
                    Glide.with(context).load(data.getCoverUrl()).placeholder(new ColorDrawable(context.getResources().getColor(R.color.color_d7d7d7))).centerCrop().into(imageView);
                    imageView.setVisibility(View.VISIBLE);
                }

            }
        }

        @OnClick(R.id.rootLayout)
        protected void onClick(View view) {
            onItemChildViewClick(view, ActionConstant.Action.ARTICLE_DETAIL);
        }
    }
}
