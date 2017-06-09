package com.yundian.blackcard.android.activity;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.WXPayInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.CashierInputFilter;
import com.yundian.blackcard.android.wxapi.WXPayUtil;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 14:11
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.amountEdit)
    protected EditText amountEdit;
    @BindView(R.id.aliPayLayout)
    protected ViewGroup aliPayLayout;
    @BindView(R.id.aliPayIcon)
    protected ImageView aliPayIcon;
    @BindView(R.id.weixinPayLayout)
    protected ViewGroup weixinPayLayout;
    @BindView(R.id.weixinPayIcon)
    protected ImageView weixinPayIcon;
    @BindView(R.id.payButton)
    protected Button payButton;
    private int payType = 1;

    @Override
    public int getLayoutId() {
        return R.layout.ac_recharge;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("充值");
        amountEdit.setFilters(new InputFilter[]{new CashierInputFilter()});
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @OnClick(value = {R.id.aliPayLayout, R.id.weixinPayLayout, R.id.payButton})
    protected void payLayoutClick(View view) {
        switch (view.getId()) {
            case R.id.weixinPayLayout:
                payType = 1;
                weixinPayIcon.setImageResource(R.mipmap.payselect);
                aliPayIcon.setImageResource(R.mipmap.paydefault);
                break;
            case R.id.aliPayLayout:
                payType = 2;
                weixinPayIcon.setImageResource(R.mipmap.paydefault);
                aliPayIcon.setImageResource(R.mipmap.payselect);
                break;
            case R.id.payButton:
                String amount = amountEdit.getText().toString().trim();
                if (TextUtils.isEmpty(amount)) {
                    showToast("请输入充值金额");
                    return;
                }
                showLoader();
                NetworkAPIFactory.getUserService().recharge(Double.parseDouble(amount), payType, new OnAPIListener<PayInfo>() {
                    @Override
                    public void onError(Throwable ex) {
                        onShowError(ex);
                    }

                    @Override
                    public void onSuccess(PayInfo payInfo) {
                        wxPay(payInfo);
                    }
                });
                break;
        }
    }

    private void wxPay(PayInfo payInfo) {
        WXPayInfo wxPayInfo = payInfo.getWxPayInfo();
        WXPayUtil.pay(this, wxPayInfo, new OnAPIListener<Boolean>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
                payButton.setText("重新支付");
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    closeLoader();
                    showToast("支付成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
