package com.yundian.blackcard.android.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiyukf.unicorn.activity.ServiceMessageFragment;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.OnBotEventListener;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.pop.OnShopEventListener;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.OrderDetailActivity;
import com.yundian.comm.util.ToastUtils;

/**
 * Created by yaowang on 2017/5/13.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Unicorn.init(this, "9bcdb2182bde2835e2b0e7fc72a39e68", options(), new UnicornImageLoader() {
//
//            @Nullable
//            @Override
//            public Bitmap loadImageSync(String uri, int width, int height) {
//                return null;
//            }
//
//            @Override
//            public void loadImage(String uri, int width, int height, final ImageLoaderListener listener) {
//                Glide.with(AppApplication.this).load(uri).asBitmap().into(new SimpleTarget<Bitmap>(width, height) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        if (listener != null) {
//                            listener.onLoadComplete(resource);
//                        }
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        if (listener != null) {
//                            listener.onLoadFailed(e);
//                        }
//                    }
//                });
//            }
//        });
    }

//    private YSFOptions options() {
//        YSFOptions options = new YSFOptions();
//        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
//        options.savePowerConfig = new SavePowerConfig();
//        options.uiCustomization.rightAvatar = "";
//        options.uiCustomization = new UICustomization();
//        options.uiCustomization.titleBarStyle = 1;
//        options.uiCustomization.titleBackgroundColor = getResources().getColor(R.color.colorPrimary);
//        options.onMessageItemClickListener = new OnMessageItemClickListener() {
//            @Override
//            public void onURLClicked(Context context, String url) {
//
//                if (url.startsWith("http://app.jingyingheika.com/service/")) {
//                    url = url.substring(7, url.length());
//                    url = "ydservice://" + url;
//                }
//                Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent1.setData(Uri.parse(url));
//                startActivity(intent1);
//
//            }
//        };
//        return options;
//    }
}
