package com.yundian.comm.networkapi.okhttp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.yundian.comm.networkapi.obsserver.BaseObserver;

/**
 * Created by yaowang on 2017/5/4.
 */

public abstract class OkHttpService<T> {
    protected T service;


    public OkHttpService() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        service = OkHttpUtils.getInstance().create((Class<T>) params[0]);
    }


    public void  setSubscribe(Observable observable, BaseObserver<?> baseObserver, boolean isObserveOnMainThread) {

        Observable observable1 = observable.subscribeOn(Schedulers.io());
        if( isObserveOnMainThread ) {
            observable1 = observable1.observeOn(AndroidSchedulers.mainThread());
        }
        observable1.subscribe(baseObserver);
    }

    public void  setSubscribe(Observable observable, BaseObserver<?> baseObserver) {
       setSubscribe(observable,baseObserver,true);
    }

}
