package com.yundian.blackcard.android.networkapi.okhttp;

import com.yundian.blackcard.android.model.TribeAddModel;
import com.yundian.blackcard.android.model.TribeInfosModel;
import com.yundian.blackcard.android.model.TribeListModel;
import com.yundian.blackcard.android.model.TribeMemberModel;
import com.yundian.blackcard.android.model.TribeModel;
import com.yundian.blackcard.android.networkapi.ITribeService;
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
import retrofit2.http.QueryMap;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-20 09:55
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeService extends OkHttpService<TribeService.RetrofitTribeService> implements ITribeService {


    interface RetrofitTribeService {

        @FormUrlEncoded
        @POST("/api/tribe/user/index.json")
        @ServiceMethod("tribeIndex")
        Observable<DefResponse<TribeModel>> tribeIndex(@Field("type") int type);

        @FormUrlEncoded
        @POST("/api/tribe/member/list.json")
        @ServiceMethod("memberList")
        Observable<DefResponse<List<TribeMemberModel>>> memberList(@Field("page") int page, @Field("tribeId") String tribeId);


        @POST("/api/tribe/add.json")
        @Multipart
        @ServiceMethod("tribeAdd")
        Observable<DefResponse<TribeListModel>> tribeAdd(@QueryMap Map<String, Object> map, @PartMap Map<String, RequestBody> fileMap);


        @FormUrlEncoded
        @POST("/api/tribe/info.json")
        @ServiceMethod("tribeInfo")
        Observable<DefResponse<TribeInfosModel>> tribeInfo(@Field("tribeId") String tribeId);


        @FormUrlEncoded
        @POST("/api/tribe/member/add.json")
        @ServiceMethod("memberAdd")
        Observable<DefResponse<Object>> memberAdd(@Field("tribeId") String tribeId);

        @FormUrlEncoded
        @POST("/api/tribe/member/delete.json")
        @ServiceMethod("memberDelete")
        Observable<DefResponse<Object>> memberDelete(@Field("tribeId") String tribeId);

        @FormUrlEncoded
        @POST("/api/tribe/member/verify.json")
        @ServiceMethod("memberVerify")
        Observable<DefResponse<Object>> memberVerify(@Field("tribeMemberId") String tribeMemberId, @Field("type") int type);

    }


    @Override
    public void tribeIndex(int type,OnAPIListener<TribeModel> listener) {
        setSubscribe(service.tribeIndex(type), new DefObserver<>(listener));
    }

    @Override
    public void memberList(int page, String tribeId, OnAPIListener<List<TribeMemberModel>> listener) {
        setSubscribe(service.memberList(page, tribeId), new DefObserver<>(listener));
    }


    @Override
    public void tribeAdd(TribeAddModel tribeAddModel, OnAPIListener<TribeListModel> listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", tribeAddModel.getName());
        map.put("industry", tribeAddModel.getIndustry());
        map.put("province", tribeAddModel.getProvince());
        map.put("city", tribeAddModel.getCity());
        map.put("description", tribeAddModel.getDescription());


        Map<String, RequestBody> fileMap = new HashMap<>();
        File f = new File(tribeAddModel.getFile());
        fileMap.put("file\"; filename=", RequestBody.create(MediaType.parse("image/jpeg"), f));

        setSubscribe(service.tribeAdd(map, fileMap), new DefObserver<>(listener));
    }

    @Override
    public void tribeInfo(String tribeId, OnAPIListener<TribeInfosModel> listener) {
        setSubscribe(service.tribeInfo(tribeId), new DefObserver<>(listener));
    }

    @Override
    public void memberAdd(String tribeId, OnAPIListener<Object> listener) {
        setSubscribe(service.memberAdd(tribeId), new DefObserver<>(listener));
    }

    @Override
    public void memberDelete(String tribeId, OnAPIListener<Object> listener) {
        setSubscribe(service.memberDelete(tribeId), new DefObserver<>(listener));
    }

    @Override
    public void memberVerify(String tribeMemberId, int type, OnAPIListener<Object> listener) {
        setSubscribe(service.memberVerify(tribeMemberId, type), new DefObserver<>(listener));
    }


}
