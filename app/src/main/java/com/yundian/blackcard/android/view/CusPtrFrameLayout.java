package com.yundian.blackcard.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/2/6 0006
 * @describe : com.yaowang.ywcommonproject.view
 */
public class CusPtrFrameLayout extends PtrFrameLayout {
    public CusPtrFrameLayout(Context context) {
        super(context);
        initGesture();
    }

    public CusPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGesture();
    }

    public CusPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGesture();
    }

    private GestureDetector detector;
    private boolean mIsDisallowIntercept = false;
    private void initGesture() {
        detector = new GestureDetector(getContext(),gestureListener);
    }

    private boolean mIsHorizontalMode = false;
    private boolean isFirst = true;
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            isFirst = true;
            mIsHorizontalMode = false;
            mIsDisallowIntercept = false;
            return super.dispatchTouchEvent(e);
        }
        if (detector.onTouchEvent(e) && mIsDisallowIntercept && mIsHorizontalMode){
            return dispatchTouchEventSupper(e);
        }
        if (mIsHorizontalMode) {
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                if (isFirst) {
                    mIsHorizontalMode = true;
                    isFirst = false;
                }
            } else {
                if (isFirst) {
                    mIsHorizontalMode = false;
                    isFirst = false;
                }
                return false;//垂直滑动会返回false
            }
            return true;//水平滑动会返回true
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };
}
