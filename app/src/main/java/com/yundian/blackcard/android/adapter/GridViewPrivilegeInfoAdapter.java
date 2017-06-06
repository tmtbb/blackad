package com.yundian.blackcard.android.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.PrivilegeInfo;

import java.util.List;


/**
 * Created by yaowang on 2017/5/10.
 */
public class GridViewPrivilegeInfoAdapter extends CommonAdapter<PrivilegeInfo> {


    public GridViewPrivilegeInfoAdapter(Context context, int layoutResId, List<PrivilegeInfo> data) {
        super(context, layoutResId, data);
    }

    @Override
    public void onUpdate(BaseAdapterHelper helper, PrivilegeInfo privilegeInfo, int position) {
        helper.setImageResource(R.id.privilege_icon, R.mipmap.privlege_icon_def);
        Glide.with(mContext)
                .load(privilegeInfo.getPrivilegeIcon())
                .into((ImageView) helper.getView(R.id.privilege_icon));
        helper.setText(R.id.privilege_name, privilegeInfo.getPrivilegeName());
        boolean isPower = privilegeInfo.getPrivilegePowers().containsKey(PrivilegeInfo.blackCardId)
                && privilegeInfo.getPrivilegePowers().get(PrivilegeInfo.blackCardId) > 0;
        helper.getView(R.id.privilege_name).setEnabled(!isPower);
        helper.getView(R.id.privilege_icon).setAlpha(isPower ? 1 : 0.5f);

    }
}
