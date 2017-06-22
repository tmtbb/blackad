package com.yundian.blackcard.android.fragment;

import android.app.Activity;
import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.TribeAdapter;
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

    @Override
    public int getLayoutId() {
        return R.layout.fm_tribe;
    }

    @Override
    protected IListAdapter<TribeListModel> createAdapter() {
        return tribeAdapter = new TribeAdapter(context);
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
                        tribeAdapter.setHasTribe(tribeModel.getOwnTribe().getStatus() != 0);
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
                    ActivityUtil.nextTribeAdd(context);
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
}
