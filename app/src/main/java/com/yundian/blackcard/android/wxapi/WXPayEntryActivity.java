package com.yundian.blackcard.android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yaowang on 2017/5/14.
 */

public class WXPayEntryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
        WXPayUtil.handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        finish();
        WXPayUtil.handleIntent(getIntent());
    }
}
