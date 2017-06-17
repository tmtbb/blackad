package com.yundian.blackcard.android.view;

import android.content.Context;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.util.TimeUtil;
import com.yundian.comm.ui.view.BaseDataFrameLayout;
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
public class DynamicCommentView extends BaseDataFrameLayout<DynamicCommentModel> {

    @BindView(R.id.userIcon)
    protected ImageView userIcon;
    @BindView(R.id.userName)
    protected TextView userName;
    @BindView(R.id.createTime)
    protected TextView createTime;
    @BindView(R.id.contentText)
    protected TextView contentText;

    public DynamicCommentView(Context context) {
        super(context);
    }

    public DynamicCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(DynamicCommentModel data) {
        if (data != null) {
            //header
            Glide.with(context).load(data.getHeadUrl()).placeholder(R.mipmap.user_head_def).centerCrop().into(userIcon);

            //username
            userName.setText(data.getNickName());

            //create time
            createTime.setText(TimeUtil.formatDate(data.getCreateTime()));

            //message
            contentText.setText(data.getComment());

        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_dynamic_comment;
    }
}
