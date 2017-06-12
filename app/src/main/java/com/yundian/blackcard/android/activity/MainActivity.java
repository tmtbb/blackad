package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.fragment.HomeFragment;
import com.yundian.blackcard.android.fragment.MyFragment;
import com.yundian.blackcard.android.model.UpdateInfo;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.updater.AppUpdater;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.DeviceUtils;

import butterknife.BindView;


public class MainActivity extends BaseActivity {


    @BindView(R.id.navigation)
    protected BottomNavigationView navigationView;
    private Fragment[] fragments = new Fragment[2];

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case com.yundian.blackcard.android.R.id.navigation_home: {
                    showFragment(0);
                    return true;
                }
                case com.yundian.blackcard.android.R.id.navigation_dashboard:

                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ServiceMessageActivity.class);
                    UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra(UserInfo.class.getName());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(UserInfo.class.getName(), userInfo);
                    intent.putExtras(bundle);
                    intent.putExtra("title", "管家");
                    startActivity(intent);
                    break;

                case com.yundian.blackcard.android.R.id.navigation_notifications: {
                    showFragment(1);
                    return true;
                }
            }
            return false;
        }

    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        showFragment(0);
    }

    private void showFragment(Integer index) {
        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new HomeFragment();
                    break;
                case 1:
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
                if (updateInfo.getIsUpdate() == 1) {
                    new AppUpdater.Builder(context)
                            .url(updateInfo.getUrl())
                            .title("版本更新啦")
                            .content(updateInfo.getDescription())
                            .app(getString(R.string.app_name))
                            .description(getString(R.string.app_name))
                            .force(updateInfo.getIsForce() == 1)
                            .build()
                            .update();
                }
            }
        });
    }

}
