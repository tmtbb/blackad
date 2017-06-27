package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.DynamicDetailAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.controller.DynamicDetailController;
import com.yundian.blackcard.android.manager.CurrentUserManager;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.view.ActionSheetDialog;
import com.yundian.blackcard.android.view.DrawableTextView;
import com.yundian.blackcard.android.view.DynamicCommentHeaderView;
import com.yundian.blackcard.android.view.DynamicView;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnChildViewClickListener;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-17 22:37
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicDetailActivity extends BaseRefreshAbsListControllerActivity<DynamicCommentModel> {

    @BindView(R.id.contentView)
    protected ListView listView;
    @BindView(R.id.praiseText)
    protected DrawableTextView praiseText;
    private DynamicView dynamicView;
    private DynamicModel dynamicModel;
    private DynamicDetailAdapter dynamicDetailAdapter;
    private DynamicDetailController dynamicDetailController;
    private DynamicCommentHeaderView dynamicCommentHeaderView;


    @Override
    public int getLayoutId() {
        return R.layout.ac_dynamic_detail;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        dynamicView = new DynamicView(context);
        dynamicDetailController = new DynamicDetailController(context, dynamicView, praiseText);
        registerController(DynamicDetailActivity.class.getSimpleName(), dynamicDetailController, false);
    }

    @Override
    protected IListAdapter<DynamicCommentModel> createAdapter() {
        return dynamicDetailAdapter = new DynamicDetailAdapter(context);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("详情");
        dynamicModel = (DynamicModel) getIntent().getSerializableExtra(ActionConstant.IntentKey.DYNAMIC);
        dynamicCommentHeaderView = new DynamicCommentHeaderView(context);
        dynamicCommentHeaderView.setVisibility(View.GONE);
        listView.addHeaderView(dynamicView);
        listView.addHeaderView(dynamicCommentHeaderView);
        dynamicDetailController.updatePraise(dynamicModel);
        dynamicView.update(dynamicModel);

    }

    @Override
    public void initListener() {
        super.initListener();
        dynamicDetailAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, final int position, int action, Object obj) {
                if (action == ActionConstant.Action.DYNAMIC_COMMENT_CONTENT) {
                    final DynamicCommentModel commentModel = dynamicDetailAdapter.getItem(position);
                    UserInfo userInfo = CurrentUserManager.getInstance().getUserInfo();
                    if (userInfo.getUserId() == commentModel.getUserId()) {
                        ActionSheetDialog sheetDialog = createActionSheetDialog();
                        sheetDialog.addSheetItem("删除", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                NetworkAPIFactory.getDynamicService().commentDelete(commentModel.getId(), new OnAPIListener<Object>() {
                                    @Override
                                    public void onError(Throwable ex) {
                                        onShowError(ex);
                                    }

                                    @Override
                                    public void onSuccess(Object o) {
                                        showToast("删除成功");
                                        dynamicDetailAdapter.remove(position);
                                        dynamicDetailAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }).show();
                    }
                }
            }
        });
        dynamicView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                dynamicDetailController.onItemChildViewClick(childView, action, dynamicModel, obj);
            }
        });
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                NetworkAPIFactory.getDynamicService().commentList(pageIndex, dynamicModel.getId(), new OnAPIListener<List<DynamicCommentModel>>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(List<DynamicCommentModel> dynamicCommentModels) {
                        getRefreshController().refreshComplete(dynamicCommentModels);
                        dynamicCommentHeaderView.setVisibility(dynamicDetailAdapter.getCount() != 0 ? View.VISIBLE : View.GONE);

                    }
                });
            }
        });
    }

    @OnClick(value = {R.id.praiseLayout, R.id.commentLayout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.praiseLayout:
                dynamicDetailController.onItemChildViewClick(view, ActionConstant.Action.DYNAMIC_PRAISE, dynamicModel, null);
                break;
            case R.id.commentLayout:
                dynamicDetailController.onItemChildViewClick(view, ActionConstant.Action.DYNAMIC_COMMENT, dynamicModel, null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActionConstant.Action.DYNAMIC_COMMENT_REQUEST
                && resultCode == RESULT_OK) {
            dynamicModel.setCommentNum(dynamicModel.getCommentNum() + 1);
            dynamicDetailController.onUpdateDynamic(ActionConstant.Action.DYNAMIC_COMMENT, dynamicModel);
            DynamicCommentModel dynamicCommentModel = (DynamicCommentModel) data.getSerializableExtra(ActionConstant.IntentKey.DYNAMIC_COMMENT);
            dynamicDetailAdapter.getList().add(dynamicCommentModel);
            dynamicDetailAdapter.notifyDataSetChanged();
            listView.setSelection(dynamicDetailAdapter.getCount());
        }

    }

    protected ActionSheetDialog createActionSheetDialog() {
        ActionSheetDialog sheetDialog = new ActionSheetDialog(context)
                .builder()
                .setTitle("请选择类型")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true);
        return sheetDialog;
    }
}
