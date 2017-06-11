package com.yundian.blackcard.android.activity;

import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.PurchaseHistoryModel;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.blackcard.android.view.UserSetInfoCell;

import java.io.Serializable;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-09 13:36
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ShopDetailActivity extends BaseActivity {

    @BindView(R.id.tradeGoodsNameCell)
    protected UserSetInfoCell tradeGoodsNameCell;
    @BindView(R.id.tradeNoCell)
    protected UserSetInfoCell tradeNoCell;
    @BindView(R.id.tradePriceCell)
    protected UserSetInfoCell tradePriceCell;
    @BindView(R.id.tradePayPriceCell)
    protected UserSetInfoCell tradePayPriceCell;
    @BindView(R.id.tradeStatusCell)
    protected UserSetInfoCell tradeStatusCell;
    @BindView(R.id.tradeTimeCell)
    protected UserSetInfoCell tradeTimeCell;

    @Override
    public int getLayoutId() {
        return R.layout.ac_shop_detail;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }


    @Override
    public void initView() {
        super.initView();
        setTitle("消费详情");
        PurchaseHistoryModel model = (PurchaseHistoryModel) getIntent().getSerializableExtra(ActionConstant.IntentKey.SHOP);
        tradeGoodsNameCell.update(model.getTradeGoodsName());
        tradeNoCell.update(model.getTradeNo());
        tradePriceCell.update("¥" + model.getTradePrice());
        tradePayPriceCell.update("¥" + model.getTradePayPrice());
        tradeStatusCell.update(model.getTradeStatusTitle());
        tradeTimeCell.update(TimeUtil.formatDate(model.getTradeTime()));
        tradeTimeCell.setLineViewVisibility(View.GONE);
    }


}
