package com.yundian.blackcard.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.TribeAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.fragment.base.BaseRefreshAbsListControllerFragment;
import com.yundian.blackcard.android.model.CreatorTribeModel;
import com.yundian.blackcard.android.model.TribeListModel;
import com.yundian.blackcard.android.model.TribeModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.blackcard.android.view.TribeFloatView;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnChildViewClickListener;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 10:19
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeFragment extends BaseRefreshAbsListControllerFragment<TribeListModel> {


    @BindView(R.id.tribeFloatView)
    protected TribeFloatView tribeFloatView;
    private TribeAdapter tribeAdapter;
    private int ownStatus;
    private TribeModel tribeModel;

    private RefreshBroadcastReceiver refreshBroadcastReceiver;
    private boolean isRefreshPage = false;

    private class RefreshBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isRefreshPage = true;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fm_tribe;
    }

    @Override
    protected IListAdapter<TribeListModel> createAdapter() {
        return tribeAdapter = new TribeAdapter(context);
    }

    @Override
    public void initData() {
        super.initData();
        refreshBroadcastReceiver = new RefreshBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConstant.Broadcast.TRIBE_UPDATE);
        getActivity().registerReceiver(refreshBroadcastReceiver, intentFilter);
    }

    @Override
    public void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetworkAPIFactory.getTribeService().tribeIndex(new OnAPIListener<TribeModel>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(TribeModel tribeModel) {

                        getRefreshController().refreshComplete();
                        TribeFragment.this.tribeModel = tribeModel;
                        tribeAdapter.setHasTribe(tribeModel.getUserTribes().size() != 0);
                        tribeAdapter.setList(convertTribeList(tribeModel));
                        tribeFloatView.update(tribeModel);
                        tribeAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        tribeFloatView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (ownStatus == 0) {
                    ActivityUtil.nextTribeAdd(getActivity());
                } else if (ownStatus == 2) {
                    tribeFloatView.update(tribeModel.setOwnTribe(tribeModel.getOwnTribe().setVerifyNum(0)));
                    ActivityUtil.nextTribeApplay(getActivity(), tribeModel.getOwnTribe().getId(), 1);
                } else if (ownStatus == 1) {
                    showToast("当前部落正在审核");
                }
            }
        });

        tribeAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                TribeListModel model = tribeAdapter.getItem(position);
                if (model != null) {
                    ActivityUtil.nextTribeDetail(context, model.getId());
                }
            }
        });
    }


    private List<TribeListModel> convertTribeList(TribeModel tribeModel) {
        List<TribeListModel> resultModels = new ArrayList<>();
        List<TribeListModel> userTribes = tribeModel.getUserTribes();
        List<TribeListModel> recommendTribes = tribeModel.getRecommendTribes();
        CreatorTribeModel ownTribe = tribeModel.getOwnTribe();

        ownStatus = ownTribe.getStatus();
        //add usertribe
        for (int i = 0; i < userTribes.size(); i++) {
            TribeListModel userTribe = userTribes.get(i);
            if (ownStatus != 0 && userTribe.getId().equals(ownTribe.getId())) {
                userTribe.setVerifyNum(ownTribe.getVerifyNum());
            }
            userTribe.setType(0);
            resultModels.add(userTribe);
        }

        //add recommendtribe
        if (recommendTribes.size() > 0) {
            TribeListModel tagModel = new TribeListModel();
            tagModel.setType(1);
            resultModels.add(tagModel);
            for (int i = 0; i < recommendTribes.size(); i++) {
                TribeListModel recommendTribe = recommendTribes.get(i);
                recommendTribe.setType(2);
                resultModels.add(recommendTribe);
            }
        }

        return resultModels;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActionConstant.Action.TRIBE_ADD_REQUEST && resultCode == RESULT_OK && data != null) {
            TribeListModel model = (TribeListModel) data.getSerializableExtra(ActionConstant.IntentKey.TRIBEID_LIST_MODEL);
            model.setType(0);
            tribeAdapter.getList().add(0, model);
            tribeAdapter.notifyDataSetChanged();
            tribeModel.setOwnTribe(tribeModel.getOwnTribe().setVerifyNum(0).setStatus(1));
            tribeFloatView.update(tribeModel);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isRefreshPage){
            isRefreshPage = false;
            getRefreshController().refreshBegin();
        }
    }

    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(refreshBroadcastReceiver);
        super.onDestroyView();
    }
}
