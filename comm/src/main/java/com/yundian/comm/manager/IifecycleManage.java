package com.yundian.comm.manager;


import com.yundian.comm.listener.Iifecycle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaowang on 16/3/31.
 */
public class IifecycleManage implements Iifecycle {
    private Map<String,Iifecycle> iifecycleMap;

    public IifecycleManage() {
        iifecycleMap = new HashMap<String,Iifecycle>();
    }


    public void register(String key, Iifecycle iifecycle) {
        iifecycleMap.put(key,iifecycle);
    }

    public void unregister(String key) {
        iifecycleMap.remove(key);
    }

    @Override
    public void initView() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initView();
        }
    }

    @Override
    public void initListener() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initListener();
        }
    }

    @Override
    public void initData() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initData();
        }
    }

    @Override
    public void onStart() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onStart();
        }
    }

    @Override
    public void onStop() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onStop();
        }
    }

    @Override
    public void onResume() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onResume();
        }
    }

    @Override
    public void onPause() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onPause();
        }
    }

    @Override
    public void onDestroy() {
        for (Map.Entry<String, Iifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onDestroy();
        }

    }

    public Iifecycle get(String key) {
        return iifecycleMap.get(key);
    }
}
