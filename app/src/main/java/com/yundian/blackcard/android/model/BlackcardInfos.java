package com.yundian.blackcard.android.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaowang on 2017/5/11.
 */

public class BlackcardInfos {
    private List<BlackcardInfo> blackcards;
    private List<PrivilegeInfo> privileges;
    private Double customNamePrice;

    public List<BlackcardInfo> getBlackcards() {
        return blackcards;
    }

    public BlackcardInfos setBlackcards(List<BlackcardInfo> blackcards) {
        this.blackcards = blackcards;
        return this;
    }

    public List<PrivilegeInfo> getPrivileges() {
        return privileges;
    }

    public BlackcardInfos setPrivileges(List<PrivilegeInfo> privileges) {
        this.privileges = privileges;
        return this;
    }

    public Double getCustomNamePrice() {
        return customNamePrice;
    }

    public BlackcardInfos setCustomNamePrice(Double customNamePrice) {
        this.customNamePrice = customNamePrice;
        return this;
    }


    public List<List<PrivilegeInfo>> privilegesSpit(Integer integer) {
        List<List<PrivilegeInfo>> lists = new ArrayList<List<PrivilegeInfo>>();
        for(Integer i = 0 ; i < privileges.size() ; i += integer) {
            List<PrivilegeInfo> list = new ArrayList<PrivilegeInfo>();
            if( i + integer  <= privileges.size() ) {
                list.addAll(privileges.subList(i,i + integer));
            }
            else {
                list.addAll(privileges.subList(i,privileges.size()));
            }
            lists.add(list);
        }
        return lists;
    }
}
