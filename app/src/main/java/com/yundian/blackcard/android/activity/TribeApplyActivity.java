package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.TribeApplyAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.TribeMemberModel;
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
 * @created time : 2017-06-22 09:51
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeApplyActivity extends BaseRefreshAbsListControllerActivity<TribeMemberModel> {

    private TribeApplyAdapter tribeApplyAdapter;
    private String tribeId;
    private int identity = 1;

    @Override
    public int getLayoutId() {
        return R.layout.ac_tribe_apply;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }


    @Override
    protected IListAdapter<TribeMemberModel> createAdapter() {
        return tribeApplyAdapter = new TribeApplyAdapter(context);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("部落申请");
        tribeId = getIntent().getStringExtra(ActionConstant.IntentKey.TRIBEID_ID);
        identity = getIntent().getIntExtra(ActionConstant.IntentKey.TRIBEID_IDENTITY, 1);
    }

    @Override
    public void initListener() {
        super.initListener();

        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                NetworkAPIFactory.getTribeService().memberList(pageIndex, tribeId, new OnAPIListener<List<TribeMemberModel>>() {
                    @Override
                    public void onError(Throwable ex) {
                        onShowError(ex);
                    }

                    @Override
                    public void onSuccess(List<TribeMemberModel> tribeMemberModels) {
                        tribeApplyAdapter.setIdentity(identity);
                        getRefreshController().refreshComplete(tribeMemberModels);
                    }
                });
            }
        });
        tribeApplyAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                final TribeMemberModel model = tribeApplyAdapter.getItem(position);
                switch (action) {
                    case ActionConstant.Action.TRIBE_AGREE:
                        NetworkAPIFactory.getTribeService().memberVerify(model.getId(), 1, new OnAPIListener<Object>() {
                            @Override
                            public void onError(Throwable ex) {
                                onShowError(ex);
                            }

                            @Override
                            public void onSuccess(Object o) {
                                model.setStatus(2);
                                tribeApplyAdapter.notifyDataSetChanged(model);
                                showToast("已同意");
                                context.sendBroadcast(new Intent(ActionConstant.Broadcast.TRIBE_UPDATE));
                            }
                        });
                        break;
                    case ActionConstant.Action.TRIBE_REFUSE:
                        NetworkAPIFactory.getTribeService().memberVerify(model.getId(), 0, new OnAPIListener<Object>() {
                            @Override
                            public void onError(Throwable ex) {
                                onShowError(ex);
                            }

                            @Override
                            public void onSuccess(Object o) {
                                model.setStatus(3);
                                tribeApplyAdapter.notifyDataSetChanged(model);
                                showToast("已拒绝");
                                context.sendBroadcast(new Intent(ActionConstant.Broadcast.TRIBE_UPDATE));
                            }
                        });
                        break;
                }
            }
        });
    }
}
