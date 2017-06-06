package com.yundian.comm.networkapi.obsserver;

import io.reactivex.disposables.Disposable;
import com.yundian.comm.networkapi.exception.NetworkAPIException;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.response.DefResponse;

/**
 * Created by yaowang on 2017/5/3.
 */

public  class DefObserver<T> extends BaseObserver<DefResponse<T>> {

    protected OnAPIListener<T> listener;

    public DefObserver(OnAPIListener<T> listener) {
        this.listener = listener;
    }



    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    protected void onSuccess(DefResponse<T> response) {
        if (response.getStatus() == DefResponse.SUCCESS_CODE) {
            T t = response.getData();
            if( listener != null ) {
                listener.onSuccess(t);
            }
        } else {
            onError(new NetworkAPIException(response.getStatus(),response.getMsg()));
        }
    }


    @Override
    public void onError(Throwable e) {
        if( listener != null ) {
            listener.onError(e);
        }
    }

    @Override
    public void onComplete() {

    }
}
