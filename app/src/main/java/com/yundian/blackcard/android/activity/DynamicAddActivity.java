package com.yundian.blackcard.android.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.adapter.DynamicAddAdapter;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.fragment.AlertDialogFragment;
import com.yundian.blackcard.android.model.DynamicModel;
import com.yundian.blackcard.android.networkapi.NetworkAPIFactory;
import com.yundian.blackcard.android.view.LengthEditView;
import com.yundian.comm.listener.OnItemChildViewClickListener;
import com.yundian.comm.networkapi.listener.OnAPIListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorHelper;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-19 11:42
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicAddActivity extends BaseActivity {

    @BindView(R.id.lengthEditText)
    protected LengthEditView lengthEditText;
    @BindView(R.id.gridView)
    protected GridView gridView;
    private DynamicAddAdapter dynamicAddAdapter;
    private ArrayList<String> selectedList = new ArrayList<>();
    private MultiImageSelectorHelper selectorHelper;
    private static final int MAX_NUM = 9;
    private String cirleId = "0";


    @Override
    public int getLayoutId() {
        return R.layout.ac_dynamic_add;
    }

    @Override
    protected boolean isShowBackButton() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("此刻");
        setSubTitle("发布");
        lengthEditText.update("此刻的想法...");
        dynamicAddAdapter = new DynamicAddAdapter(context);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(dynamicAddAdapter);
        cirleId = getIntent().getStringExtra(ActionConstant.IntentKey.TRIBEID_ID);
    }

    @Override
    public void initData() {
        super.initData();
        selectedList.add("");
        dynamicAddAdapter.addList(selectedList);
        dynamicAddAdapter.notifyDataSetChanged();
        selectorHelper = new MultiImageSelectorHelper(this);
    }

    @Override
    public void initListener() {
        super.initListener();
        dynamicAddAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                switch (action) {
                    case ActionConstant.Action.DYNAMIC_RELEASE_DELETE:
                        selectedList.remove(obj);
                        addEmptyIfNeed(selectedList);
                        dynamicAddAdapter.notifyDataSetChanged();
                        break;
                    case ActionConstant.Action.DYNAMIC_RELEASE_ICON:
                        selectorHelper.setmSelectPath(removeEmptyIfNeed(selectedList));
                        selectorHelper.pickImage(false, MAX_NUM, false);
                        break;
                }
            }
        });
    }

    public ArrayList<String> removeEmptyIfNeed(ArrayList<String> selectedList) {
        ArrayList<String> resultList = new ArrayList<>();
        for (int i = 0; i < selectedList.size(); i++) {
            if (!TextUtils.isEmpty(selectedList.get(i))) {
                resultList.add(selectedList.get(i));
            }
        }
        return resultList;
    }

    public List<String> addEmptyIfNeed(ArrayList<String> selectedList) {
        if (selectedList != null && selectedList.size() < MAX_NUM) {
            boolean hasEmpty = false;
            for (int i = 0; i < selectedList.size(); i++) {
                if (TextUtils.isEmpty(selectedList.get(i))) {
                    hasEmpty = true;
                    break;
                }
            }
            if (!hasEmpty) {
                selectedList.add(selectedList.size(), "");
            }

        }
        return selectedList;
    }

    @OnClick(R.id.toolbar_subtitle)
    protected void dynamicAddClick(View view) {
        if (lengthEditText.getLength() == 0 && removeEmptyIfNeed(selectedList).size() == 0) {
            showToast("请填写内容或图片");
            return;
        }

        showLoader();
        NetworkAPIFactory.getDynamicService().dynamicAdd(cirleId, lengthEditText.getContent(), removeEmptyIfNeed(selectedList), new OnAPIListener<DynamicModel>() {
            @Override
            public void onError(Throwable ex) {
                onShowError(ex);
            }

            @Override
            public void onSuccess(DynamicModel dynamicModel) {
                closeLoader();
                showToast("发布成功");
                Intent intent = new Intent();
                intent.putExtra(ActionConstant.IntentKey.DYNAMIC, dynamicModel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MultiImageSelectorHelper.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> resultList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                selectedList.clear();
                selectedList.addAll(resultList);
                dynamicAddAdapter.setList(addEmptyIfNeed(selectedList));
                dynamicAddAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MultiImageSelectorHelper.REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectorHelper.pickImage(false, MAX_NUM, true);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (!isCreateExitDialog()) {
                return super.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!isCreateExitDialog()) {
            super.onBackPressed();
        }
    }

    private synchronized boolean isCreateExitDialog() {
        if (lengthEditText.getLength() > 0 || removeEmptyIfNeed(selectedList).size() > 0) {
            AlertDialogFragment.AlertDialog()
                    .setTitle("提示")
                    .setContent("是否取消发布")
                    .setOkClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .show(getSupportFragmentManager());
            return true;
        } else
            return false;
    }
}
