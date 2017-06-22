package com.yundian.blackcard.android.helper;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 16:45
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CityPickerHelper {

    private OnCitySelectedListener onCitySelectedListener;

    public void selectCity(Context context, final OnCitySelectedListener onCitySelectedListener) {
        this.onCitySelectedListener = onCitySelectedListener;
        CityPicker cityPicker = new CityPicker.Builder(context).textSize(15)
                .titleTextColor("#434343")
                .cancelTextColor("#E4A63F")
                .confirTextColor("#E4A63F")
                .backgroundPop(0xa0000000)
                .province("浙江省")
                .city("杭州市")
                .district("西湖区")
                .textColor(Color.parseColor("#434343"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .build();

        cityPicker.show();

        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                if (onCitySelectedListener != null) {
                    onCitySelectedListener.onCitySelected(citySelected);
                }
            }

            @Override
            public void onCancel() {
                if (onCitySelectedListener != null) {
                    // onCitySelectedListener.onCityCancel();
                }
            }
        });
    }

    public interface OnCitySelectedListener {
        void onCitySelected(String... citySelected);

        //void onCityCancel();
    }
}
