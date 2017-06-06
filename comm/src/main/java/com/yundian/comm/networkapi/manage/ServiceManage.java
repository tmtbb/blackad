package com.yundian.comm.networkapi.manage;

import android.content.Context;


import com.yundian.comm.annotation.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Created by yaowang on 2017/5/16.
 */

public class ServiceManage {


    protected Map<String, Class<?>> serviceClassMap = new HashMap<String, Class<?>>();
    protected Map<String, Object> serviceMap = new HashMap<String, Object>();
    protected InvocationHandler invocationHandler;

    protected ServiceManage(InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }

    protected ServiceManage() {

    }

    public <T> T getService(String serviceName) {
        T service = (T) serviceMap.get(serviceName);
        if (service == null) {
            Class<?> classType = serviceClassMap.get(serviceName);
            if (classType != null) {
                try {
                    service = (T) classType.newInstance();
                    serviceMap.put(serviceName, service);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return service;
    }


    public <T> T getService(Class<T> tClass) {
        T service = (T) serviceMap.get(tClass.getName());
        if (service == null) {
            service = (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, invocationHandler);
            if( service != null ) {
                serviceMap.put(tClass.getName(),service);
            }
        }
        return service;
    }


    public void scanService(Context ctx, String entityPackage) {
        try {
            PathClassLoader classLoader = (PathClassLoader) Thread
                    .currentThread().getContextClassLoader();

            DexFile dex = new DexFile(ctx.getPackageResourcePath());
            Enumeration<String> entries = dex.entries();
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement();
                if (entryName.contains(entityPackage)) {
                    Class<?> entryClass = Class.forName(entryName, true,classLoader);
                    Service annotation = entryClass.getAnnotation(Service.class);
                    if (annotation != null) {
                        serviceClassMap.put(annotation.value(),entryClass);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String serviceName) {
        serviceMap.remove(serviceName);
    }
}
