package cn.com.codingtu.func4a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.com.codingtu.func4a.core.activity.CoreActivity;
import cn.com.codingtu.func4a.core.funcs.ViewFunc;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickTag;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickView;
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

    @ClickView({R.id.tv, R.id.tv1})
    public void clickCCC(View view, @ClickTag(R.id.tag_position) int position) {

    }

    @ClickView(R.id.tv2)
    public void clickTv2() {

    }
}
