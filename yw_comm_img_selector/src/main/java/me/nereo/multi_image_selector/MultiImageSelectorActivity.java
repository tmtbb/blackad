package me.nereo.multi_image_selector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import me.nereo.multi_image_selector.utils.FileUtils;

/**
 * Multi image selector
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 * Updated by nereo on 2016/5/18.
 */
public class MultiImageSelectorActivity extends AppCompatActivity
        implements MultiImageSelectorFragment.Callback {

    // Single choice
    public static final int MODE_SINGLE = 0;
    // Multi choice
    public static final int MODE_MULTI = 1;

    /**
     * Max image size，int，{@link #DEFAULT_IMAGE_SIZE} by default
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * Select mode，{@link #MODE_MULTI} by default
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * Whether show camera，true by default
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * Result data set，ArrayList&lt;String&gt;
     */
    public static final String EXTRA_RESULT = "select_result";

    public static final String EXTRA_CLIP_WIDTH = "clip_width";
    public static final String EXTRA_CLIP_HEIGHT = "clip_height";

    public static final String EXTRA_IS_CLIP = "is_clip";
    /**
     * Original data set
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    // Default image size
    private static final int DEFAULT_IMAGE_SIZE = 9;

    private ArrayList<String> resultList = new ArrayList<>();
    private Button mSubmitButton;
    private int mDefaultCount = DEFAULT_IMAGE_SIZE;

    private int mClipWidth = 200;
    private int mClipHeight = 200;
    private File imageCropFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MIS_NO_ACTIONBAR);
        setContentView(R.layout.mis_activity_default);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_IMAGE_SIZE);
        mClipHeight = intent.getIntExtra(EXTRA_CLIP_HEIGHT, 400);
        mClipWidth = intent.getIntExtra(EXTRA_CLIP_WIDTH, 400);
        final int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        final boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }

        mSubmitButton = (Button) findViewById(R.id.commit);
        if (mode == MODE_MULTI) {
            updateDoneText(resultList);
            mSubmitButton.setVisibility(View.VISIBLE);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (resultList != null && resultList.size() > 0) {
                        // Notify success
                        Intent data = new Intent();
                        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                        setResult(RESULT_OK, data);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                }
            });
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
            bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
            bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
            bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                    .commit();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏颜色
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update done button by select image data
     *
     * @param resultList selected image data
     */
    private void updateDoneText(ArrayList<String> resultList) {
        int size = 0;
        if (resultList == null || resultList.size() <= 0) {
            mSubmitButton.setText(R.string.mis_action_done);
            mSubmitButton.setEnabled(false);
        } else {
            size = resultList.size();
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setText(getString(R.string.mis_action_button_string,
                getString(R.string.mis_action_done), size, mDefaultCount));
    }

    @Override
    public void onSingleImageSelected(String path) {
        startPhotoZoom(path);
    }

    @Override
    public void onImageSelected(String path) {
        if (!resultList.contains(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }

    private void startPhotoZoom(String path) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(FileUtils.getFileUrl(this, new File(path)), "image/*");
        intent.putExtra("crop", "true");
//        intent.putExtra("scale", true);
        intent.putExtra("aspectX", mClipWidth);
        intent.putExtra("aspectY", mClipHeight);
        intent.putExtra("outputX", mClipWidth);
        intent.putExtra("outputY", mClipHeight);
        intent.putExtra("noFaceDetection", true);
//        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (imageCropFile == null) {
            try {
                imageCropFile = FileUtils.createTmpFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (imageCropFile.exists()) {
            imageCropFile.delete();
        }

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            intent.putExtra("return-data", false);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getFileUrl(this, imageCropFile));
//        }


        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageCropFile));

        startActivityForResult(intent, MultiImageSelectorHelper.REQUEST_IMAGE_CLIP);
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            // notify system the image has change
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
//
//            Intent data = new Intent();
//            resultList.add(imageFile.getAbsolutePath());
//            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
//            setResult(RESULT_OK, data);
//            finish();

            startPhotoZoom(imageFile.getAbsolutePath());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MultiImageSelectorHelper.REQUEST_IMAGE_CLIP) {
            if (resultCode == RESULT_OK) {
                if (imageCropFile == null)
                    return;
                String filePath = null;
                if (imageCropFile.exists()) {
                    filePath = imageCropFile.getAbsolutePath();
                } else {
                    filePath = getFilePath(data);
                }
                if (filePath == null)
                    return;

                if (resultList != null) {
                    resultList.add(filePath);
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        }
    }

    @NonNull
    private String getFilePath(Intent data) {
        Bitmap bmap = data.getParcelableExtra("data");
        if (bmap == null)
            return null;
        FileOutputStream foutput = null;
        String filePath = imageCropFile.getAbsolutePath();
        try {
            foutput = new FileOutputStream(filePath);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, foutput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != foutput) {
                try {
                    foutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }
}
