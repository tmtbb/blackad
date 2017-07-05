package com.yundian.blackcard.android.activity;


import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.RegisterInfo;
import com.yundian.blackcard.android.model.WXPayInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.wxapi.WXPayUtil;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.StringUtils;
import com.yundian.comm.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class ConfirmRegisterActivity extends BaseActivity implements OnAPIListener<AccountInfo> {


    @BindView(R.id.confirm_info)
    TextView confirmInfo;
    private RegisterInfo registerInfo;
    private AccountInfo accountInfo;
    private PayInfo payInfo;
    @BindView(R.id.but_next)
    protected Button butNext;
    private final static String REGISTER_PAY = "register_pay";
    private boolean isPay;

    @Override
    public void initData() {
        super.initData();
        setTitle(R.string.title_activity_confirm_register);
        registerInfo = (RegisterInfo) getIntent().getSerializableExtra(RegisterInfo.class.getName());
        isPay = registerInfo.getBlackcardInfo().getBlackcardPrice() > 0;

        butNext.setText(isPay ? "确认注册、支付" : "立即注册");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("黑卡会籍：%s<br/>", registerInfo.getBlackcardInfo().getBlackcardName()));
        stringBuilder.append("黑卡卡号：666随机 <br/>");
        Double monery = registerInfo.getBlackcardInfo().getBlackcardPrice();
        if (StringUtils.isNotEmpty(registerInfo.getCustomName())) {
            stringBuilder.append(String.format("定制姓名：%s <br/>", registerInfo.getCustomName()));
            monery += registerInfo.getCustomNamePrice();
        }
        if (isPay) {
            stringBuilder.append("支付方式：微信 <br/><br/>");
            stringBuilder.append(String.format("支付金额：<font color=\"#E3A63F\">¥%.2f</font> <br/>", monery));
        }

        stringBuilder.append(String.format("    收件人：%s <br/>", registerInfo.getFullName()));
        stringBuilder.append(String.format("联系电话：%s <br/>", registerInfo.getPhoneNum()));
        stringBuilder.append(String.format("收件地址：%s %s%s <br/>", registerInfo.getProvince(), registerInfo.getCity(), registerInfo.getAddr()));
        stringBuilder.append("指定快递：特速快递<br/>");
        confirmInfo.setText(Html.fromHtml(stringBuilder.toString()));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_register;
    }


    @Override
    protected boolean isShowBackButton() {
        return true;
    }


    @OnClick({R.id.but_next})
    protected void onClick(View view) {
        if (accountInfo == null) {
            showLoader("注册中...");
            NetworkAPIFactory.getBlackcardService().register(registerInfo, this);
        } else {
            createPay();
        }
    }

    private void createPay() {
        showLoader("支付中...");
        NetworkAPIFactory.getBlackcardService().tokenPay(accountInfo.getToken(), new OnAPIListener<PayInfo>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
                butNext.setText("重新支付");
            }

            @Override
            public void onSuccess(PayInfo model) {
                payInfo = model;
                wxPay();
            }
        });
    }

    @Override
    public void onSuccess(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        if (accountInfo.getIsPay() == 0) {
            showToast("注册成功");
            setResult(RESULT_OK);
            finish();
        }else {
            createPay();
        }
    }

    private void wxPay() {
        WXPayInfo wxPayInfo = payInfo.getWxPayInfo();
        WXPayUtil.pay(this, wxPayInfo, new OnAPIListener<Boolean>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
                NetworkAPIFactory.getCommService().payLog(REGISTER_PAY, payInfo, ex);
                butNext.setText("重新支付");
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    NetworkAPIFactory.getCommService().payLog(REGISTER_PAY, payInfo, null);
                    closeLoader();
                    ToastUtils.show(ConfirmRegisterActivity.this, "注册成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }


    @OnClick(R.id.show_agreement)
    protected void on(View view) {
        Intent intent = new Intent();
        intent.putExtra(WebViewActivity.EXTRA_KEY_TITLE, "精英黑卡会员协议");
        intent.setData(Uri.parse("http://app.jingyingheika.com/static/UserAgreement.html"));
        intent.setClass(this, WebViewActivity.class);
        startActivity(intent);

    }

}
