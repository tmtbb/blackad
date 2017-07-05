package com.yundian.blackcard.android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.helper.CityPickerHelper;
import com.yundian.blackcard.android.model.RegisterInfo;
import com.yundian.comm.util.StringUtils;
import com.yundian.comm.util.ValidateUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterInfoActivity extends BaseActivity {


    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.addr)
    EditText addr;
    @BindView(R.id.addr1)
    EditText addr1;
    @BindView(R.id.fullName)
    EditText fullName;
    @BindView(R.id.identity_card)
    EditText identityCard;
    @BindView(R.id.but_next)
    Button butNext;
    private RegisterInfo registerInfo;
    @BindView(R.id.province_city)
    TextView provinceCity;
    CityPickerHelper cityPickerHelper;
    @BindView(R.id.content)
    ScrollView scrollView;


    @Override
    public void initData() {
        super.initData();
        setTitle(R.string.title_activity_register_info);
        registerInfo = (RegisterInfo) getIntent().getSerializableExtra(RegisterInfo.class.getName());
        cityPickerHelper = new CityPickerHelper();

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_info;
    }

    @OnClick({R.id.but_next})
    public void onClick(View view) {

        registerInfo.setPhoneNum(phone.getText().toString().trim())
                .setEmail(email.getText().toString().trim())
                .setAddr(addr.getText().toString().trim())
                .setAddr1(addr1.getText().toString().trim())
                .setFullName(fullName.getText().toString().trim())

                .setIdentityCard(identityCard.getText().toString().trim());

        if (StringUtils.isEmpty(registerInfo.getPhoneNum())) {
            showToast(phone.getHint());
            return;
        } else if (!ValidateUtils.isPhone(registerInfo.getPhoneNum())) {
            showToast("请输入正确的手机号!");
            return;
        }

        if (StringUtils.isEmpty(registerInfo.getEmail())) {
            showToast(email.getHint());
            return;
        } else if (!ValidateUtils.isEmail(registerInfo.getEmail())) {
            showToast("请输入正确的邮箱!");
            return;
        }


        if (StringUtils.isEmpty(registerInfo.getAddr())) {
            showToast(addr.getHint());
            return;
        } else if (registerInfo.getAddr().length() < 6) {
            showToast("详细地址长度最少6位!");
            return;
        }

        if (StringUtils.isEmpty(provinceCity.getText().toString())) {
            showToast(provinceCity.getHint());
            return;
        }

        if (StringUtils.isEmpty(registerInfo.getFullName())) {
            showToast("请输入真实姓名!");
            return;
        } else if (!ValidateUtils.isFullName(registerInfo.getFullName())) {
            showToast("请输入汉字姓名!");
            return;
        }

//        if (StringUtils.isEmpty(identityCard.getText().toString())) {
//            showToast("请输入身份证号!");
//            return;
//        } else
        if (!StringUtils.isEmpty(identityCard.getText().toString())) {
            if (!ValidateUtils.isValidatedAllIdcard(registerInfo.getIdentityCard())) {
                showToast("请输入正确的身份证号!");
                return;
            }
        }


        Bundle bundle = new Bundle();
        bundle.putSerializable(RegisterInfo.class.getName(), registerInfo);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, ConfirmRegisterActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }


    @OnClick(R.id.province_city)
    public void onViewClicked() {
        closeSoftKeyboard();
        cityPickerHelper.selectCity(context, new CityPickerHelper.OnCitySelectedListener() {
            @Override
            public void onCitySelected(String... citySelected) {
                registerInfo.setProvince(citySelected[0]);
                registerInfo.setCity(citySelected[1]);
                provinceCity.setText(citySelected[0] + citySelected[1]);
            }
        });
    }
}
