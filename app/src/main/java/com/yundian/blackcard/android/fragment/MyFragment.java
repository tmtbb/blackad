package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.activity.MyPurseActivity;
import com.yundian.blackcard.android.activity.ShoppingRecordActivity;
import com.yundian.blackcard.android.activity.UserSetInfoActivity;
import com.yundian.blackcard.android.activity.WebViewActivity;
import com.yundian.blackcard.android.manager.UserDetailManager;
import com.yundian.blackcard.android.model.CreatorTribeModel;
import com.yundian.blackcard.android.model.TribeModel;
import com.yundian.blackcard.android.model.UserDetailModel;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yaowang on 2017/5/10.
 */

public class MyFragment extends BaseFragment implements UserDetailManager.OnUserDetailUpdateListener {


    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.headerIcon)
    ImageView headerIcon;
    @BindView(R.id.blackcard_name)
    TextView blackcardName;
    @BindView(R.id.blackcard_creditline)
    TextView blackcardCreditline;
    @BindView(R.id.listview)
    ListView listView;
    List<String> stringList;
    private boolean hasBadge = false;
    private CommonAdapter<String> commonAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fagment_my;
    }

    @Override
    public void initData() {
        super.initData();
        UserInfo userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra(UserInfo.class.getName());
        username.setText(userInfo.getName());
        blackcardName.setText(userInfo.getBlackCardName());
        Glide.with(this).load(userInfo.getHeadUrl()).centerCrop().placeholder(R.mipmap.user_head_def).into(headerIcon);
        blackcardCreditline.setText(String.format("%.2f", userInfo.getBlackcardCreditline()));
        stringList = new ArrayList<String>();
        stringList.add("我的部落");
        stringList.add("我的钱包");
        stringList.add("消费记录");
        stringList.add("消息中心");
        stringList.add("个人中心");
        stringList.add("意见建议");
        stringList.add("关于我们");
        listView.setAdapter(commonAdapter = new CommonAdapter<String>(getActivity(), R.layout.my_home_item, stringList) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
//                if (position == 0) {
//                    helper.getView(R.id.badgeView).setVisibility(hasBadge ? View.VISIBLE : View.INVISIBLE);
//                }
                helper.setText(R.id.textView, item);
                helper.getView(R.id.line).setVisibility(position + 1 == stringList.size() ? View.GONE : View.VISIBLE);
            }
        });

//        NetworkAPIFactory.getTribeService().tribeIndex(1, new OnAPIListener<TribeModel>() {
//            @Override
//            public void onError(Throwable ex) {
//                onShowError(ex);
//            }
//
//            @Override
//            public void onSuccess(TribeModel tribeModel) {
//                CreatorTribeModel ownTribe = tribeModel.getOwnTribe();
//                if (ownTribe != null) {
//                    hasBadge = ownTribe.getVerifyNum() != 0;
//                    commonAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }

    @Override
    public void initListener() {
        super.initListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                       // hasBadge = false;
                       // commonAdapter.notifyDataSetChanged();
                        if (onTribeSelectedListener != null) {
                            onTribeSelectedListener.onTribeSelected();
                        }
                        break;
                    case 1:
                        next(MyPurseActivity.class);
                        break;
                    case 2:
                        next(ShoppingRecordActivity.class);
                        break;
                    case 3:
                        showToast("敬请期待");
                        break;
                    case 4:
                        next(UserSetInfoActivity.class);
                        break;
                    case 5:
                        showToast("敬请期待");
                        break;
                    case 6:
                        Intent intent = new Intent();
                        intent.putExtra(WebViewActivity.EXTRA_KEY_TITLE, "关于我们");
                        intent.setData(Uri.parse("http://app.jingyingheika.com/static/about.html"));
                        intent.setClass(getActivity(), WebViewActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
        UserDetailManager.getInstance().registerUserDetailUpdateListener(this);
    }

    @OnClick(value = R.id.headerIcon)
    protected void headerClick(View view) {
        next(UserSetInfoActivity.class);
    }


    @Override
    public void onUserDetailUpdate(UserDetailModel model) {
        if (model != null) {
            if (username != null)
                username.setText(model.getFullName());
            if (headerIcon != null)
                Glide.with(this).load(model.getHeadUrl()).centerCrop().placeholder(R.mipmap.user_head_def).into(headerIcon);
        }
    }

    private OnTribeSelectedListener onTribeSelectedListener;

    public interface OnTribeSelectedListener {
        void onTribeSelected();
    }

    public void setOnTribeSelectedListener(OnTribeSelectedListener onTribeSelectedListener) {
        this.onTribeSelectedListener = onTribeSelectedListener;
    }
}
