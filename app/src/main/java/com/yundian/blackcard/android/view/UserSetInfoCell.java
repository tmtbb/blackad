package com.yundian.blackcard.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.comm.ui.view.BaseDataFrameLayout;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 14:44
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class UserSetInfoCell extends BaseDataFrameLayout<String> {

    @BindView(R.id.titleText)
    protected TextView titleText;
    @BindView(R.id.lineView)
    protected View lineView;
    @BindView(R.id.cellView)
    protected View cellView;
    @BindView(R.id.contentText)
    protected EditText contentText;
    @BindView(R.id.iconMore)
    protected ImageView iconMore;

    public EditText getContentText() {
        return contentText;
    }

    public UserSetInfoCell(Context context) {
        super(context);
    }

    public UserSetInfoCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UserSetInfoCell);
            if (typedArray.hasValue(R.styleable.UserSetInfoCell_user_info_title))
                titleText.setText(typedArray.getString(R.styleable.UserSetInfoCell_user_info_title));
            if (typedArray.hasValue(R.styleable.UserSetInfoCell_user_info_content))
                contentText.setText(typedArray.getString(R.styleable.UserSetInfoCell_user_info_content));
            if (typedArray.hasValue(R.styleable.UserSetInfoCell_user_info_content_color))
                contentText.setTextColor(typedArray.getColor(R.styleable.UserSetInfoCell_user_info_content_color, getResources().getColor(R.color.color_a6a6a6)));
            if (typedArray.hasValue(R.styleable.UserSetInfoCell_user_info_show_icon)) {
                int visibility = typedArray.getInt(R.styleable.UserSetInfoCell_user_info_show_icon, View.INVISIBLE);
                if (visibility == View.GONE) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(0, 0);
                    layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    iconMore.setLayoutParams(layoutParams);
                    iconMore.setVisibility(INVISIBLE);
                } else {
                    iconMore.setVisibility(visibility);
                }
            }
            if (typedArray.hasValue(R.styleable.UserSetInfoCell_user_info_edit_clickable)) {
                boolean clickable = typedArray.getBoolean(R.styleable.UserSetInfoCell_user_info_edit_clickable, false);
                contentText.setClickable(clickable);
                contentText.setEnabled(clickable);
            }
            if (typedArray.hasValue(R.styleable.UserSetInfoCell_user_info_edit_hint)) {
                String hint = typedArray.getString(R.styleable.UserSetInfoCell_user_info_edit_hint);
                contentText.setHint(hint);
            }

            if (typedArray != null)
                typedArray.recycle();
            typedArray = null;
        }
    }

    @Override
    public void update(String data) {
        contentText.setText(data);
        if (TextUtils.isEmpty(data)
                && iconMore.getVisibility() == View.INVISIBLE
                && contentText.isClickable()) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.titleText);
            layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.iconMore);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            contentText.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        cellView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildViewClick(v, 99, contentText);
            }
        });
    }

    public String getContent() {
        if (contentText != null) {
            return contentText.getText().toString().trim();
        }
        return "";
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_user_set_info;
    }

    public void setLineViewVisibility(int visibility) {
        lineView.setVisibility(visibility);
    }
}
