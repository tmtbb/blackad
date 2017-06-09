package com.yundian.blackcard.android.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.EmptyViewEntity;
import com.yundian.comm.ui.view.BaseDataFrameLayout;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-04-13 11:48
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DefaultEmptyView extends BaseDataFrameLayout<EmptyViewEntity> {

    @BindView(R.id.emptyIcon)
    protected ImageView emptyIcon;
    @BindView(R.id.emptyContent)
    protected TextView emptyContent;
    @BindView(R.id.emptyButton)
    protected Button emptyButton;

    public DefaultEmptyView(Context context) {
        super(context);
    }

    public DefaultEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(EmptyViewEntity data) {
        if (data != null) {

            if (!TextUtils.isEmpty(data.getButtonText())) {
                setEmptyButtonText(data.getButtonText());
            }

            if (!TextUtils.isEmpty(data.getContentText())) {
                setEmptyContent(data.getContentText());
            }

            setEmptyIcon(data.getIcon());
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.president_ly_defaultemptyview;
    }

    @Override
    protected void initListener() {
        super.initListener();
        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildViewClick(v, ActionConstant.Action.EMPTY_BUTTON);
            }
        });
    }

    protected void setEmptyIcon(int resId) {
        if (resId != 0) {
            emptyIcon.setVisibility(VISIBLE);
            emptyIcon.setImageResource(resId);
        } else {
            emptyIcon.setVisibility(INVISIBLE);
        }
    }

    public void setEmptyContent(String content) {
        if (emptyContent != null) {
            emptyContent.setText(content);
        }
    }

    public void setEmptyContent(int resId) {
        setEmptyContent(getContext().getString(resId));
    }

    public void setEmptyButtonText(String text) {
        if (emptyButton != null) {
            emptyButton.setVisibility(VISIBLE);
            emptyButton.setText(text);
        }
    }

    public void setEmptyButtonText(int resId) {
        setEmptyButtonText(getContext().getString(resId));
    }
}
