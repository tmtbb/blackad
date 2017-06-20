package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

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

    @BindView(R.id.viewPager)
    protected ViewPager viewPager;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"卡友部落", "精英生活", "邀请函"};

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
        fragmentList.add(new DynamicListFragment());
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
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
        ActivityUtil.nextDynamicAdd(getActivity());
    }
}
