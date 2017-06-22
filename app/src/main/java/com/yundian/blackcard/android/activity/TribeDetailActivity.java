package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.DynamicListAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.controller.DynamicListController;
import com.yundian.blackcard.android.fragment.DynamicListFragment;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.model.TribeInfosModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.blackcard.android.view.TribeDetailHeaderView;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnChildViewClickListener;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 19:06
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeDetailActivity extends BaseRefreshAbsListControllerActivity<DynamicModel> {

    private String tribeId;
    private TribeDetailHeaderView headerView;
    @BindView(R.id.contentView)
    protected ListView contentView;
    @BindView(R.id.toolBarLayout)
    protected RelativeLayout toolBarLayout;
    @BindView(R.id.dynamicAddLayout)
    protected RelativeLayout dynamicAddLayout;
    @BindView(R.id.titleText)
    protected TextView titleText;
    private DynamicListController dynamicListController;
    private DynamicListAdapter dynamicListAdapter;
    private TribeInfosModel tribeInfosModel;

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        dynamicListController = new DynamicListController(context, contentView, dynamicListAdapter);
        registerController(DynamicListFragment.class.getSimpleName(), dynamicListController, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_tribe_detail;
    }

    @Override
    protected IListAdapter<DynamicModel> createAdapter() {
        return dynamicListAdapter = new DynamicListAdapter(context);
    }

    @Override
    public void initView() {
        super.initView();
        headerView = new TribeDetailHeaderView(context);
        headerView.setVisibility(View.GONE);
        contentView.addHeaderView(headerView);
        tribeId = getIntent().getStringExtra(ActionConstant.IntentKey.TRIBEID_ID);

    }


    @Override
    public void initListener() {
        super.initListener();

        dynamicListAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                DynamicModel dynamicModel = dynamicListAdapter.getItem(position);
                if (dynamicModel != null) {
                    dynamicListController.onItemChildViewClick(childView, action, dynamicModel, obj);
                }
            }
        });
        headerView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                switch (action) {
                    case ActionConstant.Action.TRIBE_ADD:
                        NetworkAPIFactory.getTribeService().memberAdd(tribeId, new OnAPIListener<Object>() {
                            @Override
                            public void onError(Throwable ex) {
                                ToastUtils.show(context, ex.getMessage());
                            }

                            @Override
                            public void onSuccess(Object o) {
                                tribeInfosModel.getTribeInfo().setStatus(2);
                                headerView.update(tribeInfosModel);
                                ToastUtils.show(context, "加入成功");
                                getRefreshController().refreshBegin();
                            }
                        });
                        break;
                    case ActionConstant.Action.TRIBE_DELETE:
                        NetworkAPIFactory.getTribeService().memberDelete(tribeId, new OnAPIListener<Object>() {
                            @Override
                            public void onError(Throwable ex) {
                                ToastUtils.show(context, ex.getMessage());
                            }

                            @Override
                            public void onSuccess(Object o) {
                                tribeInfosModel.getMemberInfo().setStatus(0);
                                headerView.update(tribeInfosModel);
                                updateBottomStatus(0);
                                ToastUtils.show(context, "退出成功");
                            }
                        });
                        break;
                }
            }
        });

        getRefreshController().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    toolBarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    titleText.setVisibility(View.GONE);
                } else {
                    titleText.setText(tribeInfosModel.getTribeInfo().getName());
                    titleText.setVisibility(View.VISIBLE);
                    toolBarLayout.setBackgroundColor(getResources().getColor(R.color.color_434343));
                }
            }
        });
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                if (pageIndex == 1) {
                    showLoader();
                    NetworkAPIFactory.getTribeService().tribeInfo(tribeId, new OnAPIListener<TribeInfosModel>() {
                        @Override
                        public void onError(Throwable ex) {
                            onShowError(ex);
                        }

                        @Override
                        public void onSuccess(TribeInfosModel tribeInfosModel) {
                            closeLoader();
                            TribeDetailActivity.this.tribeInfosModel = tribeInfosModel;
                            updateBottomStatus(tribeInfosModel.getMemberInfo().getStatus());
                            headerView.setVisibility(View.VISIBLE);
                            headerView.update(tribeInfosModel);
                        }
                    });
                }
                NetworkAPIFactory.getDynamicService().dynamicList(pageIndex, tribeId, new OnAPIListener<List<DynamicModel>>() {
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

    }

    private void updateBottomStatus(int status) {
        if (status == 0 || status == 1) {
            dynamicAddLayout.setVisibility(View.GONE);
        } else if (status == 2) {
            dynamicAddLayout.setVisibility(View.VISIBLE);
        } else if (status == 3) {

        }
    }

    @OnClick(value = {R.id.dynamicAddLayout, R.id.leftIcon, R.id.userAddIcon})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftIcon:
                finish();
                break;
            case R.id.userAddIcon:
                ActivityUtil.nextTribeApplay(this, tribeId, tribeInfosModel.getMemberInfo().getIdentity());
                break;
            case R.id.dynamicAddLayout:
                ActivityUtil.nextDynamicAdd(this, tribeId);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActionConstant.Action.DYNAMIC_COMMENT_REQUEST
                && resultCode == RESULT_OK) {
            DynamicModel dynamicModel = (DynamicModel) data.getSerializableExtra(ActionConstant.IntentKey.DYNAMIC);
            if (dynamicModel != null)
                dynamicListController.onUpdateDynamic(ActionConstant.Action.DYNAMIC_COMMENT, dynamicModel);
        } else if (requestCode == ActionConstant.Action.DYNAMIC_RELEASE_REQUEST
                && resultCode == RESULT_OK) {
            DynamicModel dynamicModel = (DynamicModel) data.getSerializableExtra(ActionConstant.IntentKey.DYNAMIC);
            if (dynamicModel != null) {
                dynamicListAdapter.getList().add(0, dynamicModel);
                dynamicListAdapter.notifyDataSetChanged();
            }
        }
    }
}
