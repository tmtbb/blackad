package com.yundian.comm.networkapi.sign;

import java.util.TreeMap;

/**
 * Created by yaowang on 2017/5/3.
 */

public interface SignCalculate {
    String calculate(String method, String url, TreeMap<String,Object> parameter);
    String parameterKey();
}
