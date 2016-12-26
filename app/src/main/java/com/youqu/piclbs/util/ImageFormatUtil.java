package com.youqu.piclbs.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hjq on 16-12-20.
 * 强制将图片转换为jpg
 */

public class ImageFormatUtil {
    public static String getImageUri(Activity activity, String uri){
        Bitmap bitmap = BitmapFactory.decodeFile(uri);
        File f = getTargetFile(System.currentTimeMillis() + ".jpg");
        if (f == null) {
            Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifySystemUpdatePictures(activity,f);
        return f.getAbsolutePath();
    }

    private static File getTargetFile(String bitName) {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (storageDir == null) {
            return null;
        }
        /**
         * 目标存储目录
         */
        File imagesDir = new File(storageDir, "piclbs_Images");
        imagesDir.mkdirs();
        /**
         * 返回目标文件
         */
        return new File(imagesDir, bitName);
    }
    /**
     * 通知系统图库更新
     */
    private static void notifySystemUpdatePictures(Context context, File targetFile) {
        Intent intent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(targetFile));
        context.sendBroadcast(intent);
    }
}
