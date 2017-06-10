package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.manager.UserDetailManager;
import com.yundian.blackcard.android.model.UploadInfo;
import com.yundian.blackcard.android.model.UserDetailModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.view.ActionSheetDialog;
import com.yundian.blackcard.android.view.UserSetInfoCell;
import com.yundian.comm.listener.OnChildViewClickListener;
import com.yundian.comm.listener.OnRefreshListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;
import com.yundian.comm.util.SPUtils;
import com.yundian.comm.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorHelper;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-07 14:34
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class UserSetInfoActivity extends BaseRefreshActivity {

    @BindView(R.id.nicknameCell)
    protected UserSetInfoCell nicknameCell;
    @BindView(R.id.contentView)
    protected ScrollView contentView;
    @BindView(R.id.setupPwdCell)
    protected UserSetInfoCell setupPwdCell;
    @BindView(R.id.blackCardNoCell)
    protected UserSetInfoCell blackCardNoCell;
    @BindView(R.id.fullNameCell)
    protected UserSetInfoCell fullNameCell;
    @BindView(R.id.sexCell)
    protected UserSetInfoCell sexCell;
    @BindView(R.id.companyCell)
    protected UserSetInfoCell companyCell;
    @BindView(R.id.positionCell)
    protected UserSetInfoCell positionCell;
    @BindView(R.id.identityCardCell)
    protected UserSetInfoCell identityCardCell;
    @BindView(R.id.phoneNumCell)
    protected UserSetInfoCell phoneNumCell;
    @BindView(R.id.emailCell)
    protected UserSetInfoCell emailCell;
    @BindView(R.id.headerIcon)
    protected ImageView headerIcon;
    private MultiImageSelectorHelper selectorHelper;
    private UserDetailModel userDetailModel;

    @Override
    public int getLayoutId() {
        return R.layout.ac_user_set_info;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("个人资料");
        setSubTitle("完成");
        selectorHelper = new MultiImageSelectorHelper(this);
        contentView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initListener() {
        super.initListener();
        setupPwdCell.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                Intent intent = new Intent(context, SetupPasswordActivity.class);
                intent.putExtra(ActionConstant.IntentKey.PHONE, userDetailModel.getPhoneNum());
                startActivity(intent);
            }
        });
        sexCell.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                new ActionSheetDialog(context)
                        .builder()
                        .setTitle("请选择性别")
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                sexCell.update("男");
                            }
                        }).addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        sexCell.update("女");
                    }
                }).addSheetItem("保密", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                sexCell.update("保密");
                            }
                        }).show();
            }
        });


        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetworkAPIFactory.getUserService().userDetail(new OnAPIListener<UserDetailModel>() {
                    @Override
                    public void onError(Throwable ex) {
                        onShowError(ex);
                    }

                    @Override
                    public void onSuccess(UserDetailModel userDetailModel) {
                        UserDetailManager.getInstance().notifyUserDetailChanged(userDetailModel);
                        getRefreshController().refreshComplete();
                        UserSetInfoActivity.this.userDetailModel = userDetailModel;
                        contentView.setVisibility(View.VISIBLE);
                        Glide.with(context).load(userDetailModel.getHeadUrl()).centerCrop().placeholder(R.mipmap.user_head_def).into(headerIcon);
                        blackCardNoCell.update(userDetailModel.getBlackCardNo());
                        fullNameCell.update(userDetailModel.getFullName());
                        nicknameCell.update(userDetailModel.getNickName());
                        sexCell.update(userDetailModel.getSex());
                        companyCell.update(userDetailModel.getCompany());
                        positionCell.update(userDetailModel.getPosition());
                        identityCardCell.update(StringUtils.replaceInfo(4, userDetailModel.getIdentityCard()));
                        phoneNumCell.update(StringUtils.replaceInfo(3, userDetailModel.getPhoneNum()));
                        emailCell.update(userDetailModel.getEmail());
                    }
                });
            }
        });
    }


    @OnClick(value = {R.id.logoutText, R.id.headerLayout, R.id.toolbar_subtitle})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.logoutText:
                SPUtils.remove(UserSetInfoActivity.this, "UserToken");
                next(LoginActivity.class);
                break;
            case R.id.headerLayout:
                selectorHelper.pickImage(true, 1, true);
                break;
            case R.id.toolbar_subtitle:
                showLoader();
                NetworkAPIFactory.getUserService().userEdit(findModelByView(), new OnAPIListener<Object>() {
                    @Override
                    public void onError(Throwable ex) {
                        onShowError(ex);
                    }

                    @Override
                    public void onSuccess(Object o) {
                        closeLoader();
                        showToast("修改完成");
                        getRefreshController().refreshBegin();
                    }
                });
                break;
        }

    }


    private UserDetailModel findModelByView() {
        UserDetailModel model = new UserDetailModel();
        model.setPosition(positionCell.getContent());
        model.setCompany(companyCell.getContent());
        model.setSex(sexCell.getContent());
        model.setIdentityCard(userDetailModel.getIdentityCard());
        model.setEmail(emailCell.getContent());
        model.setNickName(nicknameCell.getContent());
        if (selectorHelper.getmSelectPath().size() > 0) {
            model.setHeadUrl(userDetailModel.getHeadUrl());
        }
        return model;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MultiImageSelectorHelper.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                selectorHelper.setmSelectPath(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                showLoader();
                NetworkAPIFactory.getCommService().upload(selectorHelper.getmSelectPath(), new OnAPIListener<List<UploadInfo>>() {
                    @Override
                    public void onError(Throwable ex) {
                        onShowError(ex);
                    }

                    @Override
                    public void onSuccess(List<UploadInfo> uploadInfos) {
                        closeLoader();
                        userDetailModel.setHeadUrl(uploadInfos.get(0).getUrl());
                        Glide.with(context).load(selectorHelper.getmSelectPath().get(0)).placeholder(R.mipmap.user_head_def).into(headerIcon);
                    }
                });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MultiImageSelectorHelper.REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectorHelper.pickImage(true, 1, true);
            }
        }
    }
}