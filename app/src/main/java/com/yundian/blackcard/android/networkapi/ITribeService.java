package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.TribeAddModel;
import com.yundian.blackcard.android.model.TribeInfosModel;
import com.yundian.blackcard.android.model.TribeMemberModel;
import com.yundian.blackcard.android.model.TribeModel;
import com.yundian.blackcard.android.networkapi.okhttp.TribeService;
import com.yundian.comm.annotation.ServiceMethod;
import com.yundian.comm.annotation.ServiceType;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-20 09:55
 * @description : none
 * @for your attention : none
 * @revise : none
 */
@ServiceType(TribeService.class)
public interface ITribeService {


    /**
     * 部落首页
     *
     * @param listener
     */
    @ServiceMethod("tribeIndex")
    void tribeIndex(OnAPIListener<TribeModel> listener);


    /**
     * 成员列表
     *
     * @param page
     * @param tribeId
     * @param listener
     */
    @ServiceMethod("memberList")
    void memberList(int page, String tribeId, OnAPIListener<List<TribeMemberModel>> listener);

    /**
     * 创建部落
     *
     * @param tribeAddModel
     * @param listener
     */
    @ServiceMethod("tribeAdd")
    void tribeAdd(TribeAddModel tribeAddModel, OnAPIListener<Object> listener);


    /**
     * 简介
     *
     * @param tribeId
     * @param listener
     */
    @ServiceMethod("tribeInfo")
    void tribeInfo(String tribeId, OnAPIListener<TribeInfosModel> listener);


    /**
     * 添加成员
     *
     * @param tribeId
     * @param listener
     */
    @ServiceMethod("memberAdd")
    void memberAdd(String tribeId, OnAPIListener<Object> listener);


    /**
     * 删除成员
     *
     * @param tribeId
     * @param listener
     */
    @ServiceMethod("memberDelete")
    void memberDelete(String tribeId, OnAPIListener<Object> listener);

    /**
     * 审核成员
     *
     * @param tribeMemberId
     * @param type
     * @param listener
     */
    @ServiceMethod("memberVerify")
    void memberVerify(String tribeMemberId, int type, OnAPIListener<Object> listener);

}
