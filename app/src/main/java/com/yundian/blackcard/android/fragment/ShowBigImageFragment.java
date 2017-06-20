package com.yundian.blackcard.android.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yundian.blackcard.android.R;
import com.yundian.blackcard.android.constant.ActionConstant;
import com.yundian.blackcard.android.view.ActionSheetDialog;
import com.yundian.blackcard.android.view.LoaderLayout;
import com.yundian.blackcard.android.view.photo.PhotoView;
import com.yundian.blackcard.android.view.photo.PhotoViewAttacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import me.nereo.multi_image_selector.utils.FileCacheUtil;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-20 11:29
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ShowBigImageFragment extends BaseFragment implements PhotoViewAttacher.OnViewTapListener {
    @BindView(R.id.imv_big)
    protected PhotoView imv_big;
    @BindView(R.id.view_progress)
    protected LoaderLayout view_progress;
    private int imageIndex;

    @Override
    public int getLayoutId() {
        return R.layout.fm_showbigimage;
    }

    @Override
    public void initView() {
        super.initView();
        String imageUrl = getArguments().getString(ActionConstant.IntentKey.IMGS_BIG);
        imageIndex = getArguments().getInt(ActionConstant.IntentKey.IMG_BIG_INDEX);
        view_progress.setVisibility(View.VISIBLE);
        imv_big.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        Glide.with(context).load(imageUrl).placeholder(new ColorDrawable(getResources().getColor(R.color.black))).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                view_progress.setVisibility(View.GONE);
                imv_big.setImageDrawable(resource);
                return false;
            }
        }).fitCenter().into(imv_big);


    }


    @Override
    public void initListener() {
        super.initListener();
        imv_big.setOnViewTapListener(this);
    }


    @Override
    public void onViewTap(View view, float x, float y) {
        if (onViewTapListener != null) {
            onViewTapListener.onViewTap(imageIndex);
        }

    }


    public OnViewTapListener onViewTapListener;

    public interface OnViewTapListener {
        void onViewTap(int index);
    }

    public void setOnViewTapListener(OnViewTapListener onViewTapListener) {
        this.onViewTapListener = onViewTapListener;
    }


    private void setImageOnLongClick() {
        imv_big.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imv_big.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new ActionSheetDialog(getContext())
                                .builder()
                                .setTitle("请选择操作")
                                .setCancelable(false)
                                .setCanceledOnTouchOutside(false)
                                .addSheetItem("保存", ActionSheetDialog.SheetItemColor.Blue,
                                        new ActionSheetDialog.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(int which) {
                                                if (imv_big.getDrawable() instanceof BitmapDrawable) {
                                                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imv_big.getDrawable();
                                                    saveImage(bitmapDrawable.getBitmap());
                                                }
                                            }
                                        }).show();
                        return true;
                    }
                });
                return false;
            }
        });
    }

    public void saveImage(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        String path = FileCacheUtil.getInstance().getCacheDir(context, "image");
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
    }
}
