package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.DynamicDetailAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.blackcard.android.view.ArticleWebHeaderView;
import com.yundian.blackcard.android.view.DynamicCommentHeaderView;
import com.yundian.comm.adapter.base.IListAdapter;
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
        listView.addHeaderView(commentHeaderView);

    }

    @Override
    public void initData() {
        super.initData();
        articleModel = (ArticleModel) getIntent().getSerializableExtra(ActionConstant.IntentKey.ARTICLE);
        setTitle(articleModel.getTitle());
        showLoader();
        webHeaderView.update(articleModel.getDetailUrl());
    }

    @Override
    public void initListener() {
        super.initListener();
        webHeaderView.setOnPageFinishListener(new ArticleWebHeaderView.OnPageFinishListener() {
            @Override
            public void onCloseLoader() {
                closeLoader();
            }
        });
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                NetworkAPIFactory.getArticleService().commentList(pageIndex, articleModel.getId(), new OnAPIListener<List<DynamicCommentModel>>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(List<DynamicCommentModel> dynamicCommentModels) {
                        commentHeaderView.update(" (" + dynamicCommentModels.size() + "条) ");
                        getRefreshController().refreshComplete(dynamicCommentModels);
                    }
                });
            }
        });
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
            String content = data.getStringExtra(ActionConstant.IntentKey.ARTICLE_COMMENT);
            if (!TextUtils.isEmpty(content))
                getRefreshController().refreshBegin();
        }
    }
}
