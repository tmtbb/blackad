package com.yundian.blackcard.android.activity;

import com.yundian.blackcard.android.R;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 16:13
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeInviteActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.ac_tribe_invite;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("邀请进入部落");
    }
}
