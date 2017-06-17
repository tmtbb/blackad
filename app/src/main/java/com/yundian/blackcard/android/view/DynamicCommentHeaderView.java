package com.yundian.blackcard.android.view;

import android.content.Context;
import android.util.AttributeSet;

import com.yundian.blackcard.android.R;
import com.yundian.comm.ui.view.BaseFrameLayout;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-17 23:05
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicCommentHeaderView extends BaseFrameLayout {
    public DynamicCommentHeaderView(Context context) {
        super(context);
    }

    public DynamicCommentHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_dynamic_comment_header;
    }
}
