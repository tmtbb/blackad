package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.helper.CityPickerHelper;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.TribeAddModel;
import com.yundian.blackcard.android.model.TribeListModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorHelper;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 15:51
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeAddActivity extends BaseActivity {

    @BindView(R.id.tribeAddressText)
    protected TextView tribeAddressText;
    @BindView(R.id.tribeIndustryText)
    protected EditText tribeIndustryText;
    @BindView(R.id.tribeDescriptionText)
    protected EditText tribeDescriptionText;
    @BindView(R.id.tribeName)
    protected EditText tribeName;
    @BindView(R.id.picIcon)
    protected ImageView picIcon;
    @BindView(R.id.picText)
    protected TextView picText;
    private CityPickerHelper cityPickerHelper;
    private MultiImageSelectorHelper selectorHelper;
    private String province;
    private String city;

    @Override
    public int getLayoutId() {
        return R.layout.ac_tribe_add;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("新增部落");
    }

    @Override
    public void initData() {
        super.initData();
        cityPickerHelper = new CityPickerHelper();
        selectorHelper = new MultiImageSelectorHelper(this);
        selectorHelper.setClip(false);
    }

    @OnClick(value = {R.id.tribeAdd, R.id.tribeAddressLayout, R.id.picLayout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribeAdd:
                String name = tribeName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast(tribeName.getHint());
                    return;
                }
                if (selectorHelper.getmSelectPath().size() < 1) {
                    showToast("请上传部落背景图片");
                    return;
                }
                String address = tribeAddressText.getText().toString().trim();
                if (TextUtils.isEmpty(province)) {
                    showToast(tribeAddressText.getHint());
                    return;
                }
                final String industry = tribeIndustryText.getText().toString().trim();
                if (TextUtils.isEmpty(industry)) {
                    showToast(tribeIndustryText.getHint());
                    return;
                }
                String description = tribeDescriptionText.getText().toString().trim();
                if (TextUtils.isEmpty(description)) {
                    showToast(tribeDescriptionText.getHint());
                    return;
                }
                TribeAddModel model = new TribeAddModel();
                model.setName(name);
                model.setFile(selectorHelper.getmSelectPath().get(0));
                model.setProvince(province);
                model.setCity(city);
                model.setDescription(description);
                model.setIndustry(industry);
                NetworkAPIFactory.getTribeService().tribeAdd(model, new OnAPIListener<TribeListModel>() {
                    @Override
                    public void onError(Throwable ex) {
                        onShowError(ex);
                    }

                    @Override
                    public void onSuccess(TribeListModel tribeListModel) {
                        showToast("创建成功");
                        Intent intent = new Intent();
                        intent.putExtra(ActionConstant.IntentKey.TRIBEID_LIST_MODEL, tribeListModel);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                break;
            case R.id.tribeAddressLayout:
                cityPickerHelper.selectCity(context, new CityPickerHelper.OnCitySelectedListener() {
                    @Override
                    public void onCitySelected(String... citySelected) {
                        tribeAddressText.setText(citySelected[0] + citySelected[1]);
                        province = citySelected[0];
                        city = citySelected[1];
                    }
                });
                break;
            case R.id.picLayout:
                selectorHelper.pickImage(true, 1, true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MultiImageSelectorHelper.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                selectorHelper.setmSelectPath(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                picText.setVisibility(View.GONE);
                picIcon.setVisibility(View.VISIBLE);
                Glide.with(context).load(selectorHelper.getmSelectPath().get(0)).into(picIcon);

            }
        }
    }
}
