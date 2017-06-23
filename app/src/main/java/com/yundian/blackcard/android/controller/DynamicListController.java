package com.yundian.blackcard.android.controller;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.yundian.blackcard.android.adapter.DynamicListAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.listener.DynamicAction;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.util.ActivityUtil;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-18 14:43
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicListController extends BaseDynamicController {

    private DynamicListAdapter dynamicListAdapter;
    private ListView contentView;


    public DynamicListController(Context context, ListView contentView, DynamicListAdapter dynamicListAdapter) {
        super(context);
        this.contentView = contentView;
        this.dynamicListAdapter = dynamicListAdapter;
    }

    @Override
    public void initData() {
        super.initData();
        registerAction(ActionConstant.Action.DYNAMIC_DETAIL, new DynamicDetailAction());
    }

    private class DynamicDetailAction implements DynamicAction {

        @Override
        public void doAction(View view, int action, DynamicModel dynamicModel, Object obj) {
            if (!hasPermission) {
                showNoPermission();
                return;
            }
            ActivityUtil.nextDynamicDetail(context, dynamicModel);

        }
    }

    @Override
    public void onUpdateDynamic(int action, DynamicModel dynamicModel) {
        for (int i = 0; i < dynamicListAdapter.getList().size(); i++) {
            DynamicModel item = dynamicListAdapter.getItem(i);
            if (item.getId().equals(dynamicModel.getId())) {
                item.setLikeNum(dynamicModel.getLikeNum());
                item.setIsLike(dynamicModel.getIsLike());
                item.setCommentNum(dynamicModel.getCommentNum());
                break;
            }
        }
        dynamicListAdapter.notifyDataSetChanged(dynamicModel);
    }
}
