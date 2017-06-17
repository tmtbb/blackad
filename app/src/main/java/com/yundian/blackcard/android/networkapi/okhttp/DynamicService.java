package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.model.DynamicLikeModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.IDynamicService;
import com.yundian.comm.annotation.ServiceMethod;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.networkapi.obsserver.DefObserver;
import com.yundian.comm.networkapi.okhttp.OkHttpService;
import com.yundian.comm.networkapi.response.DefResponse;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-16 14:09
 * @description : none
 * @for your attention : none
 * @revise : none
 */

public class DynamicService extends OkHttpService<DynamicService.RetrofitDynamicService> implements IDynamicService {

    interface RetrofitDynamicService {

        @POST("/api/circle/message/add.json")
        @Multipart
        @ServiceMethod("dynamicAdd")
        Observable<DefResponse<DynamicModel>> dynamicAdd(@Query("circleId") String circleId, @Query("message") String message, @PartMap Map<String, RequestBody> file);

        @POST("/api/circle/message/add.json")
        @FormUrlEncoded
        @ServiceMethod("dynamicAdd2")
        Observable<DefResponse<DynamicModel>> dynamicAdd2(@Field("circleId") String circleId, @Field("message") String message);


        @POST("/api/circle/message/list.json")
        @FormUrlEncoded
        @ServiceMethod("dynamicList")
        Observable<DefResponse<List<DynamicModel>>> dynamicList(@Field("page") int page, @Field("circleId") String circleId);


        @POST("/api/circle/message/delete.json")
        @FormUrlEncoded
        @ServiceMethod("dynamicDelete")
        Observable<DefResponse<Object>> dynamicDelete(@Field("circleMessageId") String circleMessageId);

        @POST("/api/circle/message/like/add.json")
        @FormUrlEncoded
        @ServiceMethod("likeAdd")
        Observable<DefResponse<Object>> likeAdd(@Field("circleMessageId") String circleMessageId);

        @POST("/api/circle/message/like/delete.json")
        @FormUrlEncoded
        @ServiceMethod("likeDelete")
        Observable<DefResponse<Object>> likeDelete(@Field("circleMessageId") String circleMessageId);

        @POST("/api/circle/message/like/list.json")
        @FormUrlEncoded
        @ServiceMethod("likeList")
        Observable<DefResponse<List<DynamicLikeModel>>> likeList(@Field("page") int page, @Field("circleMessageId") String circleMessageId);

        @POST("/api/circle/message/comment/add.json")
        @FormUrlEncoded
        @ServiceMethod("commentAdd")
        Observable<DefResponse<Object>> commentAdd(@Field("circleMessageId") String circleMessageId, @Field("comment") String comment);


        @POST("/api/circle/message/comment/list.json")
        @FormUrlEncoded
        @ServiceMethod("commentList")
        Observable<DefResponse<List<DynamicCommentModel>>> commentList(@Field("page") int page, @Field("circleMessageId") String circleMessageId);


        @POST("/api/circle/message/report.json")
        @FormUrlEncoded
        @ServiceMethod("dynamicReport")
        Observable<DefResponse<Object>> dynamicReport(@Field("circleMessageId") String circleMessageId);


    }

    @Override
    public void dynamicAdd(String circleId, String message, List<String> file, OnAPIListener<DynamicModel> listener) {
        HashMap<String, RequestBody> map = new HashMap<>();
        if (file == null || file.size() == 0) {
            setSubscribe(service.dynamicAdd2(circleId, message), new DefObserver<>(listener));
        } else {
            for (int i = 0; i < file.size(); i++) {
                File f = new File(file.get(i));
                map.put("file\"; filename=\"" + f.getAbsolutePath(), RequestBody.create(MediaType.parse("image/*"), f));
            }
            setSubscribe(service.dynamicAdd(circleId, message, map), new DefObserver<>(listener));
        }
    }

    @Override
    public void dynamicList(int page, String circleId, OnAPIListener<List<DynamicModel>> listener) {
        setSubscribe(service.dynamicList(page, circleId), new DefObserver<>(listener));
    }

    @Override
    public void dynamicDelete(String circleMessageId, OnAPIListener<Object> listener) {
        setSubscribe(service.dynamicDelete(circleMessageId), new DefObserver<>(listener));
    }

    @Override
    public void likeAdd(String circleMessageId, OnAPIListener<Object> listener) {
        setSubscribe(service.likeAdd(circleMessageId), new DefObserver<>(listener));
    }

    @Override
    public void likeDelete(String circleMessageId, OnAPIListener<Object> listener) {
        setSubscribe(service.likeDelete(circleMessageId), new DefObserver<>(listener));
    }

    @Override
    public void likeList(int page, String circleMessageId, OnAPIListener<List<DynamicLikeModel>> listener) {
        setSubscribe(service.likeList(page, circleMessageId), new DefObserver<>(listener));
    }

    @Override
    public void commentAdd(String circleMessageId, String comment, OnAPIListener<Object> listener) {
        setSubscribe(service.commentAdd(circleMessageId, comment), new DefObserver<>(listener));
    }

    @Override
    public void commentList(int page, String circleMessageId, OnAPIListener<List<DynamicCommentModel>> listener) {
        setSubscribe(service.commentList(page, circleMessageId), new DefObserver<>(listener));
    }

    @Override
    public void dynamicReport(String circleMessageId, OnAPIListener<Object> listener) {
        setSubscribe(service.dynamicReport(circleMessageId), new DefObserver<>(listener));
    }


}
