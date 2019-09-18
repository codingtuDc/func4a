package cn.com.codingtu.func4a;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;

import core.activity.CoreActivity;
import core.net.Net;
import core.processor.annotation.net.NetBack;
import core.processor.annotation.onactivityresult.OnResult4Activity;
import core.processor.annotation.onclick.ClickView;
import core.processor.annotation.permission.PermissionCheck;
import core.processor.annotation.view.FindView;

public class MainActivity extends CoreActivity {

    @FindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Net.getAlbumDetail("").main(hero);
    }

    @ClickView(R.id.tv)
    public void clickTv() {

    }

    @OnResult4Activity(OneActivity.class)
    public void sss(User user) {

    }

    @PermissionCheck(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void sss(boolean isAllow) {

    }
}
