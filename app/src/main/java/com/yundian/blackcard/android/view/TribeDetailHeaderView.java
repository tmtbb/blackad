package com.yundian.blackcard.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.TribeInfosModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.ui.view.BaseDataFrameLayout;
import com.yundian.comm.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 19:11
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeDetailHeaderView extends BaseDataFrameLayout<TribeInfosModel> {

    @BindView(R.id.statusText)
    protected TextView statusText;
    @BindView(R.id.addressText)
    protected TextView addressText;
    @BindView(R.id.tribeInfo)
    protected TextView tribeInfoText;
    @BindView(R.id.tribeName)
    protected TextView tribeName;
    @BindView(R.id.imageView)
    protected ImageView imageView;
    private static final String ADD = "加入";
    private static final String DELETE = "退出";
    private static final String ING = "审核";
    private String tribeId;

    public TribeDetailHeaderView(Context context) {
        super(context);
    }

    public TribeDetailHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(TribeInfosModel data) {
        if (data != null) {
            TribeInfosModel.MemberInfoModel memberInfo = data.getMemberInfo();
            TribeInfosModel.TribeInfoModel tribeInfo = data.getTribeInfo();
            tribeName.setText(tribeInfo.getName());
            Glide.with(context).load(tribeInfo.getCoverUrl()).centerCrop().into(imageView);
            tribeInfoText.setText("领域 " + tribeInfo.getIndustry() + "  |  " + "成员  " + tribeInfo.getMemberNum());
            addressText.setText(tribeInfo.getProvince() + tribeInfo.getCity());

            //status
            statusText.setVisibility(GONE);
            if (memberInfo.getStatus() == 2 && tribeInfo.getStatus() == 2) {
                statusText.setText(DELETE);
                statusText.setVisibility(VISIBLE);
            }
            if (memberInfo.getStatus() == 0) {
                statusText.setText(ADD);
                statusText.setVisibility(VISIBLE);
            } else if (memberInfo.getStatus() == 1) {
                statusText.setText(ING);
                statusText.setVisibility(VISIBLE);
            }
            if (memberInfo.getIdentity() == 1) {
                statusText.setVisibility(GONE);
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_tribe_detail_header;
    }

    @OnClick(R.id.statusText)
    protected void onClick(View view) {
        String text = statusText.getText().toString().trim();
        if (ADD.equals(text)) {
            onChildViewClick(view, ActionConstant.Action.TRIBE_ADD);
        } else if (DELETE.equals(text)) {
            onChildViewClick(view, ActionConstant.Action.TRIBE_DELETE);
        } else if (ING.equals(text)) {
            ToastUtils.show(context, "正在审核中，请耐心等待");
        }
    }

    public void setTribeId(String tribeId) {
        this.tribeId = tribeId;
    }
}
