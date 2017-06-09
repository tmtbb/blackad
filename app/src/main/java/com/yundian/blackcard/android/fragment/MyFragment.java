package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.yundian.blackcard.android.activity.SetupPasswordActivity;
import com.yundian.blackcard.android.activity.ShoppingRecordActivity;
import com.yundian.blackcard.android.activity.UserSetInfoActivity;
import com.yundian.blackcard.android.activity.WebViewActivity;
import com.yundian.blackcard.android.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yaowang on 2017/5/10.
 */

public class MyFragment extends BaseFragment {


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
        Glide.with(this).load(userInfo.getHeadUrl()).placeholder(R.mipmap.user_head_def).into(headerIcon);
        blackcardCreditline.setText(String.format("%.2f", userInfo.getBlackcardCreditline()));
        stringList = new ArrayList<String>();
        stringList.add("我的钱包");
        stringList.add("消费记录");
        stringList.add("消息中心");
        stringList.add("个人中心");
        stringList.add("意见建议");
        stringList.add("关于我们");
        listView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.my_home_item, stringList) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                helper.setText(R.id.textView, item);
                helper.getView(R.id.line).setVisibility(position + 1 == stringList.size() ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        next(MyPurseActivity.class);
                        break;
                    case 1:
                        next(ShoppingRecordActivity.class);
                        break;
                    case 2:
                        showToast("敬请期待");
                        break;
                    case 3:
                        next(UserSetInfoActivity.class);
                        break;
                    case 4:
                        showToast("敬请期待");
                        break;
                    case 5:
                        Intent intent = new Intent();
                        intent.putExtra("title", "关于我们");
                        intent.putExtra("url", "http://app.jingyingheika.com/api/static/about.html");
                        intent.setClass(getActivity(), WebViewActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
    }

    @OnClick(value = R.id.headerIcon)
    protected void headerClick(View view) {
        next(UserSetInfoActivity.class);
    }


}
