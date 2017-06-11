package com.yundian.blackcard.android.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.yundian.blackcard.android.model.AliPayInfo;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.Map;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-10 10:57
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class AliPayUtil {

    private static final int SDK_PAY_FLAG = 100;
    private static OnAPIListener<Boolean> onAPIListener;
    private static final String SDK_PAY_SUCCESS = "9000";

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, SDK_PAY_SUCCESS)) {
                        onAPIListener.onSuccess(true);
                    } else {
                        onAPIListener.onError(new Throwable("支付失败"));
                    }
                    break;
                }
            }
        }
    };

    public static boolean pay(final Activity context, final AliPayInfo aliPayInfo, OnAPIListener<Boolean> listener) {
        onAPIListener = listener;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(aliPayInfo.getOrderInfo(), true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
        return true;
    }

    public static class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(Map<String, String> rawResult) {
            if (rawResult == null) {
                return;
            }
            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult.get(key);
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult.get(key);
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult.get(key);
                }
            }
        }

        public String getResultStatus() {
            return resultStatus;
        }

        public String getMemo() {
            return memo;
        }

        public String getResult() {
            return result;
        }
    }
}
