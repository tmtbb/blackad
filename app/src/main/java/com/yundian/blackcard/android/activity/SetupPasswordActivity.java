package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.view.UserSetInfoCell;
import com.yundian.comm.listener.OnChildViewClickListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-08 09:55
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class SetupPasswordActivity extends BaseActivity {

    @BindView(R.id.updatePayPasswordCell)
    protected UserSetInfoCell updatePayPasswordCell;
    @BindView(R.id.updateCardPasswordCell)
    protected UserSetInfoCell updateCardPasswordCell;
    private String phoneNum = "";

    @Override
    public int getLayoutId() {
        return R.layout.ac_setup_password;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("密码设置");
        updatePayPasswordCell.setLineViewVisibility(View.GONE);
        phoneNum = getIntent().getStringExtra(ActionConstant.IntentKey.PHONE);
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initListener() {
        super.initListener();
        updatePayPasswordCell.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                Intent intent = new Intent(context, SetupPayPasswordActivity.class);
                intent.putExtra(ActionConstant.IntentKey.PHONE, phoneNum);
                startActivity(intent);
            }
        });
        updateCardPasswordCell.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                next(SetupCardPasswordActivity.class);
            }
        });
    }
}
