package cn.com.codingtu.func4a;

import android.os.Bundle;
import android.support.annotation.Nullable;

import core.activity.CoreActivity;
import core.processor.annotation.activity.Launcher;

@Launcher(paramClasses = User.class,paramNames = "user")
public class OneActivity extends CoreActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
