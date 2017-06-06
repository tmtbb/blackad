package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.AccountInfo;
import com.yundian.blackcard.android.model.BlackcardInfos;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.RegisterInfo;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.comm.networkapi.listener.OnAPIListener;

/**
 * Created by yaowang on 2017/5/11.
 */

public interface IBlackcardService {
    void blackcardInfos(OnAPIListener<BlackcardInfos> listener);

    void register(RegisterInfo registerInfo, OnAPIListener<AccountInfo> listener);

    void tokenPay(String token, OnAPIListener<PayInfo> listener);

    void smsCodePay(SMSCode smsCode, OnAPIListener<PayInfo> listener);

    void rePassword(String blackcardNo,SMSCode smsCode,String password,OnAPIListener<Object> listener);

    void smsCode(String blackcardNo,OnAPIListener<SMSCode> listener);

}
