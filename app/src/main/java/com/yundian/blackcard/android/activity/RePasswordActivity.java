package com.yundian.blackcard.android.activity;


import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RePasswordActivity extends BaseActivity {


    @BindView(R.id.blackcard_no)
    EditText blackcardNo;
    @BindView(R.id.phone_code)
    EditText phoneCode;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password1)
    EditText password1;
    @BindView(R.id.code_button)
    TextView codeButton;
    private String codeButtonText;
    private SMSCode smsCode;

    @Override
    public void initData() {
        super.initData();
        setTitle(R.string.title_activity_repassword);
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repassword;
    }

    @OnClick(R.id.but_repassword)
    protected void onRePassword(View view) {
        EditText[] editTexts = {blackcardNo, phoneCode, password, password1};
        for (EditText editText : editTexts) {
            if (StringUtils.isEmpty(editText.getText().toString())) {
                showToast(editText.getHint());
                return;
            }
        }
        String strBlackcardNo = blackcardNo.getText().toString().trim();
//        if( strBlackcardNo.indexOf("88") != 0 ) {
//            showToast("黑卡卡号不正确!");
//            return;
//        }

        String strPassword = password.getText().toString().trim();
        if (strPassword.length() < 6) {
            showToast("密码长度最少6位!");
            return;
        }
        String strPassword1 = password1.getText().toString().trim();
        if (!strPassword.equals(strPassword1)) {
            showToast("两次密码不一致!");
            return;
        }
        smsCode.setPhoneCode(phoneCode.getText().toString().trim());
        showToast("重置密码...");
        NetworkAPIFactory.getBlackcardService().rePassword(strBlackcardNo, smsCode,
                strPassword, new OnAPIListener<Object>() {

            @Override
            public void onSuccess(Object o) {
                closeLoader();
                showToast("重置密码成功");
                finish();
            }

            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
            }
        });

    }

    @OnClick(R.id.code_button)
    protected void onClickSendCode(View view) {
        if (StringUtils.isEmpty(blackcardNo.getText().toString())) {
            showToast(blackcardNo.getHint());
            return;
        }
        codeButton.setEnabled(false);
        blackcardNo.setEnabled(false);
        codeButtonText = codeButton.getText().toString();
        countDownTimer.start();
        NetworkAPIFactory.getBlackcardService().smsCode(blackcardNo.getText().toString(),
                new OnAPIListener<SMSCode>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
                codeButton.setEnabled(true);
                blackcardNo.setEnabled(true);
                countDownTimer.cancel();
                codeButton.setText(codeButtonText);
            }

            @Override
            public void onSuccess(SMSCode smsCode) {
                RePasswordActivity.this.smsCode = smsCode;
                showToast("验证码已发送");
            }
        });
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            codeButton.setText(millisUntilFinished / 1000 + "(秒)");
        }

        @Override
        public void onFinish() {
            codeButton.setEnabled(true);
            codeButton.setText(codeButtonText);
        }
    };

}
