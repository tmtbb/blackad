package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.DeviceInfo;
import com.yundian.blackcard.android.model.PurchaseHistoryModel;
import com.yundian.blackcard.android.model.SMSCode;
import com.yundian.blackcard.android.networkapi.okhttp.TradeServiceImpl;
import com.yundian.blackcard.android.networkapi.okhttp.UserServiceImpl;
import com.yundian.comm.annotation.ServiceType;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

/**
 * Created by yaowang on 2017/5/11.
 */
@ServiceType(TradeServiceImpl.class)
public interface ITradeService {
    void userTrades(int page, OnAPIListener<List<PurchaseHistoryModel>> listener);
}
