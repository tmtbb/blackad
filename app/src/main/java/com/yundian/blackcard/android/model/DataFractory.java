package com.yundian.blackcard.android.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 13:40
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DataFractory {

    public static List<MyPurseDetailModel> getMyPurseDetailList() {
        List<MyPurseDetailModel> modelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyPurseDetailModel model = new MyPurseDetailModel();
            model.setAmount(12.3);
            model.setCreateTime(new Date().getTime());
            model.setTradeName("充值");
            model.setTradeNo("12345" + i);
            modelList.add(model);
        }
        return modelList;
    }
    public static List<PurchaseHistoryModel> getPurchaseHistoryList() {
        List<PurchaseHistoryModel> modelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PurchaseHistoryModel model = new PurchaseHistoryModel();
            model.setTradeNo("201412343212345432");
            model.setTradeTime(new Date().getTime());
            model.setTradeGoodsName("充值");
            model.setTradeNo("12345" + i);
            modelList.add(model);
        }
        return modelList;
    }

}

