package com.yundian.blackcard.android.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by yaowang on 2017/5/11.
 */

public class PrivilegeInfo implements Serializable {
    /**
     * privilegeIcon : res://icon_p10001
     * privilegePowers : {"10001":1,"10002":1,"10003":1}
     * privilegeImgurl : res://img_p10001
     * privilegeName : 24小时管家
     * privilegeId : 10001
     * privilegeDescribe : 真人管家全天为您一对一服务，生活琐事无需烦恼，为您打造贵族般的享受。
     */
    public static  String  blackCardId;


    private String privilegeIcon;
    private String privilegeImgurl;
    private String privilegeName;
    private Integer privilegeId;
    private String privilegeDescribe;
    private Map<String, Integer> privilegePowers;


    public String getPrivilegeIcon() {
        return privilegeIcon;
    }

    public void setPrivilegeIcon(String privilegeIcon) {
        this.privilegeIcon = privilegeIcon;
    }


    public String getPrivilegeImgurl() {
        return privilegeImgurl;
    }

    public void setPrivilegeImgurl(String privilegeImgurl) {
        this.privilegeImgurl = privilegeImgurl;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeDescribe() {
        return privilegeDescribe;
    }

    public void setPrivilegeDescribe(String privilegeDescribe) {
        this.privilegeDescribe = privilegeDescribe;
    }

    public Map<String, Integer> getPrivilegePowers() {
        return privilegePowers;
    }

    public PrivilegeInfo setPrivilegePowers(Map<String, Integer> privilegePowers) {
        this.privilegePowers = privilegePowers;
        return this;
    }

}
