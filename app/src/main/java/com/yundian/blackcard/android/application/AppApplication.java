package com.yundian.blackcard.android.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.yundian.blackcard.android.R;

/**
 * Created by yaowang on 2017/5/13.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Unicorn.init(this, "9bcdb2182bde2835e2b0e7fc72a39e68", options(), new UnicornImageLoader()
        {

            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, final ImageLoaderListener listener) {
                Glide.with(AppApplication.this).load(uri).asBitmap().into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (listener != null) {
                            listener.onLoadComplete(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        if (listener != null) {
                            listener.onLoadFailed(e);
                        }
                    }
                });
            }
        });
    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        options.uiCustomization = new UICustomization();
        options.uiCustomization.titleBarStyle = 1;
        options.uiCustomization.titleBackgroundColor = getResources().getColor(R.color.colorPrimary);
        return options;
    }
}
