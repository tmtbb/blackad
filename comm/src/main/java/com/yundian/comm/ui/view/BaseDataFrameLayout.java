package com.yundian.comm.ui.view;

import android.content.Context;
import android.util.AttributeSet;

public abstract class BaseDataFrameLayout<T> extends BaseFrameLayout {
    public BaseDataFrameLayout(Context context) {
        super(context);
    }

    public BaseDataFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void update(T data);
}
