package com.yundian.blackcard.android.constant;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 10:39
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public interface ActionConstant {

    interface Action {
        int EMPTY_BUTTON = 0;
        int PAY_CLOSE = EMPTY_BUTTON + 1;
        int PAY_PURSE = PAY_CLOSE + 1;
        int PAY_ALI = PAY_PURSE + 1;
        int PAY_WEIXIN = PAY_ALI + 1;
    }

    interface IntentKey {
        String PAY_PASSOWRD = "PAY_PASSOWRD";
        String PHONE = "PHONE";
        String SHOP = "SHOP";
        String SMS_CODE = "SMS_CODE";
    }
}
