package com.yundian.comm.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

/*
 * Toast自定义显示时间
 * 使用方法
 * 1.先初始化类 ToastUtils myToast = new ToastUtils(this);
 * 2.显示消息   myToast.setText("要显示的内容");//设置要显示的内容
 *				myToast.show(8000);  //传入消息显示时间，单位毫秒，最少2000毫秒，并且只能是2000的倍数。
 *				传入0时会一直显示，只有调用myToast.cancel();时才会取消。
 * 3.取消消息显示   myToast.cancel();
 * */

public class ToastUtils {

    private Context mContext = null;
    private Toast mToast = null;
    private Handler mHandler = null;
    private int duration = 0;
    private int currDuration = 0;
    private final int DEFAULT = 2000;
    private Runnable mToastThread = new Runnable() {

        public void run() {
            mToast.show();
            mHandler.postDelayed(mToastThread, DEFAULT);// 每隔2秒显示一次
            if (duration != 0) {
                if (currDuration <= duration) {
                    currDuration += DEFAULT;
                } else {
                    cancel();
                }
            }

        }
    };

    public ToastUtils(Context context) {
        mContext = context;
        currDuration = DEFAULT;
        if (mContext != null) {
            mHandler = new Handler(mContext.getMainLooper());
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
        }
    }

    public static void show(Context context, String text, int duration) {
        final ToastUtils toastUtils = new ToastUtils(context);
        toastUtils.setText(text);
        toastUtils.show(duration);
    }

    public static void show(Context context, String text) {
        final ToastUtils toastUtils = new ToastUtils(context);
        toastUtils.setText(text);
        toastUtils.show(2000);
    }

    public static void show(Context context, int resId) {
        final ToastUtils toastUtils = new ToastUtils(context);
        toastUtils.setText(context.getString(resId));
        toastUtils.show(2000);
    }

    public void setText(String text) {
        if (mToast != null)
            mToast.setText(text);
    }

    public void show(int duration) {
        this.duration = duration;
        if (mToast != null)
            mHandler.post(mToastThread);
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
    }

    public void setDuration(int duration) {
        mToast.setDuration(duration);
    }

    public void setView(View view) {
        mToast.setView(view);
    }

    public void cancel() {
        mHandler.removeCallbacks(mToastThread);// 先把显示线程删除
        mToast.cancel();// 把最后一个线程的显示效果cancel掉，就一了百了了
        currDuration = DEFAULT;
    }

}
