package com.yundian.blackcard.android.activity;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.ButlerserviceInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.blackcard.android.view.ChoosePayView;
import com.yundian.blackcard.android.view.UserSetInfoCell;
import com.yundian.comm.listener.OnChildViewClickListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-09 13:36
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class OrderDetailActivity extends BaseActivity {


    @BindView(R.id.contentView)
    protected ScrollView contentView;

    @BindView(R.id.tradeGoodsNameCell)
    protected UserSetInfoCell tradeGoodsNameCell;

    @BindView(R.id.tradeNoCell)
    protected UserSetInfoCell tradeNoCell;
    @BindView(R.id.serviceDetailsText)
    protected TextView serviceDetailsText;
    @BindView(R.id.tradePriceCell)
    protected UserSetInfoCell tradePriceCell;
    @BindView(R.id.tradeStatusCell)
    protected UserSetInfoCell tradeStatusCell;
    @BindView(R.id.phoneNumCell)
    protected UserSetInfoCell phoneNumCell;
    @BindView(R.id.tradeTimeCell)
    protected UserSetInfoCell tradeTimeCell;
    private String serviceNo;
    private ButlerserviceInfo butlerserviceInfo;

    @Override
    public int getLayoutId() {
        return R.layout.ac_order_detail;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }


    @Override
    public void initView() {
        super.initView();
        setTitle("订单详情");
        contentView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void initData() {
        super.initData();
        serviceNo = "20170609131549180000";
        showLoader();
        NetworkAPIFactory.getTradeService().butlerserviceInfo(serviceNo, new OnAPIListener<ButlerserviceInfo>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
            }

            @Override
            public void onSuccess(ButlerserviceInfo info) {
                closeLoader();
                contentView.setVisibility(View.VISIBLE);
                OrderDetailActivity.this.butlerserviceInfo = info;
                tradeGoodsNameCell.update(info.getServiceName());
                tradeNoCell.update(info.getServiceNo());
                serviceDetailsText.setText(info.getServiceDetails());
                tradePriceCell.update("¥" + info.getServiceAmount());
                tradeStatusCell.update(info.getServiceStatusTitle());
                phoneNumCell.update(info.getServiceUserTel());
                tradeTimeCell.update(TimeUtil.formatDate(info.getCreateTime()));
                tradeTimeCell.setLineViewVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.payButton)
    protected void payClick(View view) {
        final Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        bottomDialog.setCanceledOnTouchOutside(true);
        ChoosePayView contentView = new ChoosePayView(context);
        contentView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                switch (action) {
                    case ActionConstant.Action.PAY_ALI:
                        showToast("支付宝支付");
                        break;
                    case ActionConstant.Action.PAY_CLOSE:
                        bottomDialog.dismiss();
                        break;
                    case ActionConstant.Action.PAY_WEIXIN:
                        showToast("微信支付");
                        break;
                    case ActionConstant.Action.PAY_PURSE:
                        showToast("余额支付");
                        break;
                }
            }
        });
        contentView.update(butlerserviceInfo);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
}
