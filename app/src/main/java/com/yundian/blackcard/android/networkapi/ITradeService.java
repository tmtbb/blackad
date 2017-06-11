package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.ButlerserviceInfo;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.PurchaseHistoryModel;
import com.yundian.blackcard.android.networkapi.okhttp.RetrofitTradeService;
import com.yundian.comm.annotation.ServiceType;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

/**
 * Created by yaowang on 2017/5/11.
 */
@ServiceType(RetrofitTradeService.class)
public interface ITradeService {
    void userTrades(int page, OnAPIListener<List<PurchaseHistoryModel>> listener);

    void butlerserviceInfo(String serviceNo, OnAPIListener<ButlerserviceInfo> listener);

    void butlerservicePay(String serviceNo, int payType, String payPassword, OnAPIListener<PayInfo> listener);
}
