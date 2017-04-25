package app.soundmail.MyUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sound on 2017/4/25.
 */

public class CommonUtils {

    public static String getDate(){
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String dateString = format.format(currentDate);
        return dateString;
    }


    public static int getWindowWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getWindowHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


    public static Bitmap convertToBitmap(String path, Context mContext) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > CommonUtils.getWindowWidth(mContext) || height > CommonUtils.getWindowHeight(mContext)) {
            // 缩放
            scaleWidth = ((float) width) / CommonUtils.getWindowWidth(mContext);
            scaleHeight = ((float) height) / CommonUtils.getWindowHeight(mContext);
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));

        try {
            return Bitmap.createScaledBitmap(weak.get(), CommonUtils.getWindowWidth(mContext), CommonUtils.getWindowHeight(mContext), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveBitmap(Bitmap mBitmap,String bitNamePath)  {
        File f = new File(bitNamePath);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            if(getBitmapSize(mBitmap) / 1024 > 512){
                //图片压缩
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
            }else{
                //图片压缩
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
    }

    public static int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){     //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }
}

