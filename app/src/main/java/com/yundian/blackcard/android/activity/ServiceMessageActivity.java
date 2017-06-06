package com.yundian.blackcard.android.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.qiyukf.unicorn.activity.ServiceMessageFragment;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.UserInfo;
import com.yundian.comm.util.StringUtils;

public class ServiceMessageActivity extends BaseActivity {

    private String strTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_service_message;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (StringUtils.isEmpty(title.toString())) {
            title = strTitle;
        }
        super.setTitle(title);
    }

    @Override
    public void initData() {
        super.initData();
        strTitle = getIntent().getStringExtra("title");
        setTitle(strTitle);
        UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra(UserInfo.class.getName());
        if (userInfo == null) {
            finish();
            return;
        }
        YSFUserInfo ysfUserInfo = new YSFUserInfo();
        ysfUserInfo.userId = userInfo.getUserId().toString();
        ysfUserInfo.data = "[\n" +
                "        {\"key\":\"real_name\", \"value\":\"%s\"},\n" +
                "        {\"key\":\"mobile_phone\", \"value\":\"%s\"},\n" +
                "        {\"key\":\"email\", \"hidden\":true},\n" +
                "        {\"index\":0, \"key\":\"userID\", \"label\":\"用户ID\", \"value\":\"%s\"},\n" +
                "        {\"index\":1, \"key\":\"blackCardNo\", \"label\":\"黑卡卡号\", \"value\":\"%s\"},\n" +
                "        {\"index\":2, \"key\":\"blackCard\", \"label\":\"黑卡类型\", \"value\":\"%s\"},\n" +
                "        {\"index\":3, \"key\":\"privilegeName\", \"label\":\"服务类型\", \"value\":\"%s\"}\n" +
                "]";
        ysfUserInfo.data = String.format(ysfUserInfo.data, userInfo.getName(),
                userInfo.getPhoneNum(), userInfo.getUserId().toString(),
                userInfo.getBlackCardId().toString(), userInfo.getBlackCardName(), strTitle);
        Unicorn.setUserInfo(ysfUserInfo);


        ConsultSource source = new ConsultSource(strTitle, strTitle, strTitle);
        LinearLayout sdkIconContainer = new LinearLayout(this);
        sdkIconContainer.setOrientation(LinearLayout.HORIZONTAL);
        ServiceMessageFragment fragment = Unicorn.newServiceFragment("", source, sdkIconContainer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();


    }
}
