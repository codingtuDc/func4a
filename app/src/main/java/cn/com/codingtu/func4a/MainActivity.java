package cn.com.codingtu.func4a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.com.codingtu.func4a.core.activity.CoreActivity;
import cn.com.codingtu.func4a.core.funcs.ViewFunc;
import cn.com.codingtu.func4a.core.processor.annotation.view.FindView;

public class MainActivity extends CoreActivity {
    @FindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewFunc.setText(tv, "xfsesdf");
    }
}
