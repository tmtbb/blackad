package com.yundian.blackcard.android.activity;

import android.os.Handler;
import android.widget.ListView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.MyPurseDetailAdapter;
import com.yundian.blackcard.android.model.DataFractory;
import com.yundian.blackcard.android.model.MyPurseDetailModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.EmptyViewEntityUtil;
import com.yundian.blackcard.android.view.SpaceView;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 11:33
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class MyPurseDetailActivity extends BaseRefreshAbsListControllerActivity<MyPurseDetailModel> {

    @BindView(R.id.contentView)
    protected ListView contentView;
    @Override
    public int getLayoutId() {
        return R.layout.ac_mypurse_detail;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("零钱明细");
        contentView.addHeaderView(new SpaceView(context));
        getRefreshController().setEmptyViewEntityList(EmptyViewEntityUtil.getInstance().getDefaultEmptyList());
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    protected IListAdapter<MyPurseDetailModel> createAdapter() {
        return new MyPurseDetailAdapter(context);
    }

    @Override
    public void initListener() {
        super.initListener();
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                NetworkAPIFactory.getUserService().balanceDetail(pageIndex, new OnAPIListener<List<MyPurseDetailModel>>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(List<MyPurseDetailModel> purseDetailModels) {
                        getRefreshController().refreshComplete(purseDetailModels);
                    }
                });
            }
        });
    }
}
