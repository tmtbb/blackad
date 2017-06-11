package me.nereo.multi_image_selector.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import java.io.File;


public class FileCacheUtil {

    private static FileCacheUtil INSTANCE;


    public synchronized static FileCacheUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileCacheUtil();
        }
        return INSTANCE;
    }

    public String getCacheDir(Context con, String dir) {
        String strDir = getDir(getCacheDir(con), dir);
        makeDir(strDir);
        return strDir;
    }

    public String getFilesDir(Context con, String dir) {
        String strDir = getDir(getFilesDir(con), dir);
        makeDir(strDir);
        return strDir;
    }


    private void makeDir(String dir) {
        if (dir != null) {
            String makeDir = dir;
            if (makeDir.lastIndexOf('/') == makeDir.length() - 1)
                makeDir = makeDir.substring(0, makeDir.lastIndexOf('/'));
            if (!(new File(makeDir).exists())) {
                String[] dirs = makeDir.split("/");
                makeDir = "";
                for (String str : dirs) {
                    if (str != null && str.length() > 0) {
                        makeDir += "/" + str;
                        File fileDir = new File(makeDir);
                        if (!fileDir.exists())
                            fileDir.mkdir();
                    }
                }
            }
        }
    }

    private String getDir(File file, String path) {
        String dir = null;
        if (file != null) {
            dir = file.getAbsolutePath();
            if (path != null) {

                if (path.indexOf('/') == 0)
                    dir += path;
                else
                    dir += "/" + path;
            }
        }
        return dir;
    }

    private File getCacheDir(Context con) {
        File file = con.getExternalCacheDir();
        if (file == null || !Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            file = con.getCacheDir();
        return file;
    }

    private File getFilesDir(Context con) {
        File file = con.getExternalFilesDir(null);
        if (file == null || !Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            file = con.getFilesDir();
        return file;
    }


    /**
     * 创建绝对路径(包含多级)
     *
     * @param header 绝对路径的前半部分(已存在)
     * @param tail   绝对路径的后半部分(第一个和最后一个字符不能是/，格式：123/258/456)
     */
    public static void makeDir(String header, String tail) {
        String[] sub = tail.split("/");
        File dir = new File(header);
        for (int i = 0; i < sub.length; i++) {
            if (!dir.exists()) {
                dir.mkdir();
            }
            File dir2 = new File(dir + File.separator + sub[i]);
            if (!dir2.exists()) {
                dir2.mkdir();
            }
            dir = dir2;
        }
    }


    public long getUsedSpaceforImageCache(Context context) {
        File cacheDir = new File(FileCacheUtil.getInstance().getCacheDir(context, "cache_images"));
        long size = 0;
        if (cacheDir != null && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                size += file.length();
            }
        }
        return size;
    }

    public long deleteImageCache(Context context) {
        File cacheDir = new File(FileCacheUtil.getInstance().getCacheDir(context, "cache_images"));
        if (cacheDir != null && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                file.delete();
            }
        }
        cacheDir.delete();
        cacheDir.mkdir();
        return 0;
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void deleteCacheFile(Context context) {
        try {
            File cacheDir = getCacheDir(context);
            if (cacheDir != null && cacheDir.isDirectory()) {
                for (File file : cacheDir.listFiles()) {
                    file.delete();
                }
            }
            if(cacheDir!=null) {
                cacheDir.delete();
                cacheDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
