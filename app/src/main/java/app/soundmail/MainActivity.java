package app.soundmail;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.ArrayList;

import app.maillibrary.NewMail;
import app.soundmail.MyUtil.CommonUtils;
import app.soundmail.MyUtil.PiceUtil;

/**
 * Created by sound on 2017/4/25.
 */
public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,     //写内存
            Manifest.permission.READ_EXTERNAL_STORAGE,      //读内存
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {  //如果是android6.0
            if(getPermissions()){           //申请权限
                submitEmail();
            }

        }else{
            submitEmail();
        }
    }


    private Boolean getPermissions(){
        //检查我们是否读内存权限
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED
                || permission2 != PackageManager.PERMISSION_GRANTED) {
            // 我们没有权限这样提示用户
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
            return false;
        }else{
            new PiceUtil(this);
            return true;
        }
    }


    /**
     * 发送带附件的邮件
     */
    public void submitEmail(){

        //发送普通邮件
        new Thread(new NewMail("sounddashi@163.com","普通邮件标题"+ CommonUtils.getDate(),
                "普通邮件内容",new ArrayList<String>(),new ArrayList<String>())).start();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
