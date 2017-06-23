package com.yundian.blackcard.android.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.CircleMessageImgModel;
import com.yundian.comm.listener.OnImageViewTouchListener;
import com.yundian.comm.ui.view.BaseFrameLayout;
import com.yundian.comm.util.DisplayUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/3/9 0009
 * @describe : DynamicContentImgView
 */
public class DynamicContentImgView extends BaseFrameLayout {

    @BindView(R.id.rl_img)
    protected RelativeLayout rl_img;
    private int childWidth;
    private List<CircleMessageImgModel> dynamicImageEntities;
    private int imageWidth;
    private int maxImageHeight;
    private int maxImageWidth;
    private Pattern pattern = Pattern.compile("_[\\d]+X[\\d]+_");

    public DynamicContentImgView(Context context) {
        super(context);
    }

    public DynamicContentImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_dynamic_content_img;
    }

    @Override
    protected void initView() {
        super.initView();
        imageWidth = (int) getResources().getDimension(R.dimen.dynamic_image_size);
        maxImageHeight = (int) (imageWidth * 2.2);
        maxImageWidth = (int) (imageWidth * 1.1);
    }

    @Override
    protected void initData() {
        super.initData();
        childWidth = (DisplayUtil.getScreenWidth(getContext()) - 2 * DisplayUtil.dip2px(28, getContext())
                - 2 * DisplayUtil.dip2px(4, getContext()) - DisplayUtil.dip2px(41, getContext())) / 3;
    }

    public void update(List<CircleMessageImgModel> dynamicImageEntities) {
        hideViews();
        if (dynamicImageEntities == null || dynamicImageEntities.size() == 0) {
            return;
        }
        this.dynamicImageEntities = dynamicImageEntities;
        int size = this.dynamicImageEntities.size();
        if (size > 9) {
            size = 9;
        }
        if (size == 4) {
            showFourImgs();
        } else if (size == 1) {
            showView(rl_img.getChildAt(0), true, 0);
        } else {
            showMoreImgs(size);
        }
    }

    /**
     * 显示多张图片
     *
     * @param size
     */
    private void showMoreImgs(int size) {
        for (int i = 0; i < size; i++) {
            showView(rl_img.getChildAt(i), false, i);
        }
    }

    /**
     * 显示四张图片
     */
    private void showFourImgs() {
        showView(rl_img.getChildAt(0), false, 0);
        showView(rl_img.getChildAt(1), false, 1);
        showView(rl_img.getChildAt(3), false, 2);
        showView(rl_img.getChildAt(4), false, 3);
    }

    /**
     * 显示图片
     *
     * @param view
     * @param isSingleImg
     * @param position
     */
    private void showView(View view, boolean isSingleImg, final int position) {
        if (!isSingleImg) {
            setViewLayoutParams(view, childWidth, childWidth);
        } else {
            resizeSingleImg(view);
        }
        final ImageView imageView = (ImageView) view;
        String url = dynamicImageEntities.get(position).getImgUrl();
        if (url.indexOf("http") != 0) {
            url = "file://" + url;
        }

        Glide.with(context).load(url).placeholder(new ColorDrawable(getResources().getColor(R.color.color_f8f8f8))).centerCrop().into(imageView);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildViewClick(v, ActionConstant.Action.DYNAMIC_PIC, position);
            }
        });
        view.setOnTouchListener(OnImageViewTouchListener.getInstance());
    }

    /**
     * 设置单张图片大小
     *
     * @param view
     */
    private void resizeSingleImg(View view) {
        try {
            String size[] = dynamicImageEntities.get(0).getSize().split("x");
            int width = Integer.parseInt(size[0]);
            int height = Integer.parseInt(size[1]);
            if (width < height) {
                height = maxImageWidth * height / width;
                width = maxImageWidth;
            } else if (height < width) {
                height = maxImageHeight * height / width;
                width = maxImageHeight;
            } else {
                width = maxImageHeight * width / height;
                height = maxImageHeight;
            }
            width = width > maxImageHeight ? maxImageHeight : width;
            height = height > maxImageHeight ? maxImageHeight : height;
            setViewLayoutParams(view, width, height);
        } catch (Exception e) {
            setViewLayoutParams(view, 300, 300);
        }

    }

    /**
     * 设置view的大小
     *
     * @param view
     * @param width
     * @param height
     */
    private void setViewLayoutParams(View view, int width, int height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    /**
     * 隐藏控件
     */
    private void hideViews() {
        for (int i = 0; i < rl_img.getChildCount(); i++) {
            rl_img.getChildAt(i).setVisibility(View.GONE);
        }
    }

}
