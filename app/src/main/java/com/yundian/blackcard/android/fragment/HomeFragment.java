package com.yundian.blackcard.android.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.GridViewPageAdapter;
import com.yundian.blackcard.android.adapter.GridViewPrivilegeInfoAdapter;
import com.yundian.blackcard.android.model.BlackcardInfo;
import com.yundian.blackcard.android.model.BlackcardInfos;
import com.yundian.blackcard.android.model.PrivilegeInfo;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by yaowang on 2017/5/10.
 */

public class HomeFragment extends BaseFragment implements OnAPIListener<BlackcardInfos> {

    @BindView(R.id.viewpager)
    protected ViewPager viewPager;

    @BindView(R.id.pageindicator)
    protected PageIndicator pageIndicator;


    private static final int ITEM_COUNT_OF_PAGE = 8;
    @BindView(R.id.home_user_title)
    TextView homeUserTitle;
    protected BlackcardInfos blackcardInfos;
    protected UserInfo userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.fagment_home;
    }

    @Override
    public void initData() {
        super.initData();
        NetworkAPIFactory.getBlackcardService().blackcardInfos(this);
        userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra(UserInfo.class.getName());
        Spanned strHomeUserTitle = Html.fromHtml(String.format(getString(R.string.home_user_title),
                "<font color=\"#E3A63F\">" + userInfo.getBlackCardName() + "</font>", userInfo.getName()));
        homeUserTitle.setText(strHomeUserTitle);
        PrivilegeInfo.blackCardId = userInfo.getBlackCardId().toString();
    }

    @Override
    public void onSuccess(BlackcardInfos blackcardInfos) {

        this.blackcardInfos = blackcardInfos;

        List<View> gridViews = new ArrayList<View>();

        List<List<PrivilegeInfo>> lists = blackcardInfos.privilegesSpit(ITEM_COUNT_OF_PAGE);
        for (Integer i = 0; i < lists.size(); ++i) {
            GridView gridView = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.gridview_layout, null);
            BaseAdapter adapter = new GridViewPrivilegeInfoAdapter(getActivity(), R.layout.home_privilege_item, lists.get(i));
            gridView.setAdapter(adapter);
            gridViews.add(gridView);
            gridView.setTag(i);
            gridView.setOnItemClickListener(onItemClickListener);
        }
        viewPager.setAdapter(new GridViewPageAdapter(gridViews));
        pageIndicator.setViewPager(viewPager, 0);
    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PrivilegeInfo privilegeInfo = (PrivilegeInfo) parent.getAdapter().getItem(position);
            if (privilegeInfo.getPrivilegePowers().containsKey(userInfo.getBlackCardId().toString())) {
                Bundle b = new Bundle();
                b.putSerializable(PrivilegeInfo.class.getName(), privilegeInfo);
                ServiceDialogFragment serviceDialogFragment = new ServiceDialogFragment();
                serviceDialogFragment.setArguments(b);
                serviceDialogFragment.show(getFragmentManager());
            } else {
                Set<Map.Entry<String, Integer>> entrySet = privilegeInfo.getPrivilegePowers().entrySet();
                for (Map.Entry<String, Integer> entry : entrySet) {
                    if (entry.getValue().equals(1)) {
                        for (BlackcardInfo blackcardInfo : blackcardInfos.getBlackcards()) {
                            if (blackcardInfo.getBlackcardId().toString().equals(entry.getKey())) {
                                String content = String.format("很抱歉，该特权为%s专属特权，您可通过会籍升级享受此特权!",
                                        blackcardInfo.getBlackcardName());
                                AlertDialogFragment.AlertDialog()
                                        .setHideCancel(true)
                                        .setContent(content)
                                        .show(getFragmentManager());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    };

}
