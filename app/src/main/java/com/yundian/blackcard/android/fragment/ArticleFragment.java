package com.yundian.blackcard.android.fragment;

import android.view.View;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.ArticleListAdapter;
import com.yundian.blackcard.android.fragment.base.BaseRefreshAbsListControllerFragment;
import com.yundian.blackcard.android.model.ArticleModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.ActivityUtil;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-19 16:00
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ArticleFragment extends BaseRefreshAbsListControllerFragment<ArticleModel> {

    private ArticleListAdapter articleListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fm_article;
    }

    @Override
    protected IListAdapter<ArticleModel> createAdapter() {
        return articleListAdapter = new ArticleListAdapter(context);
    }

    @Override
    public void initListener() {
        super.initListener();
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                NetworkAPIFactory.getArticleService().articleList(pageIndex, "1", new OnAPIListener<List<ArticleModel>>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(List<ArticleModel> articleModels) {
                        getRefreshController().refreshComplete(articleModels);
                    }
                });
            }
        });

        articleListAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                ArticleModel model = articleListAdapter.getItem(position);
                if (model != null) {
                    ActivityUtil.nextArticleDetail(context, model);
                }
            }
        });
    }
}
