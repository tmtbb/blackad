package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.TribeMemberModel;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-22 09:54
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeApplyAdapter extends MicroUpdateListViewAdapter<TribeMemberModel> {

    private int identity;

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public TribeApplyAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<TribeMemberModel> getViewHolder(int position) {
        return new TribeApplyViewHolder(context);
    }

    @Override
    protected boolean compareEqual(TribeMemberModel t1, TribeMemberModel t2) {
        if (t1 != null && t2 != null) {
            return t1.getId().equals(t2.getId());
        }
        return super.compareEqual(t1, t2);
    }

    protected class TribeApplyViewHolder extends BaseViewHolder<TribeMemberModel> {

        @BindView(R.id.userIcon)
        protected ImageView userIcon;
        @BindView(R.id.iconId)
        protected ImageView iconId;
        @BindView(R.id.agreeText)
        protected TextView agreeText;
        @BindView(R.id.refuseText)
        protected TextView refuseText;
        @BindView(R.id.otherText)
        protected TextView otherText;
        @BindView(R.id.nickNameText)
        protected TextView nickNameText;
        @BindView(R.id.createTimeText)
        protected TextView createTimeText;

        public TribeApplyViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_tribe_apply;
        }

        @Override
        protected void update(TribeMemberModel data) {
            if (data != null) {
                Glide.with(context).load(data.getHeadUrl()).placeholder(R.mipmap.user_head_def).centerCrop().into(userIcon);
                if (identity == 1) {
                    if (data.getStatus() == 1) {
                        applying();
                    } else if (data.getStatus() == 2) {
                        applyed("已同意");
                        Glide.with(context).load(R.mipmap.icon_tribe_ok).into(iconId);
                    } else if (data.getStatus() == 3) {
                        applyed("已拒绝");
                        Glide.with(context).load(R.mipmap.icon_tribe_unok).into(iconId);
                    }
                } else if (identity == 0){
                    normal();
                }

                nickNameText.setText(data.getNickName());
                createTimeText.setText(TimeUtil.formatDate(data.getCreateTime()));
            }
        }

        @OnClick({R.id.agreeText, R.id.refuseText})
        protected void onClick(View view) {
            switch (view.getId()) {
                case R.id.agreeText:
                    onItemChildViewClick(view, ActionConstant.Action.TRIBE_AGREE);
                    break;
                case R.id.refuseText:
                    onItemChildViewClick(view, ActionConstant.Action.TRIBE_REFUSE);
                    break;
            }
        }

        private void applying() {
            agreeText.setVisibility(View.VISIBLE);
            refuseText.setVisibility(View.VISIBLE);
            otherText.setVisibility(View.GONE);
        }
        private void normal() {
            agreeText.setVisibility(View.GONE);
            refuseText.setVisibility(View.GONE);
            otherText.setVisibility(View.GONE);
        }

        private void applyed(String text) {
            agreeText.setVisibility(View.GONE);
            refuseText.setVisibility(View.GONE);
            otherText.setVisibility(View.VISIBLE);
            otherText.setText(text);
        }
    }
}
