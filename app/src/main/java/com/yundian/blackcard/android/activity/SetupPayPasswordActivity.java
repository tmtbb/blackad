package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-08 10:03
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class SetupPayPasswordActivity extends BaseActivity {


    @BindView(R.id.phone_code)
    protected EditText phone_code;
    @BindView(R.id.phoneText)
    protected TextView phoneText;
    @BindView(R.id.code_button)
    protected Button code_button;
    private String codeButtonText;
    private SMSCode smsCode;
    private String phoneNum;

    @Override
    public int getLayoutId() {
        return R.layout.ac_setup_pay_password;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("设置支付密码");
        phoneNum = getIntent().getStringExtra(ActionConstant.IntentKey.PHONE);
        phoneText.setText(StringUtils.replaceInfo(3, phoneNum));
    }

    @OnClick(value = {R.id.okButton})
    protected void onClick(View view) {

        if (TextUtils.isEmpty(phone_code.getText().toString().trim())) {
            showToast(phone_code.getHint());
            return;
        }
        if (smsCode == null) {
            showToast("请先获取验证码");
            return;
        }
        showLoader();
        NetworkAPIFactory.getCommService().validate(phoneNum, smsCode.getCodeToken(), phone_code.getText().toString().trim(), 4, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
            }

            @Override
            public void onSuccess(Object o) {
                smsCode.setPhoneNum(phoneNum);
                smsCode.setPhoneCode(phone_code.getText().toString().trim());
                Intent intent = new Intent(context, SetupPayPassword2Activity.class);
                intent.putExtra(ActionConstant.IntentKey.SMS_CODE, smsCode);
                startActivity(intent);
                finish();
            }
        });

    }

    @OnClick(R.id.code_button)
    protected void onClickSendCode(View view) {

        code_button.setEnabled(false);
        codeButtonText = code_button.getText().toString();
        countDownTimer.start();
        NetworkAPIFactory.getCommService().smsCode(phoneNum, 4, new OnAPIListener<SMSCode>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
                code_button.setEnabled(true);
                countDownTimer.cancel();
                code_button.setText(codeButtonText);
            }

            @Override
            public void onSuccess(SMSCode smsCode) {
                SetupPayPasswordActivity.this.smsCode = smsCode;

                showToast("验证码已发送");
            }
        });
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            code_button.setText(millisUntilFinished / 1000 + "(秒)");
        }

        @Override
        public void onFinish() {
            code_button.setEnabled(true);
            code_button.setText(codeButtonText);
        }
    };
}
