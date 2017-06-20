package com.yundian.blackcard.android.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import com.qiyukf.unicorn.api.YSFOptions;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.fragment.DynamicFragment;
import com.yundian.blackcard.android.fragment.HomeFragment;
import com.yundian.blackcard.android.fragment.MyFragment;
import com.yundian.blackcard.android.model.UpdateInfo;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.DeviceUtils;
import com.yundian.comm.util.LogUtils;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.BindView;

import static com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog.FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE;
import static com.qiangxi.checkupdatelibrary.dialog.UpdateDialog.UPDATE_DIALOG_PERMISSION_REQUEST_CODE;


public class MainActivity extends BaseActivity {


    @BindView(R.id.navigation)
    protected BottomNavigationView navigationView;
    @BindView(R.id.service_message_badge)
    protected View serviceMessageBadge;
    private Fragment[] fragments = new Fragment[3];
    private ForceUpdateDialog forceupdatedialog;
    private UpdateDialog updateDialog;
    private DynamicFragment dynamicFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    showFragment(0);
                    return true;
                }
                case R.id.navigation_dashboard:
                    serviceMessageBadge.setVisibility(View.GONE);
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ServiceMessageActivity.class);
                    UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra(UserInfo.class.getName());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(UserInfo.class.getName(), userInfo);
                    intent.putExtras(bundle);
                    intent.putExtra("title", "管家");
                    startActivity(intent);
                    break;

                case R.id.navigation_dynamic: {
                    showFragment(1);
                    return true;
                }
                case com.yundian.blackcard.android.R.id.navigation_notifications: {
                    showFragment(2);
                    return true;
                }
            }
            return false;
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Unicorn.init(this, "9bcdb2182bde2835e2b0e7fc72a39e68", options(), new UnicornImageLoader() {

            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, final ImageLoaderListener listener) {
                Glide.with(MainActivity.this).load(uri).asBitmap().into(new SimpleTarget<Bitmap>(width, height) {
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        showFragment(0);
        disableShiftMode();
    }

    public void disableShiftMode() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            LogUtils.showException(e);
        } catch (IllegalAccessException e) {
            LogUtils.showException(e);
        }
    }


    private void showFragment(Integer index) {
        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new HomeFragment();
                    break;
                case 1:
                    fragments[index] = dynamicFragment = new DynamicFragment();
                    break;
                case 2:
                    fragments[index] = new MyFragment();
                    break;
            }
        }
        replacenFragment(fragments[index]);
    }

    private void replacenFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void initListener() {
        super.initListener();
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Unicorn.addUnreadCountChangeListener(new UnreadCountChangeListener() {
            @Override
            public void onUnreadCountChange(int count) {
                serviceMessageBadge.setVisibility(View.VISIBLE);
            }
        }, true);
    }

    @Override
    public void initData() {
        super.initData();
        checkAppVersion();
    }

    private void checkAppVersion() {
        Integer appVersionCode = Integer.parseInt(DeviceUtils.getVersionCode(context));
        NetworkAPIFactory.getCommService().checkAppVersion(appVersionCode, new OnAPIListener<UpdateInfo>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(UpdateInfo updateInfo) {
                if (updateInfo != null && updateInfo.getIsUpdate() == 1) {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    String fileName = getPackageName() + "_" + updateInfo.getVersion() + ".apk";
                    if (updateInfo.getIsForce() == 1) {
                        forceupdatedialog = new ForceUpdateDialog(MainActivity.this);
                        forceupdatedialog.setDownloadUrl(updateInfo.getUrl())
                                .setTitle(getString(R.string.app_name) + "有更新啦")
                                .setVersionName(updateInfo.getVersion())
                                .setUpdateDesc(updateInfo.getDescription())
                                .setFileName(fileName)
                                .setFilePath(path.getAbsolutePath())
                                .show();
                    } else {
                        updateDialog = new UpdateDialog(MainActivity.this);
                        updateDialog.setTitle(getString(R.string.app_name) + "有更新啦")
                                .setDownloadUrl(updateInfo.getUrl())
                                .setVersionName(updateInfo.getVersion())
                                .setUpdateDesc(updateInfo.getDescription())
                                .setFileName(fileName)
                                .setFilePath(path.getAbsolutePath())
                                .setShowProgress(true)
                                .setIconResId(R.mipmap.ic_launcher)
                                .setAppName(getString(R.string.app_name))
                                .show();
                    }

                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                updateDialog.download();
            } else if (requestCode == FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                forceupdatedialog.download();
            }
        } else {
            showToast("请开启读写sd卡权限,不然无法正常升级");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceMessageBadge.setVisibility(Unicorn.getUnreadCount() == 0 ? View.GONE : View.VISIBLE);
    }


    private YSFOptions options() {
        UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra(UserInfo.class.getName());
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        options.uiCustomization = new UICustomization();
        options.uiCustomization.rightAvatar = userInfo.getHeadUrl();
        options.uiCustomization.titleBarStyle = 1;
        options.uiCustomization.titleBackgroundColor = getResources().getColor(R.color.colorPrimary);
        options.onMessageItemClickListener = new OnMessageItemClickListener() {
            @Override
            public void onURLClicked(Context context, String url) {

                if (url.startsWith("http://app.jingyingheika.com/service/")) {
                    url = url.substring(7, url.length());
                    url = "ydservice://" + url;
                }
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setData(Uri.parse(url));
                startActivity(intent1);

            }
        };
        return options;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dynamicFragment != null) {
            dynamicFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
