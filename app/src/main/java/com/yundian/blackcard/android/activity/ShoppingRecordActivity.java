package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.ShopRecordAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.DataFractory;
import com.yundian.blackcard.android.model.PurchaseHistoryModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.util.EmptyViewEntityUtil;
import com.yundian.blackcard.android.view.SpaceView;
import com.yundian.comm.adapter.base.IListAdapter;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.listener.OnRefreshPageListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 15:47
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ShoppingRecordActivity extends BaseRefreshAbsListControllerActivity<PurchaseHistoryModel> {
    @BindView(R.id.contentView)
    protected ListView contentView;

    private ShopRecordAdapter shopRecordAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.ac_shopping_detail;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("消费记录");
        contentView.addHeaderView(new SpaceView(context));
        getRefreshController().setEmptyViewEntityList(EmptyViewEntityUtil.getInstance().getDefaultEmptyList());

    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }


    @Override
    protected IListAdapter<PurchaseHistoryModel> createAdapter() {
        return shopRecordAdapter = new ShopRecordAdapter(context);
    }

    @Override
    public void initListener() {
        super.initListener();
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                NetworkAPIFactory.getTradeService().userTrades(pageIndex, new OnAPIListener<List<PurchaseHistoryModel>>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(List<PurchaseHistoryModel> purchaseHistoryModels) {
                        getRefreshController().refreshComplete(purchaseHistoryModels);
                    }
                });
            }
        });
        shopRecordAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                PurchaseHistoryModel item = shopRecordAdapter.getItem(position);
                if (item != null) {
                    Intent intent = new Intent(context, ShopDetailActivity.class);
                    intent.putExtra(ActionConstant.IntentKey.SHOP, item);
                    startActivity(intent);
                }

            }
        });
    }
}
