package com.yundian.blackcard.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yundian.comm.listener.InitPageListener;
import com.yundian.comm.listener.OnErrorListener;
import com.yundian.comm.util.LogUtils;
import com.yundian.comm.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaowang on 2017/5/10.
 */

public abstract class BaseFragment extends Fragment implements InitPageListener , OnErrorListener {

    protected View rootView;
    protected Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
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
}
