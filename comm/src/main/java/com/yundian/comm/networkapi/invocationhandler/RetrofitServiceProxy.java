package com.yundian.comm.networkapi.invocationhandler;


import com.yundian.comm.annotation.ServiceMethod;
import com.yundian.comm.annotation.ServiceType;
import com.yundian.comm.networkapi.exception.NetworkAPIException;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.obsserver.DefObserver;
import com.yundian.comm.networkapi.okhttp.OkHttpUtils;
import com.yundian.comm.networkapi.response.DefResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaowang on 2017/5/19.
 */
public class RetrofitServiceProxy implements InvocationHandler {

    protected Map<String, Method> serviceMethodMap = new HashMap<String, Method>();
    protected Map<String, Object> serviceClassMap = new HashMap<String, Object>();


    private Class<?> getServiceType(Method method) {
        ServiceType serviceType = method.getAnnotation(ServiceType.class);
        if (serviceType == null) {
            serviceType = method.getDeclaringClass().getAnnotation(ServiceType.class);
        }
        return serviceType != null ? serviceType.value() : null;
    }


    private synchronized Method getServiceMethod(Method method) throws NetworkAPIException {

        Class<?> serviceClass = getServiceType(method);
        if (serviceClass == null) {
            throw new NetworkAPIException(-1, "serviceClass null");
        }

        String methodName = method.getName();
        ServiceMethod serviceMethod = method.getAnnotation(ServiceMethod.class);
        if (serviceMethod != null) {
            methodName = serviceMethod.value();
        }

        String key = serviceClass.getName() + "_" + methodName;
        Method retrofitMethod = serviceMethodMap.get(key);
        if (retrofitMethod == null) {
            Method[] methods = serviceClass.getDeclaredMethods();
            for (Method method1 : methods) {
                String methodName1 = method1.getName();
                ServiceMethod serviceMethod1 = method1.getAnnotation(ServiceMethod.class);
                if (serviceMethod1 != null) {
                    methodName1 = serviceMethod1.value();
                }

                if (methodName1.equals(methodName)) {
                    retrofitMethod = method1;
                    serviceMethodMap.put(key, retrofitMethod);
                    break;
                }
            }
        }
        return retrofitMethod;
    }

    private Object invokeClass(final Method method, final Object[] args) throws Throwable {
        Class<?> cls = getServiceType(method);
        Object service = serviceClassMap.get(cls.getName());
        if (service == null) {
            service = cls.newInstance();
            putService(service);
        }

        if (service == null)
            throw new NetworkAPIException(-1, "service null");

        Method servicemethod = getServiceMethod(method);
        if (servicemethod == null)
            throw new NetworkAPIException(-1, "serviceMethod null");

        return servicemethod.invoke(service, args);

    }

    private void putService(Object service) {
        if (service != null) {
            serviceClassMap.put(service.getClass().getName(), service);
        }
    }

    private Object invokeInterface(final Method method, final Object[] args) throws Throwable {
        final ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.method = method;
        serviceInfo.args = args;
        final Boolean isListener = serviceInfo.method.getReturnType() != Observable.class
                && serviceInfo.args.length > 0
                && serviceInfo.method.getParameterTypes()[serviceInfo.args.length - 1] == OnAPIListener.class;

        Observable observable = Observable.just(serviceInfo)
                .subscribeOn(Schedulers.io())
                .map(new Function<ServiceInfo, ServiceInfo>() {
                    @Override
                    public ServiceInfo apply(ServiceInfo serviceInfo) throws Exception {
                        if (isListener) {
                            Object[] newArgs = new Object[serviceInfo.args.length - 1];
                            for (Integer i = 0; i < serviceInfo.args.length - 1; ++i) {
                                newArgs[i] = serviceInfo.args[i];
                            }
                            serviceInfo.args = newArgs;
                        }
                        serviceInfo.method = getServiceMethod(serviceInfo.method);
                        if (serviceInfo.method == null)
                            throw new NetworkAPIException(-1, "serviceMethod null");
                        return serviceInfo;
                    }
                })
                .flatMap(new Function<ServiceInfo, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(ServiceInfo serviceInfo) throws Exception {
                        Class<?> cls = serviceInfo.method.getDeclaringClass();
                        Object service = serviceClassMap.get(cls.getName());
                        if (service == null) {
                            service = OkHttpUtils.getInstance().create(cls);
                            putService(service);
                        }
                        if (service == null)
                            throw new NetworkAPIException(-1, "service null");
                        Observable obs = (Observable) serviceInfo.method.invoke(service, serviceInfo.args);
                        return obs;
                    }
                });

        if (method.getReturnType() == Observable.class) {
            return observable.map(new Function<DefResponse<?>, Object>() {
                @Override
                public Object apply(final DefResponse<?> defResponse) throws Exception {
                    if (defResponse.getStatus() == DefResponse.SUCCESS_CODE) {
                        return defResponse.getData();
                    }
                    throw new NetworkAPIException(defResponse.getStatus(), defResponse.getMsg());
                }
            });
        } else if ( isListener ) {
            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new DefObserver((OnAPIListener) args[args.length - 1]));
        } else {
            observable.observeOn(AndroidSchedulers.mainThread()).subscribe();
        }
        return null;
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {

        if (getServiceType(method).isInterface()) {
            return invokeInterface(method, args);
        } else {
            return invokeClass(method, args);
        }

    }


    static class ServiceInfo {
        Method method;
        Object[] args;
    }
}