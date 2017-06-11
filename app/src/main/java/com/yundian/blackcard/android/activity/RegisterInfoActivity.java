package com.yundian.blackcard.android.activity;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.ProvinceInfo;
import com.yundian.blackcard.android.model.RegisterInfo;
import com.yundian.comm.util.StringUtils;
import com.yundian.comm.util.ValidateUtils;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterInfoActivity extends BaseActivity {


    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.addr)
    EditText addr;
    @BindView(R.id.addr1)
    EditText addr1;
    @BindView(R.id.fullName)
    EditText fullName;
    @BindView(R.id.identity_card)
    EditText identityCard;
    @BindView(R.id.but_next)
    Button butNext;
    private RegisterInfo registerInfo;
    @BindView(R.id.province_city)
    TextView provinceCity;
    OptionsPickerView pickerView;
    @BindView(R.id.content)
    ScrollView scrollView;

    private ArrayList<ProvinceInfo> provinceInfos;
    private ArrayList<ArrayList<String>> cityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> districtList = new ArrayList<>();


    @Override
    public void initData() {
        super.initData();
        setTitle(R.string.title_activity_register_info);
        registerInfo = (RegisterInfo) getIntent().getSerializableExtra(RegisterInfo.class.getName());
        initJsonData();

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_info;
    }

    @OnClick({R.id.but_next})
    public void onClick(View view) {

        registerInfo.setPhoneNum(phone.getText().toString().trim())
                .setEmail(email.getText().toString().trim())
                .setAddr(addr.getText().toString().trim())
                .setAddr1(addr1.getText().toString().trim())
                .setFullName(fullName.getText().toString().trim())

                .setIdentityCard(identityCard.getText().toString().trim());

        if (StringUtils.isEmpty(registerInfo.getPhoneNum())) {
            showToast(phone.getHint());
            return;
        } else if (!ValidateUtils.isPhone(registerInfo.getPhoneNum())) {
            showToast("请输入正确的手机号!");
            return;
        }

        if (StringUtils.isEmpty(registerInfo.getEmail())) {
            showToast(email.getHint());
            return;
        } else if (!ValidateUtils.isEmail(registerInfo.getEmail())) {
            showToast("请输入正确的邮箱!");
            return;
        }


        if (StringUtils.isEmpty(registerInfo.getAddr())) {
            showToast(addr.getHint());
            return;
        } else if (registerInfo.getAddr().length() < 6) {
            showToast("详细地址长度最少3位!");
            return;
        }

        if (StringUtils.isEmpty(provinceCity.getText().toString())) {
            showToast(provinceCity.getHint());
            return;
        }

        if (StringUtils.isEmpty(registerInfo.getFullName())) {
            showToast("请输入真实姓名!");
            return;
        } else if (!ValidateUtils.isFullName(registerInfo.getFullName())) {
            showToast("请输入汉字姓名!");
            return;
        }

        if (StringUtils.isEmpty(identityCard.getText().toString())) {
            showToast("请输入身份证号!");
            return;
        } else if (!ValidateUtils.isValidatedAllIdcard(registerInfo.getIdentityCard())) {
            showToast("请输入正确的身份证号!");
            return;
        }


        Bundle bundle = new Bundle();
        bundle.putSerializable(RegisterInfo.class.getName(), registerInfo);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, ConfirmRegisterActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }


    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void initJsonData() {

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this, "province.json");//获取assets目录下的json文件数据

        provinceInfos = parseData(JsonData);//用Gson 转成实体


        for (int i = 0; i < provinceInfos.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < provinceInfos.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = provinceInfos.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (provinceInfos.get(i).getCityList().get(c).getArea() == null
                        || provinceInfos.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < provinceInfos.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = provinceInfos.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            cityList.add(CityList);

            /**
             * 添加地区数据
             */
            districtList.add(Province_AreaList);
        }
    }


    public ArrayList<ProvinceInfo> parseData(String result) {//Gson 解析
        ArrayList<ProvinceInfo> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceInfo entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceInfo.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }




    @OnClick(R.id.province_city)
    public void onViewClicked() {
        closeSoftKeyboard();
        if( pickerView == null ) {
            pickerView = new OptionsPickerView(this);
            pickerView.setPicker(provinceInfos, cityList, districtList, true);
            pickerView.setTitle("城市选择");
            pickerView.setCyclic(false, false, false);
            pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String province = provinceInfos.get(options1).getPickerViewText();
                    registerInfo.setProvince(province);
                    String address;
                    //  如果是直辖市或者特别行政区只设置市和区/县
                    if ("北京市".equals(province) || "上海市".equals(province)
                            || "天津市".equals(province)
                            || "重庆市".equals(province)
                            || "澳门".equals(province) || "香港".equals(province)) {
                        address = provinceInfos.get(options1).getPickerViewText()
                                + " " + districtList.get(options1).get(option2).get(options3);
                        registerInfo.setCity(districtList.get(options1).get(option2).get(options3));
                    } else {
                        address = provinceInfos.get(options1).getPickerViewText()
                                + " " + cityList.get(options1).get(option2)
                                + " " + districtList.get(options1).get(option2).get(options3);

                        registerInfo.setCity(cityList.get(options1).get(option2) + " " + districtList.get(options1).get(option2).get(options3));

                    }
                    provinceCity.setText(address);
                }
            });
        }
        pickerView.show();
    }
}
