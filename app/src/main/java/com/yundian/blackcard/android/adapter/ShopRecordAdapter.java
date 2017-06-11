package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.MyPurseDetailModel;
import com.yundian.blackcard.android.model.PurchaseHistoryModel;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.comm.adapter.base.BaseListViewAdapter;
import com.yundian.comm.adapter.viewholder.BaseViewHolder;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 15:48
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ShopRecordAdapter extends BaseListViewAdapter<PurchaseHistoryModel> {
    public ShopRecordAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder<PurchaseHistoryModel> getViewHolder(int position) {
        ShopRecordViewHolder viewHolder = new ShopRecordViewHolder(context);
        PurchaseHistoryModel item = getItem(position);
        if (position < list.size() - 1) {
            PurchaseHistoryModel nextItem = getItem(position + 1);
            if (item.formatTradeTime().equals(nextItem.formatTradeTime())) {
                viewHolder.setDateVisiblity(View.GONE);
                viewHolder.setLineVisiblity(View.VISIBLE);
            } else {
                viewHolder.setDateText(nextItem.formatTradeTime());
                viewHolder.setDateVisiblity(View.VISIBLE);
                viewHolder.setLineVisiblity(View.GONE);
            }
        } else if (position == list.size() - 1) {
            viewHolder.setDateVisiblity(View.GONE);
            viewHolder.setLineVisiblity(View.GONE);
        }
        return viewHolder;
    }


    protected class ShopRecordViewHolder extends BaseViewHolder<PurchaseHistoryModel> {


        public void setDateVisiblity(int visiblity) {
            timeText.setVisibility(visiblity);
        }

        public void setDateText(String text) {
            timeText.setText(text);
        }

        public void setLineVisiblity(int visiblity) {
            lineView.setVisibility(visiblity);
        }


        @BindView(R.id.tradeNo)
        protected TextView tradeNo;
        @BindView(R.id.cellView)
        protected View cellView;
        @BindView(R.id.tradeGoodsName)
        protected TextView tradeGoodsName;
        @BindView(R.id.tradeTime)
        protected TextView tradeTime;
        @BindView(R.id.timeText)
        protected TextView timeText;
        @BindView(R.id.lineView)
        protected View lineView;

        public ShopRecordViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_shop_record;
        }

        @Override
        protected void initListener() {
            super.initListener();
            cellView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemChildViewClick(v, 99);
                }
            });
        }

        @Override
        protected void update(PurchaseHistoryModel data) {
            if (data != null) {
                tradeNo.setText(data.getTradeNo());
                tradeGoodsName.setText(data.getTradeGoodsName());
                tradeTime.setText(TimeUtil.formatDate(data.getTradeTime()));
            }
        }
    }


}
