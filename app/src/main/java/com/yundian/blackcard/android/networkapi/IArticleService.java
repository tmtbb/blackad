package com.yundian.blackcard.android.networkapi;

import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.networkapi.okhttp.ArticleService;
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
@ServiceType(ArticleService.class)
public interface IArticleService {

    /**
     * 文章列表
     *
     * @param page
     * @param categoryId
     * @param listener
     */

    @ServiceMethod("articleList")
    void articleList(int page, String categoryId, OnAPIListener<List<ArticleModel>> listener);

    /**
     * 文章评论列表
     *
     * @param page
     * @param articleId
     * @param listener
     */
    @ServiceMethod("commentList")
    void commentList(int page, String articleId, OnAPIListener<List<DynamicCommentModel>> listener);


    /**
     * 文章详情页
     *
     * @param articleId
     * @param listener
     */
    void articleDetail(String articleId, OnAPIListener<ArticleModel> listener);


    /**
     * 文章评论
     *
     * @param articleId
     * @param comment
     * @param listener
     */
    void articleComment(String articleId, String comment, OnAPIListener<DynamicCommentModel> listener);

}
