package cn.lsmya.smart.utils;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.text.DecimalFormat;

public class AppCacheUtils {
    private static AppCacheUtils appCacheUtils;

    public static AppCacheUtils getInstance() {
        if (appCacheUtils == null) {
            synchronized (AppCacheUtils.class) {
                if (appCacheUtils == null) {
                    appCacheUtils = new AppCacheUtils();
                }
            }
        }
        return appCacheUtils;
    }

    File filesDir, cacheDir;

    /**
     * 获取总缓存大小
     *
     * @param activity
     * @return
     */
    public String getAppCache(Activity activity) {
        long fileSize = 0;
        filesDir = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        cacheDir = activity.getExternalCacheDir();
        fileSize += getDirSize(activity.getFilesDir());
        fileSize += getDirSize(activity.getCacheDir());
        fileSize += getDirSize(filesDir);
        fileSize += getDirSize(cacheDir);
        String fileSizeStr = formatFileSize(fileSize);
        return fileSizeStr;
    }

    /**
     * 获取文件大小(字节为单位)
     *
     * @param dir
     * @return
     */
    private long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();//文件的长度就是文件的大小
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 格式化文件长度
     *
     * @param fileSize
     * @return
     */
    private String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#0.00");//表示小数点前至少一位,0也会显示,后保留两位
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public void clearAppCache(Activity activity, OnClearCacheFolderListener listener) {
        filesDir = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        cacheDir = activity.getExternalCacheDir();
        new Thread(() -> {
            try {
                clearCacheFolder(activity.getFilesDir(), System.currentTimeMillis());
                clearCacheFolder(activity.getCacheDir(), System.currentTimeMillis());
                clearCacheFolder(filesDir, System.currentTimeMillis());
                clearCacheFolder(cacheDir, System.currentTimeMillis());
                listener.onSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                listener.onFail(e);
            }
        }).start();
    }

    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    public interface OnClearCacheFolderListener {
        void onSuccess();
        void onFail(Exception e);
    }
}
