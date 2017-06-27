package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.model.DynamicLikeModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.okhttp.DynamicService;
import com.yundian.comm.annotation.ServiceMethod;
import com.yundian.comm.annotation.ServiceType;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-16 13:49
 * @description : none
 * @for your attention : none
 * @revise : none
 */

@ServiceType(DynamicService.class)
public interface IDynamicService {
    /**
     * 发布
     *
     * @param circleId
     * @param message
     * @param file
     * @param listener
     */
    @ServiceMethod("dynamicAdd")
    void dynamicAdd(String circleId, String message, List<String> file, OnAPIListener<DynamicModel> listener);


    /**
     * 列表
     *
     * @param page
     * @param circleId
     * @param listener
     */
    @ServiceMethod("dynamicList")
    void dynamicList(int page, String circleId, OnAPIListener<List<DynamicModel>> listener);


    /**
     * 删除
     *
     * @param circleMessageId
     * @param listener
     */
    @ServiceMethod("dynamicDelete")
    void dynamicDelete(String circleMessageId, OnAPIListener<Object> listener);


    /**
     * 点赞
     *
     * @param circleMessageId
     * @param listener
     */
    @ServiceMethod("likeAdd")
    void likeAdd(String circleMessageId, OnAPIListener<Object> listener);

    /**
     * 取消
     *
     * @param circleMessageId
     * @param listener
     */
    @ServiceMethod("likeDelete")
    void likeDelete(String circleMessageId, OnAPIListener<Object> listener);


    /**
     * 点赞列表
     *
     * @param page
     * @param circleMessageId
     * @param listener
     */
    @ServiceMethod("likeList")
    void likeList(int page, String circleMessageId, OnAPIListener<List<DynamicLikeModel>> listener);

    /**
     * 评论
     *
     * @param circleMessageId
     * @param comment
     * @param listener
     */
    @ServiceMethod("commentAdd")
    void commentAdd(String circleMessageId, String comment, OnAPIListener<DynamicCommentModel> listener);

    /**
     * 评论列表
     *
     * @param page
     * @param circleMessageId
     * @param listener
     */
    @ServiceMethod("commentList")
    void commentList(int page, String circleMessageId, OnAPIListener<List<DynamicCommentModel>> listener);


    /**
     * 举报
     *
     * @param circleMessageId
     * @param listener
     */
    @ServiceMethod("dynamicReport")
    void dynamicReport(String circleMessageId, OnAPIListener<Object> listener);


    /**
     * 评论删除
     * @param commentId
     * @param listener
     */
    @ServiceMethod("commentDelete")
    void commentDelete(String commentId,OnAPIListener<Object> listener);
}
