package com.yundian.blackcard.android.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.View;

import com.yundian.blackcard.android.activity.LoginActivity;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.listener.DynamicAction;
import com.yundian.blackcard.android.listener.OnDynamicUpdateListener;
import com.yundian.blackcard.android.model.CircleMessageImgModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.blackcard.android.util.DynamicActionObservable;
import com.yundian.blackcard.android.view.ActionSheetDialog;
import com.yundian.comm.controller.BaseController;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.ArrayList;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-18 14:33
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public abstract class BaseDynamicController extends BaseController {

    protected SparseArray<DynamicAction> actionMap = new SparseArray<>();

    public abstract void onUpdateDynamic(int action, DynamicModel dynamicModel);

    private OnDynamicUpdateListener onDynamicUpdateListenerImpl = new OnDynamicUpdateListener() {

        @Override
        public void onDynamicUpdate(int action, DynamicModel dynamicEntity) {

            BaseDynamicController.this.onUpdateDynamic(action, dynamicEntity);

        }
    };

    public BaseDynamicController(Context context) {
        super(context);
    }

    @Override
    public void initListener() {
        super.initListener();
        DynamicActionObservable.getInstance().registerUpdateListener(onDynamicUpdateListenerImpl);
    }

    @Override
    public void initData() {
        super.initData();
        registerAction(ActionConstant.Action.DYNAMIC_NAME, new DynamicUserNameAction());
        registerAction(ActionConstant.Action.DYNAMIC_PRAISE, new DynamicPraiseAction());
        registerAction(ActionConstant.Action.DYNAMIC_COMMENT, new DynamicCommentAction());
        registerAction(ActionConstant.Action.DYNAMIC_MORE, new DynamicMoreAction());
        registerAction(ActionConstant.Action.DYNAMIC_PIC, new DynamicPicsAction());

    }

    protected void registerAction(int action, DynamicAction dynamicAction) {
        actionMap.put(action, dynamicAction);
    }

    public void onItemChildViewClick(View view, int action, DynamicModel dynamicModel, Object obj) {

        if (checkLogin(action)) {
            if (dynamicModel != null) {
                DynamicAction dynamicAction = actionMap.get(action);
                if (dynamicAction != null) {
                    dynamicAction.doAction(view, action, dynamicModel, obj);
                }
            }
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public boolean checkLogin(int action) {

        return true;

    }

    class DynamicUserNameAction implements DynamicAction {

        @Override
        public void doAction(View view, int action, DynamicModel dynamicModel, Object obj) {
            showToast("点击昵称-->" + dynamicModel.getNickName());
        }
    }

    class DynamicPicsAction implements DynamicAction {

        @Override
        public void doAction(View view, int action, DynamicModel dynamicModel, Object obj) {
            if (dynamicModel.getCircleMessageImgs() != null) {
                ArrayList<String> images = new ArrayList<>();
                for (CircleMessageImgModel image : dynamicModel.getCircleMessageImgs()) {
                    String imageUrl = image.getImg();
                    if (imageUrl.contains("_thumb")) {
                        String[] imgs = imageUrl.split("_thumb");
                        imageUrl = imgs[0] + imgs[1];
                    }
                    images.add(imageUrl);
                }
                ActivityUtil.nextBigImg(context, images, (int) obj);
            }
        }
    }

    class DynamicMoreAction implements DynamicAction {

        @Override
        public void doAction(View view, int action, final DynamicModel dynamicModel, Object obj) {
            new ActionSheetDialog(context)
                    .builder()
                    .setTitle("请选择类型")
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(true)
                    .addSheetItem("举报", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            NetworkAPIFactory.getDynamicService().dynamicReport(dynamicModel.getId(), new OnAPIListener<Object>() {
                                @Override
                                public void onError(Throwable ex) {
                                    showToast(ex.getMessage());
                                }

                                @Override
                                public void onSuccess(Object o) {
                                    showToast("举报成功");
                                }
                            });
                        }
                    }).show();
        }
    }

    class DynamicPraiseAction implements DynamicAction {

        @Override
        public void doAction(View view, final int action, final DynamicModel dynamicModel, Object obj) {
            NetworkAPIFactory.getDynamicService().likeAdd(dynamicModel.getId(), new OnAPIListener<Object>() {
                @Override
                public void onError(Throwable ex) {
                    showToast(ex.getMessage());
                }

                @Override
                public void onSuccess(Object o) {
                    if (dynamicModel.getIsLike() == 1) {
                        dynamicModel.setIsLike(0);
                        dynamicModel.setLikeNum(dynamicModel.getLikeNum() - 1);
                    } else {
                        dynamicModel.setIsLike(1);
                        dynamicModel.setLikeNum(dynamicModel.getLikeNum() + 1);
                    }
                    notifyDynamicChanged(action, dynamicModel);
                }
            });
        }
    }

    class DynamicCommentAction implements DynamicAction {

        @Override
        public void doAction(View view, int action, DynamicModel dynamicModel, Object obj) {
            if (context instanceof Activity)
                ActivityUtil.nextDynamicComment((Activity) context, dynamicModel);
        }
    }


    public void notifyDynamicChanged(int action, DynamicModel dynamicModel) {
        if (actionMap.get(action) != null && dynamicModel != null)
            DynamicActionObservable.getInstance().notifyUpdate(action, dynamicModel);
    }

    @Override
    public void onDestroy() {
        DynamicActionObservable.getInstance().unregisterUpdateListener(onDynamicUpdateListenerImpl);
        super.onDestroy();
    }
}
