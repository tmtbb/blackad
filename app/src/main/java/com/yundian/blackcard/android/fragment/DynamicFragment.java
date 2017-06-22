package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.DynamicAddActivity;
import com.yundian.blackcard.android.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-16 16:05
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicFragment extends BaseFragment {


    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;
    @BindView(R.id.iconCamera)
    protected ImageView iconCamera;

    @BindView(R.id.viewPager)
    protected ViewPager viewPager;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"精英生活", "邀请函", "卡友部落"};

    private DynamicListFragment dynamicListFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fm_dynamic;
    }

    @Override
    public void initView() {
        super.initView();
        fragmentList.clear();
        fragmentList.add(dynamicListFragment = new DynamicListFragment());
        fragmentList.add(new ArticleFragment());
        fragmentList.add(new TribeFragment());
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initListener() {
        super.initListener();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    iconCamera.setImageResource(R.mipmap.icon_camera);
                } else if (position == 2) {
                    iconCamera.setImageResource(R.mipmap.icon_tribe_add);
                }
                iconCamera.setVisibility(position == 0 || position == 2 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {


        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dynamicListFragment != null) {
            dynamicListFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.iconCamera)
    protected void onClick(View view) {
        if (viewPager.getCurrentItem() == 0) {
            ActivityUtil.nextDynamicAdd(getActivity(),"0");
        } else if (viewPager.getCurrentItem() == 2) {
            showToast("敬请期待");
        }

    }
}
