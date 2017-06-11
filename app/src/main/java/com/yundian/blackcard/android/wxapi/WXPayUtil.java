package com.yundian.blackcard.android.wxapi;

import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yundian.blackcard.android.model.WXPayInfo;
import com.yundian.comm.networkapi.exception.NetworkAPIException;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaowang on 2017/5/14.
 */

public class WXPayUtil {

    private static IWXAPI wxapi = null;

    private static Map<String, OnAPIListener<Boolean>> listenerMap = new HashMap<String, OnAPIListener<Boolean>>();

    public static boolean pay(Context context, WXPayInfo payInfo, OnAPIListener<Boolean> listener) {
        wxapi = WXAPIFactory.createWXAPI(context, payInfo.getAppId());
        wxapi.registerApp(payInfo.getAppId());
        PayReq request = new PayReq();
        request.appId = payInfo.getAppId();
        request.partnerId = payInfo.getPartnerid();
        request.prepayId = payInfo.getPrepayid();
        request.packageValue = payInfo.getPayPackage();
        request.nonceStr = payInfo.getNoncestr();
        request.timeStamp = payInfo.getTimestamp();
        request.sign = payInfo.getPaysign();
        if (wxapi.sendReq(request)) {
            listenerMap.put(payInfo.getPrepayid(), listener);
            return true;
        }
        return false;
    }

    public static void handleIntent(Intent intent) {
        wxapi.handleIntent(intent, new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {

            }

            @Override
            public void onResp(BaseResp resp) {


                if (resp instanceof PayResp) {
                    PayResp payResp = (PayResp) resp;
                    OnAPIListener<Boolean> listener = listenerMap.get(payResp.prepayId);
                    if (listener != null) {
                        listenerMap.remove(resp.transaction);
                        if (resp.errCode == 0) {
                            listener.onSuccess(true);
                        } else if (resp.errCode == -2) {
                            listener.onError(new NetworkAPIException(1, "取消支付"));
                        } else if (resp.errCode == -1) {
                            listener.onError(new NetworkAPIException(2, "支付失败"));
                        }
                    }
                }

            }
        });
    }

}
