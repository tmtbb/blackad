package com.yundian.blackcard.android.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yundian.comm.listener.InitPageListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaowang on 2017/5/13.
 */

public abstract class BaseDialogFragment extends DialogFragment implements InitPageListener  {


    protected View rootView;
    protected Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder =  ButterKnife.bind(this, rootView);
        initView();
        initListener();
        initData();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    public void show(FragmentManager manager) {
        super.show(manager, this.getClass().getName());
    }
}
