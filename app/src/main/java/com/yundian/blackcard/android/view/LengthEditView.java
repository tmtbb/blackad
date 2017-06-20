package com.yundian.blackcard.android.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.comm.ui.view.BaseDataFrameLayout;

import butterknife.BindView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-19 10:37
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class LengthEditView extends BaseDataFrameLayout<String> {


    @BindView(R.id.commentEdit)
    protected EditText commentEdit;
    @BindView(R.id.countText)
    protected TextView countText;

    public LengthEditView(Context context) {
        super(context);
    }

    public LengthEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(String data) {
        commentEdit.setHint(data);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_length_edit;
    }

    @Override
    public void initListener() {
        super.initListener();
        commentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCountText();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateCountText() {
        String comment = commentEdit.getText().toString().trim();
        countText.setText(comment.length() + "/300");
    }

    public String getContent() {
        return commentEdit.getText().toString().trim();
    }

    public int getLength() {
        return commentEdit.getText().toString().trim().length();
    }
}
