package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.manager.UserDetailManager;
import com.yundian.blackcard.android.model.DynamicCommentModel;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.view.LengthEditView;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-18 14:59
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicCommentActivity extends BaseActivity {

    @BindView(R.id.lengthEditText)
    protected LengthEditView lengthEditText;
    private DynamicModel dynamicModel;

    @Override
    public int getLayoutId() {
        return R.layout.ac_dynamic_comment;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("评论");
        setSubTitle("提交");
        lengthEditText.update("写下您的评论...");
        dynamicModel = (DynamicModel) getIntent().getSerializableExtra(ActionConstant.IntentKey.DYNAMIC);
    }

    @OnClick(value = {R.id.toolbar_subtitle})
    protected void onClick(View view) {
        if (lengthEditText.getLength() <= 0) {
            showToast("请输入评论内容");
            return;
        }
        NetworkAPIFactory.getDynamicService().commentAdd(dynamicModel.getId(), lengthEditText.getContent(), new OnAPIListener<DynamicCommentModel>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
            }

            @Override
            public void onSuccess(DynamicCommentModel model) {
                closeSoftKeyboard();
                Intent intent = new Intent();
                intent.putExtra(ActionConstant.IntentKey.DYNAMIC_COMMENT, model);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        closeSoftKeyboard();
        super.finish();
    }
}
