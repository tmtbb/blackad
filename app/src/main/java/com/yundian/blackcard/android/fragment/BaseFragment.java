package com.yundian.blackcard.android.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yundian.comm.listener.InitPageListener;
import com.yundian.comm.listener.OnErrorListener;
import com.yundian.comm.util.LogUtils;
import com.yundian.comm.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaowang on 2017/5/10.
 */

public abstract class BaseFragment extends Fragment implements InitPageListener, OnErrorListener {

    protected View rootView;
    protected Unbinder unbinder;
    protected Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);

        onInit();
        initView();
        initListener();
        initData();
        return rootView;
    }


    protected void onShowError(Throwable ex) {
        if (!this.getActivity().isFinishing()) {
            ToastUtils.show(getActivity(), ex.getLocalizedMessage());
        }
        LogUtils.showException(ex);
    }

    @Override
    public void onError(Throwable ex) {
        onShowError(ex);
    }

    protected void onInit() {
        unbinder = ButterKnife.bind(this, rootView);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void next(Class clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    protected void showToast(CharSequence toast) {
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }
}
