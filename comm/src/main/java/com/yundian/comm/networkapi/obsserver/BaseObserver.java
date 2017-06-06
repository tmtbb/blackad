package com.yundian.comm.networkapi.obsserver;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaowang on 2017/5/3.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T value) {
        onSuccess(value);
    }

    protected abstract void onSuccess(T t);
}
