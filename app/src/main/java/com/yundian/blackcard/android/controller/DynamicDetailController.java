package com.yundian.blackcard.android.controller;

import android.content.Context;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.DynamicDetailActivity;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.listener.DynamicAction;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.view.DrawableTextView;
import com.yundian.blackcard.android.view.DynamicView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-18 14:44
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicDetailController extends BaseDynamicController {

    private DynamicView dynamicView;
    private DrawableTextView praiseText;

    public DynamicDetailController(Context context, DynamicView dynamicView, DrawableTextView praiseText) {
        super(context);
        this.dynamicView = dynamicView;
        this.praiseText = praiseText;
    }

    @Override
    public void onUpdateDynamic(int action, DynamicModel dynamicModel) {
        if (action == ActionConstant.Action.DYNAMIC_DELETE) {
            if (context instanceof DynamicDetailActivity) {
                ((DynamicDetailActivity) context).closeLoader();
                ((DynamicDetailActivity) context).finish();
            }
            return;
        }
        dynamicView.update(dynamicModel);
        updatePraise(dynamicModel);

    }

    public void updatePraise(DynamicModel dynamicModel) {
        if (dynamicModel.getIsLike() == 1) {
            praiseText.setDrawableLeft(R.mipmap.icon_praised);
            praiseText.setText("取消赞");
        } else {
            praiseText.setDrawableLeft(R.mipmap.icon_praise);
            praiseText.setText("赞");
        }
    }
}
