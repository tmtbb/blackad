package com.yundian.comm.networkapi.sign;

//import com.yundian.comm.util.LogUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yaowang on 2017/5/4.
 */

public  class DefSignCalculateImpl implements SignCalculate {
    private String privateKey;

    public DefSignCalculateImpl(String privateKey) {
        this.privateKey = privateKey;
    }

    public boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public String md5(String str) {
        if (isEmpty(str)) {
            return "";
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            return md5StrBuff.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String calculate(String method, String url, TreeMap<String, Object> parameter) {

//        LogUtils.d("sign",method + " " + url + " " + parameter.toString() );
        StringBuilder builder = new StringBuilder();
        builder.append(method);
        builder.append(url);
        Iterator iter = parameter.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if (isNotEmpty(val.toString())) {
                builder.append(key).append("=").append(val);
            }
        }
        builder.append(privateKey);
        String sign = md5(builder.toString()).toUpperCase();
//        LogUtils.d("sign",builder.toString() + " " + sign);

        return sign;
    }

    @Override
    public String parameterKey() {
        return "sign";
    }
}
