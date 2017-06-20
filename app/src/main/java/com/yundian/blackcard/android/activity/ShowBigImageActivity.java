package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.fragment.ShowBigImageFragment;

import java.util.List;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-20 11:26
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ShowBigImageActivity extends BaseActivity {

    @BindView(R.id.vp_image)
    protected ViewPager vp_image;
    @BindView(R.id.tv_index)
    protected TextView tv_index;
    private List<String> images;


    @Override
    public int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.ac_showbigimage;
    }

    @Override
    public void initData() {
        super.initData();
        images = getIntent().getStringArrayListExtra(ActionConstant.IntentKey.IMGS_LIST);
        int position = getIntent().getIntExtra(ActionConstant.IntentKey.IMGS_POSITION, 0);
        vp_image.setAdapter(new ShowBigImagesPageAdapter(getSupportFragmentManager(), images));
        vp_image.setCurrentItem(position);
        refreshIndex(position);

    }

    @Override
    public void initListener() {
        super.initListener();
        vp_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {
                refreshIndex(index);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    private void refreshIndex(int index) {
        if (images != null) {
            int formatIndex = index + 1;
            tv_index.setText("[" + formatIndex + "/" + images.size() + "]");
        }
    }

    public class ShowBigImagesPageAdapter extends FragmentStatePagerAdapter {

        private List<String> images;

        public ShowBigImagesPageAdapter(FragmentManager fm, List<String> images) {
            super(fm);
            this.images = images;
        }

        @Override
        public Fragment getItem(int position) {
            ShowBigImageFragment fragment = new ShowBigImageFragment();
            fragment.setOnViewTapListener(new ShowBigImageFragment.OnViewTapListener() {
                @Override
                public void onViewTap(int index) {
                    Intent intent = new Intent();
                    intent.putExtra(ActionConstant.IntentKey.IMG_BIG_INDEX, index);
                    if (ShowBigImageActivity.this != null && !ShowBigImageActivity.this.isFinishing()) {
                        ShowBigImageActivity.this.setResult(ActionConstant.Action.BIG_IMAGE, intent);
                        ShowBigImageActivity.this.finish();
                        ShowBigImageActivity.this.overridePendingTransition(R.anim.alpha_stay, R.anim.alpha_out);
                    }
                }
            });
            Bundle b = new Bundle();
            b.putString(ActionConstant.IntentKey.IMGS_BIG, images.get(position));
            b.putInt(ActionConstant.IntentKey.IMG_BIG_INDEX, position);
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return images.size();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_stay, R.anim.alpha_out);
    }
}
