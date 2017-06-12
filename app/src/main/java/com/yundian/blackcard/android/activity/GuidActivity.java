package com.yundian.blackcard.android.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.yundian.blackcard.android.R;
import com.yundian.comm.util.DeviceUtils;
import com.yundian.comm.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-11 20:32
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class GuidActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    protected ViewPager viewPager;
    private List<View> views;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_guid;
    }

    @Override
    public void initView() {
        super.initView();
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.ly_guidview_1, null));
        views.add(inflater.inflate(R.layout.ly_guidview_2, null));
        views.add(inflater.inflate(R.layout.ly_guidview_3, null));
        viewPager.setAdapter(new GuidAdapter());
        viewPager.setCurrentItem(0);
    }

    @Override
    public void initListener() {
        super.initListener();
        for (int i = 0; i < views.size(); i++) {
            views.get(i).findViewById(R.id.skipText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next();
                }
            });
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void next() {
        SPUtils.put(context, GuidActivity.class.getSimpleName(), DeviceUtils.getVersionName(context));
        next(LoginActivity.class);
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    class GuidAdapter extends PagerAdapter {
        public GuidAdapter() {
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }


}
