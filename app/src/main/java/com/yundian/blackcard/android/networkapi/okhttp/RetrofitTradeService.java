package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.ButlerserviceInfo;
import com.yundian.blackcard.android.model.PayInfo;
import com.yundian.blackcard.android.model.PurchaseHistoryModel;
import com.yundian.comm.networkapi.response.DefResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yaowang on 2017/6/11.
 */

public interface RetrofitTradeService {
    @FormUrlEncoded
    @POST("/api/trade/usertrades.json")
    Observable<DefResponse<List<PurchaseHistoryModel>>> userTrades(@Field("page") int page);

    @FormUrlEncoded
    @POST("/api/butlerservice/info.json")
    Observable<DefResponse<ButlerserviceInfo>> butlerserviceInfo(@Field("serviceNo") String serviceNo);

    @FormUrlEncoded
    @POST("/api/butlerservice/pay.json")
    Observable<DefResponse<PayInfo>> butlerservicePay(@Field("serviceNo") String serviceNo, @Field("payType") int payType, @Field("payPassword") String payPassword);
}
