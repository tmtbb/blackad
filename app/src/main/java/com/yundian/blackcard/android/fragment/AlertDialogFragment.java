package com.yundian.blackcard.android.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yundian.blackcard.android.R;
import com.yundian.comm.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yaowang on 2017/5/15.
 */

public class AlertDialogFragment extends BaseDialogFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_line)
    View titleLine;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.button_line)
    View buttonLine;
    @BindView(R.id.ok)
    Button ok;
    private String strTitle;
    private String strContent;
    private String strCancelText;
    private String strOkText;
    private boolean isHideCancel = false;
    private boolean isHideOk = false;
    private boolean isHideTitle = false;
    private View.OnClickListener okClickListener;
    private View.OnClickListener cancelClickListener;


    @Override
    public void initData() {
        super.initData();
        if( isHideTitle ) {
            titleLine.setVisibility(View.GONE);
        }
        bindTextView(title,strTitle,isHideTitle);
        bindTextView(ok,strOkText,isHideOk);
        bindTextView(cancel,strCancelText,isHideCancel);

        if( isHideCancel || isHideOk ) {
            buttonLine.setVisibility(View.GONE);
        }
        content.setText(strContent);
    }

    private  void bindTextView(TextView view,String text,boolean isHide) {
        if( isHide ) {
            view.setVisibility(View.GONE);
        }
        else if(StringUtils.isNotEmpty(text)) {
            view.setText(text);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.comm_alert;
    }

    public AlertDialogFragment setTitle(String strTitle) {
        this.strTitle = strTitle;
        return this;
    }

    public AlertDialogFragment setContent(String strContent) {
        this.strContent = strContent;
        return this;
    }

    public AlertDialogFragment setCancelText(String strCancelText) {
        this.strCancelText = strCancelText;
        return this;
    }

    public AlertDialogFragment setOkText(String strOkText) {
        this.strOkText = strOkText;
        return this;
    }

    public AlertDialogFragment setHideCancel(boolean hideCancel) {
        isHideCancel = hideCancel;
        return this;
    }

    public AlertDialogFragment setHideOk(boolean hideOk) {
        isHideOk = hideOk;
        return this;
    }

    public AlertDialogFragment setHideTitle(boolean hideTitle) {
        isHideTitle = hideTitle;
        return this;
    }

    public AlertDialogFragment setOkClickListener(View.OnClickListener okClickListener) {
        this.okClickListener = okClickListener;
        return this;
    }

    public AlertDialogFragment setCancelClickListener(View.OnClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
        return this;
    }

    @OnClick({R.id.cancel, R.id.ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                if( cancelClickListener != null )
                    cancelClickListener.onClick(view);
                break;
            case R.id.ok:
                dismiss();
                if( okClickListener != null )
                    okClickListener.onClick(view);
                    break;
        }
    }

    public static AlertDialogFragment AlertDialog() {
        return new AlertDialogFragment();
    }
}
