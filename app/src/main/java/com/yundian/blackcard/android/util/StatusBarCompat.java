package com.yundian.blackcard.android.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-03-31 09:23
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class StatusBarCompat {

    public static final String TAG = StatusBarCompat.class.getName();

    public static final int COLOR_DEFAULT_WHITE = Color.parseColor("#434343");

    public static void compat(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        ViewGroup contentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(statusColor);
                View childView = contentView.getChildAt(0);
                if (childView != null) {
                    ViewCompat.setFitsSystemWindows(childView, true);
                }
            } else {
                int statusBarHeight = getStatusBarHeight(activity);

                View childView = contentView.getChildAt(0);
                if (childView != null) {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) childView.getLayoutParams();
                    if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                        ViewCompat.setFitsSystemWindows(childView, false);
                        lp.topMargin += statusBarHeight;
                        childView.setLayoutParams(lp);
                    }
                }

                View statusBarView = contentView.getChildAt(0);
                if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
                    statusBarView.setBackgroundColor(statusColor);
                    return;
                }
                statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                statusBarView.setBackgroundColor(statusColor);
                contentView.addView(statusBarView, 0, lp);
            }
        }
    }


    public static void translucent(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup contentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        View childView = contentView.getChildAt(0);
        if (childView != null) {
            ViewCompat.setFitsSystemWindows(childView, false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(activity);

            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                if (childView != null && childView.getLayoutParams() != null && childView.getLayoutParams().height == statusBarHeight) {
                    contentView.removeView(childView);
                    childView = contentView.getChildAt(0);
                }
                if (childView != null) {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) childView.getLayoutParams();
                    if (lp != null && lp.topMargin >= statusBarHeight) {
                        lp.topMargin -= statusBarHeight;
                        childView.setLayoutParams(lp);
                    }
                }
            }

        }
    }


    public static void compat(Activity activity) {
        compat(activity, COLOR_DEFAULT_WHITE);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }
}
