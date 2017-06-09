package com.yundian.blackcard.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.comm.util.DisplayUtil;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-08 14:48
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class SpaceView extends View {
    public SpaceView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getSuggestedMinimumWidth(), DisplayUtil.dip2px(20, getContext()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(getResources().getColor(R.color.color_f8f8f8));
    }
}
