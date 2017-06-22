package com.yundian.blackcard.android.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 10:58
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeModel extends BaseModel {


    private List<TribeListModel> userTribes = new ArrayList<>();
    private List<TribeListModel> recommendTribes = new ArrayList<>();
    private CreatorTribeModel ownTribe;


    public List<TribeListModel> getUserTribes() {
        return userTribes;
    }

    public void setUserTribes(List<TribeListModel> userTribes) {
        this.userTribes = userTribes;
    }

    public List<TribeListModel> getRecommendTribes() {
        return recommendTribes;
    }

    public void setRecommendTribes(List<TribeListModel> recommendTribes) {
        this.recommendTribes = recommendTribes;
    }


    public CreatorTribeModel getOwnTribe() {
        return ownTribe;
    }

    public TribeModel setOwnTribe(CreatorTribeModel ownTribe) {
        this.ownTribe = ownTribe;
        return this;
    }
}
