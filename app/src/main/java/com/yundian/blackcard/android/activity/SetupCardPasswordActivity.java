package com.yundian.blackcard.android.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;

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
public class SetupCardPasswordActivity extends BaseActivity {

    @BindView(R.id.passwordEdit)
    protected EditText passwordEdit;
    @BindView(R.id.newPasswordEdit)
    protected EditText newPasswordEdit;
    @BindView(R.id.newPasswordEdit2)
    protected EditText newPasswordEdit2;

    @Override
    public int getLayoutId() {
        return R.layout.ac_setup_card_password;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("修改密码");
    }


    @OnClick(value = {R.id.okButton})
    protected void onClick(View view) {
        String password = passwordEdit.getText().toString().trim();
        String newPassword = newPasswordEdit.getText().toString().trim();
        String newPassword2 = newPasswordEdit2.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast("请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(newPassword2)) {
            showToast("请再次输入新密码");
            return;
        }
        if (!newPassword.equals(newPassword2)) {
            showToast("两次密码不一致");
            return;
        }
        showLoader();
        NetworkAPIFactory.getUserService().repassword(password, newPassword, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
            }

            @Override
            public void onSuccess(Object o) {
                closeLoader();
                showToast("密码修改成功");
                finish();
            }
        });
    }
}
