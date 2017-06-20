package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;
import com.yundian.comm.listener.OnImageViewTouchListener;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-09-10 17:47
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicAddAdapter extends BaseListViewAdapter<String> {
    public DynamicAddAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<String> getViewHolder(int position) {
        return new DynamicAddViewHolder(context);
    }


    protected class DynamicAddViewHolder extends BaseViewHolder<String> {

        @BindView(R.id.imageIcon)
        protected ImageView imageIcon;
        @Nullable
        @BindView(R.id.deleteIcon)
        protected ImageView deleteIcon;

        public DynamicAddViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_dynamic_add;
        }

        @Override
        protected void initListener() {
            super.initListener();
            imageIcon.setOnTouchListener(OnImageViewTouchListener.getInstance());
            imageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemChildViewClick(v, ActionConstant.Action.DYNAMIC_RELEASE_ICON);
                }
            });
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemChildViewClick(v, ActionConstant.Action.DYNAMIC_RELEASE_DELETE, list.get(position));
                }
            });
        }

        @Override
        protected void update(String data) {
            if (TextUtils.isEmpty(data)) {
                Glide.with(context).load(R.mipmap.icon_dynamic_add).centerCrop().into(imageIcon);
                deleteIcon.setVisibility(View.GONE);
            } else {
                Glide.with(context).load(data).placeholder(new ColorDrawable(context.getResources().getColor(R.color.color_f8f8f8))).centerCrop().into(imageIcon);
                deleteIcon.setVisibility(View.VISIBLE);
            }
        }
    }
}
