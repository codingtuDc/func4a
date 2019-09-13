package cn.com.codingtu.func4a.ui;

import android.os.Bundle;
import android.widget.TextView;

import cn.com.codingtu.func4a.R;
import cn.com.codingtu.func4a.User;
import cn.com.codingtu.func4a.core.activity.CoreActivity;
import cn.com.codingtu.func4a.core.processor.annotation.activity.Launcher;
import cn.com.codingtu.func4a.core.processor.annotation.view.FindView;

@Launcher(paramClasses = User.class,paramNames = "user")
public class OneActivity extends CoreActivity {

    @FindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
