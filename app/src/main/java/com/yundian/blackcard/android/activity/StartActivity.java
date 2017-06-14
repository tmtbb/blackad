package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.os.Handler;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.DeviceInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.DeviceUtils;
import com.yundian.comm.util.SPUtils;

public class StartActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    private void next(int delayMillis) {
        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent();
                String versionName = SPUtils.get(context, GuidActivity.class.getSimpleName(), "0").toString();
                try {
                    if (Double.parseDouble(DeviceUtils.getVersionName(context)) > Double.parseDouble(versionName.toString())) {
                        intent.setClass(StartActivity.this, GuidActivity.class);
                    } else {
                        intent.setClass(StartActivity.this, LoginActivity.class);
                    }
                } catch (Exception e) {
                    intent.setClass(StartActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, delayMillis);
    }

    private void deviceRegister() {

        DeviceInfo deviceInfo = new DeviceInfo();
        String deviceModel = String.format("%s %s", DeviceUtils.getPhoneBrand(), DeviceUtils.getPhoneModel());
        deviceInfo.setDeviceId(DeviceUtils.getDeviceId(this))
                .setDeviceModel(deviceModel)
                .setDeviceName(DeviceUtils.getPhoneBrand())
                .setDeviceResolution(DeviceUtils.deviceWidth(this) + "x" + DeviceUtils.deviceHeight(this));

        NetworkAPIFactory.getCommService().deviceRegister(deviceInfo, new OnAPIListener<DeviceInfo>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
                deviceRegister();
            }

            @Override
            public void onSuccess(DeviceInfo deviceInfo) {
                if (deviceInfo.getDeviceKeyId() != null) {
                    SPUtils.put(getApplicationContext(), "deviceKeyId", deviceInfo.getDeviceKeyId());
                    SPUtils.put(getApplicationContext(), "deviceKey", deviceInfo.getDeviceKey());
                    NetworkAPIFactory.init(StartActivity.this);
                }
                next(1000);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        NetworkAPIFactory.init(this);
        deviceRegister();
    }


}
