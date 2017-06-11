package com.yundian.blackcard.android.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.AliPayInfo;
import com.yundian.blackcard.android.model.BalanceModel;
import com.yundian.blackcard.android.model.ButlerserviceInfo;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.WXPayInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.AliPayUtil;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.blackcard.android.view.AlertDialog;
import com.yundian.blackcard.android.view.ChoosePayView;
import com.yundian.blackcard.android.view.UserSetInfoCell;
import com.yundian.blackcard.android.wxapi.WXPayUtil;
import com.yundian.comm.listener.OnChildViewClickListener;
import com.yundian.comm.networkapi.exception.NetworkAPIException;
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
    @BindView(R.id.payButton)
    protected Button payButton;
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
    private Dialog bottomDialog;
    private PayInfo payInfo;
    private BalanceModel balanceModel;
    private final static String BUTLERSERVICE_PAY = "butlerservice_pay";

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
        String dataString = getIntent().getDataString();
        serviceNo = dataString.split("/")[dataString.split("/").length - 2];
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

                payButton.setVisibility(info.getServiceStatus() == 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @OnClick(R.id.payButton)
    protected void payClick(View view) {
        bottomDialog = new Dialog(context, R.style.BottomDialog);
        bottomDialog.setCanceledOnTouchOutside(true);
        final ChoosePayView contentView = new ChoosePayView(context);
        contentView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                switch (action) {
                    case ActionConstant.Action.PAY_ALI:
                        requestPayInfo(butlerserviceInfo.getServiceNo(), 2, "");
                        break;
                    case ActionConstant.Action.PAY_CLOSE:
                        bottomDialog.dismiss();
                        break;
                    case ActionConstant.Action.PAY_WEIXIN:
                        requestPayInfo(butlerserviceInfo.getServiceNo(), 1, "");
                        break;
                    case ActionConstant.Action.PAY_PURSE:
                        if (butlerserviceInfo.getServiceAmount() > balanceModel.getBalance()) {
                            showToast("你的账户余额不足，无法完成支付");
                        } else {
                            Intent intent = new Intent(context, PursePayActivity.class);
                            intent.putExtra(ActionConstant.IntentKey.PHONE, butlerserviceInfo.getServiceUserTel());
                            startActivityForResult(intent, 0);
                        }

                        break;
                }
            }
        });
        setCurrentAmount(contentView);
        contentView.update(butlerserviceInfo);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void setCurrentAmount(final ChoosePayView contentView) {
        NetworkAPIFactory.getUserService().balance(new OnAPIListener<BalanceModel>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
            }

            @Override
            public void onSuccess(BalanceModel balanceModel) {
                OrderDetailActivity.this.balanceModel = balanceModel;
                contentView.setCurrentAmount(balanceModel.getBalance());
            }
        });
    }

    private void requestPayInfo(final String serviceNo, final int payType, String payPassword) {
        NetworkAPIFactory.getTradeService().butlerservicePay(serviceNo, payType, payPassword, new OnAPIListener<PayInfo>() {
            @Override
            public void onError(Throwable ex) {
                if (ex instanceof NetworkAPIException) {
                    if (((NetworkAPIException) ex).getErrorCode() == 10020) {
                        createAlertDialog();
                    }
                }
                onShowError(ex);
                payInfo = new PayInfo();
                payInfo.setPayType(payType);
                payInfo.setTradeNo(serviceNo);
                payInfo.setPayTotalPrice(butlerserviceInfo.getServiceAmount());
                NetworkAPIFactory.getCommService().payLog(BUTLERSERVICE_PAY, payInfo, ex);

            }

            @Override
            public void onSuccess(PayInfo payInfo) {
                OrderDetailActivity.this.payInfo = payInfo;
                if (payInfo.getPayType() == 1)
                    wxPay(payInfo);
                else if (payInfo.getPayType() == 2)
                    aliPay(payInfo);
                else if (payInfo.getPayType() == 0) {
                    if (payInfo.getPayTotalPrice() == butlerserviceInfo.getServiceAmount())
                        paySuccess();
                }

            }
        });
    }


    private void createAlertDialog() {
        final AlertDialog alertDialog = new AlertDialog(context).builder()
                .setTitle("提示")
                .setCancelable(false)
                .setMsg("你尚未设置支付密码，是否立即设置")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SetupPayPasswordActivity.class);
                        intent.putExtra(ActionConstant.IntentKey.PHONE, butlerserviceInfo.getServiceUserTel());
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }


    private void aliPay(PayInfo payInfo) {
        AliPayInfo aliPayInfo = payInfo.getAliPayInfo();
        AliPayUtil.pay(this, aliPayInfo, listener);
    }

    private void wxPay(PayInfo payInfo) {
        WXPayInfo wxPayInfo = payInfo.getWxPayInfo();
        WXPayUtil.pay(this, wxPayInfo, listener);
    }

    private OnAPIListener<Boolean> listener = new OnAPIListener<Boolean>() {
        @Override
        public void onError(Throwable ex) {
            onShowError(ex);
            payButton.setText("重新支付");
            NetworkAPIFactory.getCommService().payLog(BUTLERSERVICE_PAY, payInfo, ex);
        }

        @Override
        public void onSuccess(Boolean aBoolean) {
            if (aBoolean) {
                paySuccess();
            }
        }
    };

    private void paySuccess() {
        if (bottomDialog.isShowing())
            bottomDialog.dismiss();
        NetworkAPIFactory.getCommService().payLog(BUTLERSERVICE_PAY, payInfo, null);
        closeLoader();
        showToast("支付成功");
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String password = data.getStringExtra(ActionConstant.IntentKey.PAY_PASSWORD);
            requestPayInfo(butlerserviceInfo.getServiceNo(), 0, password);
        }
    }
}
