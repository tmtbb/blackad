package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.BalanceModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.listener.OnRefreshListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 11:01
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class MyPurseActivity extends BaseRefreshActivity {

    private static final int REQUESTCODE = 66;
    @BindView(R.id.balanceText)
    protected TextView balanceText;

    @Override
    public int getLayoutId() {
        return R.layout.ac_my_wallet;
    }


    @Override
    public void initView() {
        super.initView();
        setTitle("钱包");
        setSubTitle("零钱明细");

    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetworkAPIFactory.getUserService().balance(new OnAPIListener<BalanceModel>() {
                    @Override
                    public void onError(Throwable ex) {
                        getRefreshController().refreshError(ex);
                    }

                    @Override
                    public void onSuccess(BalanceModel balanceModel) {
                        balanceText.setText(balanceModel.getBalance() + "");
                        getRefreshController().refreshComplete();
                        getRefreshController().getContentView().setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    @OnClick(value = {R.id.toolbar_subtitle, R.id.payButton})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                next(MyPurseDetailActivity.class);
                break;
            case R.id.payButton:
                nextForResult(RechargeActivity.class, REQUESTCODE);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
            getRefreshController().refreshBegin();
        }
    }
}
