package com.yundian.blackcard.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yundian.blackcard.android.R;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-11-08 11:27
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DimImageView extends AppCompatImageView {
    public DimImageView(Context context) {
        super(context);
        init(null);
    }

    public DimImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DimImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray array = getContext().obtainStyledAttributes(attributeSet, R.styleable.DimImageView);
            int i = array.getColor(0, ContextCompat.getColor(getContext(), R.color.color_dim));
            array.recycle();
            setColorFilter(i);
        }

    }

}
