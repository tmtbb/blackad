package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.DynamicDetailActivity;
import com.yundian.blackcard.android.adapter.DynamicAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.fragment.base.BaseRefreshAbsListControllerFragment;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

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

    private DynamicAdapter dynamicAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fm_dynamic_list;
    }

    @Override
    protected IListAdapter<DynamicModel> createAdapter() {
        return dynamicAdapter = new DynamicAdapter(context);
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

        dynamicAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                DynamicModel dynamicModel = dynamicAdapter.getItem(position);
                if (dynamicModel != null) {
                    Intent intent = new Intent(context, DynamicDetailActivity.class);
                    intent.putExtra(ActionConstant.IntentKey.DYNAMIC, dynamicModel);
                    startActivity(intent);
                }
            }
        });
    }
}
