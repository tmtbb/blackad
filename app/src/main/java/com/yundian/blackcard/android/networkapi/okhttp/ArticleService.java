package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.networkapi.IArticleService;
import com.yundian.comm.annotation.ServiceMethod;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.obsserver.DefObserver;
import com.yundian.comm.networkapi.okhttp.OkHttpService;
import com.yundian.comm.networkapi.response.DefResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-20 09:55
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ArticleService extends OkHttpService<ArticleService.RetrofitArticleService> implements IArticleService {


    interface RetrofitArticleService {

        @POST("/api/article/list.json")
        @FormUrlEncoded
        @ServiceMethod("articleList")
        Observable<DefResponse<List<ArticleModel>>> articleList(@Field("page") int page, @Field("categoryId") String categoryId);

        @POST("/api/article/comment/list.json")
        @FormUrlEncoded
        @ServiceMethod("commentList")
        Observable<DefResponse<List<DynamicCommentModel>>> commentList(@Field("page") int page, @Field("articleId") String articleId);


        @POST("/api/article/info.json")
        @FormUrlEncoded
        @ServiceMethod("articleDetail")
        Observable<DefResponse<ArticleModel>> articleDetail(@Field("articleId") String articleId);

        @POST("/api/article/comment/add.json")
        @FormUrlEncoded
        @ServiceMethod("articleComment")
        Observable<DefResponse<DynamicCommentModel>> articleComment(@Field("articleId") String articleId, @Field("comment") String comment);

    }

    @Override
    public void articleList(int page, String categoryId, OnAPIListener<List<ArticleModel>> listener) {
        setSubscribe(service.articleList(page, categoryId), new DefObserver<>(listener));
    }

    @Override
    public void commentList(int page, String articleId, OnAPIListener<List<DynamicCommentModel>> listener) {
        setSubscribe(service.commentList(page, articleId), new DefObserver<>(listener));
    }

    @Override
    public void articleDetail(String articleId, OnAPIListener<ArticleModel> listener) {
        setSubscribe(service.articleDetail(articleId), new DefObserver<>(listener));
    }

    @Override
    public void articleComment(String articleId, String comment, OnAPIListener<DynamicCommentModel> listener) {
        setSubscribe(service.articleComment(articleId, comment), new DefObserver<>(listener));
    }


}
