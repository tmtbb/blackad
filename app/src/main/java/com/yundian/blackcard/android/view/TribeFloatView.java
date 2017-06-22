package com.yundian.blackcard.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.TribeModel;
import com.yundian.comm.ui.view.BaseDataFrameLayout;
import com.yundian.comm.ui.view.BaseFrameLayout;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 15:16
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeFloatView extends BaseDataFrameLayout<TribeModel> {

    @BindView(R.id.contentText)
    protected TextView contentText;
    @BindView(R.id.rootLayout)
    protected View rootLayout;
    @BindView(R.id.dotView)
    protected View dotView;
    private static final String TRIBE_ADD = "创建部落";
    private static final String TRIBE_INVITE = "申请进入";
    private static final String TRIBE_APPLYING = "正在审核";

    public TribeFloatView(Context context) {
        super(context);
    }

    public TribeFloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(TribeModel tribeModel) {
        setVisibility(GONE);
        dotView.setVisibility(GONE);
        if (tribeModel.getOwnTribe().getStatus() == 0) {
            contentText.setText(TRIBE_ADD);
            setVisibility(View.VISIBLE);
        }
        else if (tribeModel.getOwnTribe().getStatus() == 1) {
            contentText.setText(TRIBE_APPLYING);
            setVisibility(View.VISIBLE);
        }
        else if (tribeModel.getOwnTribe().getStatus() == 2 ) {
            contentText.setText(TRIBE_INVITE);
            setVisibility(View.VISIBLE);
        }
        if(tribeModel.getOwnTribe().getVerifyNum() != 0){
            dotView.setVisibility(VISIBLE);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_tribe_float;
    }

    public String getContent() {
        return contentText.getText().toString().trim();
    }

    @Override
    protected void initListener() {
        super.initListener();
        rootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildViewClick(v, ActionConstant.Action.TRIBE_FLOAT);
            }
        });
    }
}
