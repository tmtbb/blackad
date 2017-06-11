package com.yundian.comm.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yaowang on 2017/4/4.
 */
public class StringUtils {
    public static String randomUUID() {
        String uuid = java.util.UUID.randomUUID().toString();
        uuid = uuid.replace("-","");
        uuid = uuid.toUpperCase();
        return  uuid;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String md5(String str) {
        if (StringUtils.isEmpty(str)) {
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

    public static String replaceInfo(int count, String info) {
        if (TextUtils.isEmpty(info))
            return info;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(info.substring(0, count));
        for (int i = 0; i < info.length() - count; i++) {
            stringBuffer.append("*");

        }
        return stringBuffer.toString();

    }


}
