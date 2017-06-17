package com.yundian.blackcard.android.activity;

import android.view.View;
import android.widget.ListView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.DynamicDetailAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.view.DynamicCommentHeaderView;
import com.yundian.blackcard.android.view.DynamicView;
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
 * @created time : 2017-06-17 22:37
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicDetailActivity extends BaseRefreshAbsListControllerActivity<DynamicCommentModel> {

    @BindView(R.id.contentView)
    protected ListView listView;
    private DynamicDetailAdapter dynamicDetailAdapter;
    private DynamicModel dynamicModel;
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
    protected IListAdapter<DynamicCommentModel> createAdapter() {
        return dynamicDetailAdapter = new DynamicDetailAdapter(context);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("详情");

        dynamicModel = (DynamicModel) getIntent().getSerializableExtra(ActionConstant.IntentKey.DYNAMIC);
        DynamicView dynamicView = new DynamicView(context);
        dynamicView.update(dynamicModel);
        listView.addHeaderView(dynamicView);
        dynamicCommentHeaderView = new DynamicCommentHeaderView(context);
        dynamicCommentHeaderView.setVisibility(View.GONE);
        listView.addHeaderView(dynamicCommentHeaderView);

    }

    @Override
    public void initListener() {
        super.initListener();
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
                        dynamicCommentHeaderView.setVisibility(dynamicCommentModels.size() != 0 ? View.VISIBLE : View.GONE);
                        getRefreshController().refreshComplete(dynamicCommentModels);
                    }
                });
            }
        });
    }

    @OnClick(value = {R.id.praiseLayout, R.id.commentLayout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.praiseLayout:
                showToast("点赞");
                break;
            case R.id.commentLayout:
                showToast("评论");
                break;
        }
    }
}
