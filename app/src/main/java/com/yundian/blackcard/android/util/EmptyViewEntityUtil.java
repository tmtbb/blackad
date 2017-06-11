package com.yundian.blackcard.android.util;


import com.yundian.blackcard.android.model.EmptyViewEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-04-14 11:26
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class EmptyViewEntityUtil {
    public static EmptyViewEntityUtil getInstance() {
        return HolderClass.sInstance;
    }

    private static class HolderClass {
        private static final EmptyViewEntityUtil sInstance = new EmptyViewEntityUtil();
    }

    private List<EmptyViewEntity> emptyViewEntityList = new ArrayList<EmptyViewEntity>();


    public List<EmptyViewEntity> getDefaultEmptyList() {
        addEntity("暂无数据，下拉试试~~");
        return emptyViewEntityList;
    }

    /**
     * 收藏、评论、点赞
     *
     * @return
     */
    public List<EmptyViewEntity> getCollectionWithCommentOfPraise() {
        addEntity("赶紧去撩妹子呀");
        return emptyViewEntityList;
    }

    /**
     * 充值记录
     *
     * @return
     */
    public List<EmptyViewEntity> getPayRecordList() {
        addEntity("这游戏不充钱能玩？");
        return emptyViewEntityList;
    }

    /**
     * 我的游戏、礼包
     *
     * @return
     */
    public List<EmptyViewEntity> getUserGameWithGift() {
        addEntity("居然什么都没有？");
        return emptyViewEntityList;
    }

    /**
     * 我的好友、关注、粉丝
     *
     * @return
     */
    public List<EmptyViewEntity> getUserFansWithKeep() {
        addEntity("我猜你肯定是个单身汪？", "推荐几个");
        return emptyViewEntityList;
    }

    public List<EmptyViewEntity> getUserFansWithKeep_0() {
        addEntity("我猜你肯定是个单身汪？");
        return emptyViewEntityList;
    }

    /**
     * 公会游戏、礼包、充值
     *
     * @return
     */
    public List<EmptyViewEntity> getSociatyGameWithGift() {
        addEntity("你的会长真是太懒了，赶紧去催催他吧");
        return emptyViewEntityList;
    }


    /**
     * 我的动态
     *
     * @return
     */
    public List<EmptyViewEntity> getUserDynamicList() {
        addEntity("发点什么才能勾搭到妹子呀", "发一条");
        return emptyViewEntityList;
    }


    /**
     * 新游预约
     *
     * @return
     */
    public List<EmptyViewEntity> getNewGameBookEmptyList() {
        addEntity("您还没有预约新的游戏哦~");
        return emptyViewEntityList;
    }

    public List<EmptyViewEntity> getMyProfit() {
        addEntity("您还没有收益哦~");
        return emptyViewEntityList;
    }


    public void addEntity(String contentText) {
        emptyViewEntityList.clear();
        EmptyViewEntity entity = new EmptyViewEntity();
        entity.setContentText(contentText);
        emptyViewEntityList.add(entity);
    }

    public void addEntity(String contentText, String buttonText) {
        emptyViewEntityList.clear();
        EmptyViewEntity entity = new EmptyViewEntity();
        entity.setContentText(contentText);
        entity.setButtonText(buttonText);
        emptyViewEntityList.add(entity);
    }
}
