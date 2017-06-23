package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.DynamicListAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.controller.DynamicListController;
import com.yundian.blackcard.android.fragment.base.BaseRefreshAbsListControllerFragment;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-17 16:22
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicListFragment extends BaseRefreshAbsListControllerFragment<DynamicModel> {

    private DynamicListAdapter dynamicListAdapter;
    private DynamicListController dynamicListController;
    @BindView(R.id.contentView)
    protected ListView contentView;
    private int clickedPosition;

    @Override
    public int getLayoutId() {
        return R.layout.fm_dynamic_list;
    }

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        dynamicListController = new DynamicListController(context, contentView, dynamicListAdapter);
        registerController(DynamicListFragment.class.getSimpleName(), dynamicListController, false);
    }

    @Override
    protected IListAdapter<DynamicModel> createAdapter() {
        return dynamicListAdapter = new DynamicListAdapter(context);
    }


    @Override
    public void initListener() {
        super.initListener();
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                NetworkAPIFactory.getDynamicService().dynamicList(pageIndex, "0", new OnAPIListener<List<DynamicModel>>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(List<DynamicModel> dynamicModels) {
                        getRefreshController().refreshComplete(dynamicModels);
                    }
                });
            }
        });

        dynamicListAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                DynamicModel dynamicModel = dynamicListAdapter.getItem(position);
                if (dynamicModel != null) {
                    clickedPosition = position;
                    dynamicListController.onItemChildViewClick(childView, action, dynamicModel, obj);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActionConstant.Action.DYNAMIC_COMMENT_REQUEST
                && resultCode == RESULT_OK && data != null) {
            DynamicModel dynamicModel = dynamicListAdapter.getItem(clickedPosition);
            dynamicModel.setCommentNum(dynamicModel.getCommentNum() + 1);
            if (dynamicModel != null)
                dynamicListController.onUpdateDynamic(ActionConstant.Action.DYNAMIC_COMMENT, dynamicModel);
        } else if (requestCode == ActionConstant.Action.DYNAMIC_RELEASE_REQUEST
                && resultCode == RESULT_OK && data != null) {
            DynamicModel dynamicModel = (DynamicModel) data.getSerializableExtra(ActionConstant.IntentKey.DYNAMIC);
            if (dynamicModel != null) {
                dynamicListAdapter.getList().add(0, dynamicModel);
                dynamicListAdapter.notifyDataSetChanged();
            }
        }
    }
}
