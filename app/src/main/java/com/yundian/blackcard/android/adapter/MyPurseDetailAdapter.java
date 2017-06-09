package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.MyPurseDetailModel;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 11:43
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class MyPurseDetailAdapter extends BaseListViewAdapter<MyPurseDetailModel> {

    public MyPurseDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<MyPurseDetailModel> getViewHolder(int position) {
        MyPurseViewHolder viewHolder = new MyPurseViewHolder(context);
        MyPurseDetailModel item = getItem(position);
        if (position < list.size() - 1) {
            MyPurseDetailModel nextItem = getItem(position + 1);
            if (item.formatCreateTime().equals(nextItem.formatCreateTime())) {
                viewHolder.setDateVisiblity(View.GONE);
                viewHolder.setLineVisiblity(View.VISIBLE);
            } else {
                viewHolder.setDateText(nextItem.formatCreateTime());
                viewHolder.setDateVisiblity(View.VISIBLE);
                viewHolder.setLineVisiblity(View.GONE);
            }
        } else if (position == list.size() - 1) {
            viewHolder.setDateVisiblity(View.GONE);
            viewHolder.setLineVisiblity(View.GONE);
        }

        return viewHolder;
    }

    protected class MyPurseViewHolder extends BaseViewHolder<MyPurseDetailModel> {

        @BindView(R.id.tradeNameText)
        protected TextView tradeNameText;
        @BindView(R.id.createTimeText)
        protected TextView createTimeText;
        @BindView(R.id.amountText)
        protected TextView amountText;
        @BindView(R.id.timeText)
        protected TextView timeText;
        @BindView(R.id.lineView)
        protected View lineView;

        public void setDateVisiblity(int visiblity) {
            timeText.setVisibility(visiblity);
        }

        public void setDateText(String text) {
            timeText.setText(text);
        }

        public void setLineVisiblity(int visiblity) {
            lineView.setVisibility(visiblity);
        }

        public MyPurseViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_mypurse_detail;
        }

        @Override
        protected void update(MyPurseDetailModel data) {
            if (data != null) {
                if (data.getAmount() > 0) {
                    amountText.setTextColor(context.getResources().getColor(R.color.color_1FCF55));
                    amountText.setText("+￥" + data.getAmount());
                } else {
                    amountText.setTextColor(context.getResources().getColor(R.color.color_DB462E));
                    amountText.setText("-￥" + data.getAmount() + "");
                }
                tradeNameText.setText(data.getTradeName());
                createTimeText.setText(TimeUtil.formatDate(data.getCreateTime()));

            }
        }
    }

}
