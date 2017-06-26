package com.yundian.blackcard.android.networkapi;

import android.content.Context;

import com.yundian.blackcard.android.BuildConfig;
import com.yundian.blackcard.android.networkapi.okhttp.BlackcardServiceImpl;
import com.yundian.blackcard.android.networkapi.okhttp.CommServiceImpl;
import com.yundian.blackcard.android.networkapi.okhttp.DynamicService;
import com.yundian.blackcard.android.networkapi.okhttp.RetrofitTradeService;
import com.yundian.comm.networkapi.config.NetworkAPIConfig;
import com.yundian.comm.networkapi.invocationhandler.RetrofitServiceProxy;
import com.yundian.comm.networkapi.manage.ServiceManage;
import com.yundian.comm.networkapi.okhttp.OkHttpUtils;
import com.yundian.comm.networkapi.sign.DefSignCalculateImpl;
import com.yundian.comm.util.DeviceUtils;
import com.yundian.comm.util.SPUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaowang on 2017/5/11.
 */

public class NetworkAPIFactory extends ServiceManage {

    private static NetworkAPIFactory networkAPIFactory;

    public NetworkAPIFactory() {
        super(new RetrofitServiceProxy());
    }

    public static Map<String, Object> commParameter = new HashMap<String, Object>();

    public static void addCommParameter(String key, Object object) {
        commParameter.put(key, object);
    }

    public static void init(Context context) {
        commParameter.put("appVersion", DeviceUtils.getVersionName(context));
        commParameter.put("appVersionCode", DeviceUtils.getVersionCode(context));
        commParameter.put("osType", "1");
        if (SPUtils.contains(context, "UserToken")) {
            commParameter.put("token", SPUtils.get(context, "UserToken", ""));
        }
        commParameter.put("keyId", SPUtils.get(context, "deviceKeyId", 34474661562457L));
        String osVersion = String.format("%s(%s)", DeviceUtils.getBuildVersion(), DeviceUtils.getBuildLevel());
        commParameter.put("osVersion", osVersion);

        String deviceKey = SPUtils.get(context, "deviceKey", "24BFA1509B794899834AA9E24B447322").toString();

        final NetworkAPIConfig networkAPIConfig = NetworkAPIConfig.Builder()
                .setBaseUrl(BuildConfig.SERVER_HOST)
                .setCommParameter(commParameter)
                .setConnectTimeout(30)
                .setReadTimeout(30)
                .setWriteTimeout(30)
                .setSignCalculate(new DefSignCalculateImpl(deviceKey)).build();

        OkHttpUtils.getInstance().initConfig(networkAPIConfig);
        try {
            InputStream inputStream = context.getAssets().open("app.jingyingheika.com");
            OkHttpUtils.getInstance().setCertificates(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkAPIFactory = new NetworkAPIFactory();
    }

    public static ICommService getCommService() {
        return new CommServiceImpl();
    }

    public static IDynamicService getDynamicService() {
        return networkAPIFactory.getService(IDynamicService.class);
    }


    public static IUserService getUserService() {
        return networkAPIFactory.getService(IUserService.class);
    }

    public static IBlackcardService getBlackcardService() {
        return new BlackcardServiceImpl();
    }

    public static ITradeService getTradeService() {
        return networkAPIFactory.getService(ITradeService.class);
    }

    public static IArticleService getArticleService() {
        return networkAPIFactory.getService(IArticleService.class);
    }

    public static ITribeService getTribeService() {
        return networkAPIFactory.getService(ITribeService.class);
    }
}
