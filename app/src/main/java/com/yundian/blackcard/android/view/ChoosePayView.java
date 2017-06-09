package com.yundian.blackcard.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.fragment.BaseFragment;
import com.yundian.blackcard.android.model.ButlerserviceInfo;
import com.yundian.comm.ui.view.BaseDataFrameLayout;
import com.yundian.comm.ui.view.BaseFrameLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-09 15:10
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ChoosePayView extends BaseDataFrameLayout<ButlerserviceInfo> {

    @BindView(R.id.priceText)
    protected TextView priceText;

    @BindView(R.id.purseAmountText)
    protected TextView purseAmountText;

    public ChoosePayView(Context context) {
        super(context);
    }

    public ChoosePayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(ButlerserviceInfo data) {
        if (data != null) {
            priceText.setText("¥" + data.getServiceAmount());
            purseAmountText.setText("当前可用余额 ¥");
        }

    }

    @Override
    protected int layoutId() {
        return R.layout.ly_choose_pay;
    }

    @OnClick(value = {R.id.closeIcon, R.id.purseLayout, R.id.aliPayLayout, R.id.weixinPayLayout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeIcon:
                onChildViewClick(view, ActionConstant.Action.PAY_CLOSE);
                break;
            case R.id.purseLayout:
                onChildViewClick(view, ActionConstant.Action.PAY_PURSE);
                break;
            case R.id.aliPayLayout:
                onChildViewClick(view, ActionConstant.Action.PAY_ALI);
                break;
            case R.id.weixinPayLayout:
                onChildViewClick(view, ActionConstant.Action.PAY_WEIXIN);
                break;
        }
    }
}
