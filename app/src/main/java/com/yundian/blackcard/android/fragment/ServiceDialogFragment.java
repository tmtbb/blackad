package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.ServiceMessageActivity;
import com.yundian.blackcard.android.model.PrivilegeInfo;
import com.yundian.blackcard.android.model.UserInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yaowang on 2017/5/13.
 */

public class ServiceDialogFragment extends BaseDialogFragment {


    @BindView(R.id.privilege_img)
    ImageView privilegeImg;
    @BindView(R.id.privilege_name)
    TextView privilegeName;
    @BindView(R.id.privilege_describe)
    TextView privilegeDescribe;

    @Override
    public int getLayoutId() {
        return R.layout.service_alert;
    }


    @Override
    public void initData() {
        Bundle bundle = getArguments();
        PrivilegeInfo privilegeInfo = (PrivilegeInfo) bundle.getSerializable(PrivilegeInfo.class.getName());
        privilegeName.setText(privilegeInfo.getPrivilegeName());
        privilegeDescribe.setText(privilegeInfo.getPrivilegeDescribe());
        Glide.with(getActivity())
                .load(privilegeInfo.getPrivilegeImgurl())
                .into(privilegeImg);
    }


    @OnClick({R.id.close,R.id.service})
    protected void onClose(View view) {
        dismiss();
        if( view.getId() == R.id.service ) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ServiceMessageActivity.class);
            Bundle bundle = new Bundle();
            UserInfo userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra(UserInfo.class.getName());
            bundle.putSerializable(UserInfo.class.getName(), userInfo);
            intent.putExtras(bundle);
            intent.putExtra("title",privilegeName.getText());
            startActivity(intent);
        }
    }

}
