package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.DynamicDetailAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.manager.CurrentUserManager;
import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.blackcard.android.view.ActionSheetDialog;
import com.yundian.blackcard.android.view.ArticleWebHeaderView;
import com.yundian.blackcard.android.view.DynamicCommentHeaderView;
import com.yundian.comm.adapter.base.IListAdapter;
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
 * @created time : 2017-06-20 10:39
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ArticleDetailActivity extends BaseRefreshAbsListControllerActivity<DynamicCommentModel> {


    @BindView(R.id.contentView)
    protected ListView listView;
    private DynamicDetailAdapter dynamicDetailAdapter;
    private ArticleModel articleModel;
    private ArticleWebHeaderView webHeaderView;
    private DynamicCommentHeaderView commentHeaderView;

    @Override
    public int getLayoutId() {
        return R.layout.ac_article_detail;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }


    @Override
    protected IListAdapter<DynamicCommentModel> createAdapter() {
        return dynamicDetailAdapter = new DynamicDetailAdapter(context);
    }

    @Override
    public void initView() {
        super.initView();
        setRightImage(R.mipmap.icon_comment_title);
        webHeaderView = new ArticleWebHeaderView(context);
        listView.addHeaderView(webHeaderView);
        commentHeaderView = new DynamicCommentHeaderView(context);
        commentHeaderView.setVisibility(View.GONE);
        listView.addHeaderView(commentHeaderView);
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        articleModel = (ArticleModel) getIntent().getSerializableExtra(ActionConstant.IntentKey.ARTICLE);
        setTitle(articleModel.getTitle());

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
                                NetworkAPIFactory.getArticleService().commentDelete(commentModel.getId(), new OnAPIListener<Object>() {
                                    @Override
                                    public void onError(Throwable ex) {
                                        onShowError(ex);
                                    }

                                    @Override
                                    public void onSuccess(Object o) {
                                        showToast("删除成功");
                                        dynamicDetailAdapter.remove(position);
                                        dynamicDetailAdapter.notifyDataSetChanged();
                                        updateCommentHeaderView();
                                    }
                                });
                            }
                        }).show();
                    }
                }
            }
        });
        webHeaderView.setOnPageFinishListener(new ArticleWebHeaderView.OnPageFinishListener() {
            @Override
            public void onCloseLoader() {
                closeLoader();
                listView.setVisibility(View.VISIBLE);
            }
        });
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(final int pageIndex) {
                if (pageIndex == 1) {
//                    showLoader();
                    NetworkAPIFactory.getArticleService().articleDetail(articleModel.getId(), new OnAPIListener<ArticleModel>() {
                        @Override
                        public void onError(Throwable ex) {
                            onShowError(ex);
                        }

                        @Override
                        public void onSuccess(ArticleModel articleModel) {
                            webHeaderView.update(articleModel.getDetailUrl());
                            requestCommentList(pageIndex);
                        }
                    });
                } else {
                    requestCommentList(pageIndex);
                }

            }
        });
    }

    public void requestCommentList(final int pageIndex) {
        NetworkAPIFactory.getArticleService().commentList(pageIndex, articleModel.getId(), new OnAPIListener<List<DynamicCommentModel>>() {
            @Override
            public void onError(Throwable ex) {
                getRefreshController().refreshError(ex);
            }

            @Override
            public void onSuccess(List<DynamicCommentModel> dynamicCommentModels) {
                getRefreshController().refreshComplete(dynamicCommentModels);
                updateCommentHeaderView();
            }
        });
    }

    private void updateCommentHeaderView() {
        commentHeaderView.update(" (" + dynamicDetailAdapter.getCount() + "条) ");
        commentHeaderView.setVisibility(dynamicDetailAdapter.getCount() != 0 ? View.VISIBLE : View.GONE);

    }

    @OnClick(R.id.toolbar_rightimage)
    protected void onClick(View view) {
        ActivityUtil.nextArticleComment(this, articleModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActionConstant.Action.ARTICLE_COMMENT_REQUEST
                && resultCode == RESULT_OK) {
            DynamicCommentModel model = (DynamicCommentModel) data.getSerializableExtra(ActionConstant.IntentKey.ARTICLE_COMMENT);
            if (model != null) {
                dynamicDetailAdapter.getList().add(model);
                dynamicDetailAdapter.notifyDataSetChanged();
                listView.setSelection(dynamicDetailAdapter.getCount());
                updateCommentHeaderView();
            }
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
