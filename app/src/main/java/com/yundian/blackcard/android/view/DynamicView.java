package com.yundian.blackcard.android.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.comm.listener.OnChildViewClickListener;
import com.yundian.comm.ui.view.BaseDataFrameLayout;
import com.yundian.comm.util.DisplayUtil;
import com.yundian.comm.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-17 17:25
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicView extends BaseDataFrameLayout<DynamicModel> {

    @BindView(R.id.topText)
    protected TextView topText;
    @BindView(R.id.userIcon)
    protected ImageView userIcon;
    @BindView(R.id.userName)
    protected TextView userName;
    @BindView(R.id.createTime)
    protected TextView createTime;
    @BindView(R.id.contentText)
    protected TextView contentText;
    @BindView(R.id.dynamicContentImgView)
    protected DynamicContentImgView dynamicContentImgView;
    @BindView(R.id.praiseText)
    protected DrawableTextView praiseText;
    @BindView(R.id.commentText)
    protected TextView commentText;
    @BindView(R.id.moreText)
    protected TextView moreText;
    @BindView(R.id.spaceView)
    protected View spaceView;

    public DynamicView(Context context) {
        super(context);
    }

    public DynamicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(DynamicModel data) {
        if (data != null) {

            //header
            Glide.with(context).load(data.getHeadUrl()).placeholder(R.mipmap.user_head_def).centerCrop().into(userIcon);

            //is top
            topText.setVisibility(1 == data.getIsTop() ? VISIBLE : GONE);

            //username
            userName.setText(data.getNickName());

            //create time
            createTime.setText(TimeUtil.formatDate(data.getCreateTime()));

            //message
            if (!TextUtils.isEmpty(data.getMessage())) {
                contentText.setText(data.getMessage());
                contentText.setVisibility(VISIBLE);
                if(data.getCircleMessageImgs().size()==0){
                    spaceView.setVisibility(VISIBLE);
                }else {
                    spaceView.setVisibility(GONE);
                }
            } else {
                contentText.setVisibility(GONE);
            }

            //image
            if (data.getCircleMessageImgs().size() > 0) {
                dynamicContentImgView.setVisibility(VISIBLE);
                dynamicContentImgView.update(data.getCircleMessageImgs());
                if (TextUtils.isEmpty(data.getMessage())) {
                    spaceView.setVisibility(GONE);
                } else {
                    spaceView.setVisibility(VISIBLE);
                }
            } else {
                dynamicContentImgView.setVisibility(GONE);
            }

            //praise
            praiseText.setText(String.valueOf(data.getLikeNum()));
            if (data.getIsLike() == 1) {
                praiseText.setDrawableLeft(R.mipmap.icon_praised);
            } else {
                praiseText.setDrawableLeft(R.mipmap.icon_praise);
            }

            //comment count
            commentText.setText(String.valueOf(data.getCommentNum()));


        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_dynamic;
    }

    @Override
    protected void initListener() {
        super.initListener();
        dynamicContentImgView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                DynamicView.this.onChildViewClick(childView, action, obj);
            }
        });
    }

    @OnClick(value = {R.id.praiseText, R.id.commentText, R.id.moreText, R.id.rootLayout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.rootLayout:
                onChildViewClick(view, ActionConstant.Action.DYNAMIC_DETAIL);
                break;
            case R.id.praiseText:
                onChildViewClick(view, ActionConstant.Action.DYNAMIC_PRAISE);
                break;

            case R.id.commentText:
                onChildViewClick(view, ActionConstant.Action.DYNAMIC_COMMENT);
                break;

            case R.id.moreText:
                onChildViewClick(view, ActionConstant.Action.DYNAMIC_MORE);
                break;
        }
    }
    public void setContentMaxLines(int maxlines){
        if(contentText != null)
            contentText.setMaxLines(maxlines);
    }
}
