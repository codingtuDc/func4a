package cn.com.codingtu.func4a;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.com.codingtu.func4a.core.activity.CoreActivity;
import cn.com.codingtu.func4a.core.funcs.ViewFunc;
import cn.com.codingtu.func4a.core.net.Net;
import cn.com.codingtu.func4a.core.net.NetBackI;
import cn.com.codingtu.func4a.core.net.NetUtils;
import cn.com.codingtu.func4a.core.permission.Permissions;
import cn.com.codingtu.func4a.core.processor.annotation.net.BackType;
import cn.com.codingtu.func4a.core.processor.annotation.net.NetBack;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickTag;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickView;
import cn.com.codingtu.func4a.core.processor.annotation.permission.PermissionCheck;
import cn.com.codingtu.func4a.core.processor.annotation.view.FindView;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public class MainActivity extends CoreActivity {
    @FindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewFunc.setText(tv, "xfsesdf");

        Permissions.checkWriteExternalStorage(hero);

        Net.getAlbumDetail("").main(hero);


    }

    @NetBack
    public void getAlbumDetailBack(Throwable error, Response<ResponseBody> response) {

    }

    @NetBack(BeanBack.class)
    public void getAlbumDetailBack1(String message, User user) {

    }

    @PermissionCheck(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE}, isForce = false)
    public void checkWriteExternalStorage(String[] permissions, int[] grantResults) {

    }

    @PermissionCheck(value = {Manifest.permission.CAMERA}, isForce = false)
    public void chekCamera(boolean isAllow) {

    }

    @PermissionCheck(value = {Manifest.permission.CAMERA}, isForce = false)
    public void chekCamera1() {

    }

    @ClickView({R.id.tv, R.id.tv1})
    public void clickCCC(View view, @ClickTag(R.id.tag_position) int position) {

    }

    @ClickView(R.id.tv2)
    public void clickTv2() {

    }

}
