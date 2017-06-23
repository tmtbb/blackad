package me.nereo.multi_image_selector;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 2017/5/11.
 */

public class MultiImageSelectorHelper {
    protected Activity mActivity;

    public MultiImageSelectorHelper(Activity activity) {
        mActivity = activity;
    }

    public static final int REQUEST_IMAGE = 2;
    public static final int REQUEST_IMAGE_CLIP = 3;
    public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private boolean isClip = true;
    private ArrayList<String> mSelectPath = new ArrayList<>();

    public void pickImage(boolean isSingle, int maxNum) {
        pickImage(isSingle, maxNum, false);
    }

    public void pickImage(boolean isSingle, int maxNum, boolean isInit) {
        pickImage(isSingle, maxNum, isInit, 200, 200);
    }


    public void setClip(boolean clip) {
        isClip = clip;
    }

    public void pickImage(boolean isSingle, int maxNum, boolean isInit, int clipWidth, int clipHeight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, mActivity.getString(R.string.mis_permission_rationale), REQUEST_STORAGE_READ_ACCESS_PERMISSION);
            }
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.CAMERA, mActivity.getString(R.string.mis_permission_rationale), REQUEST_STORAGE_READ_ACCESS_PERMISSION);
            }
        } else {
            MultiImageSelector selector = MultiImageSelector.create();
            selector.showCamera(true);
            selector.count(maxNum);
            selector.clipHeight(clipHeight);
            selector.clipWidth(clipWidth);
            selector.isClip(isClip);
            if (isSingle) {
                selector.single();
            } else {
                selector.multi();
            }

            if (isInit) {
                mSelectPath.clear();
            }

            selector.origin(mSelectPath);
            selector.start(mActivity, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
            new AlertDialog.Builder(mActivity)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mActivity, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{permission}, requestCode);
        }
    }

    public List<String> getmSelectPath() {
        return mSelectPath;
    }

    public void setmSelectPath(ArrayList<String> mSelectPath) {
        this.mSelectPath = mSelectPath;
    }
}
