package cn.com.codingtu.func4a;

import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.codingtu.func4a.core.activity.viewholder.CoreViewHolder;
import cn.com.codingtu.func4a.core.hero.HeroFunc;
import cn.com.codingtu.func4a.core.processor.annotation.view.FindView;

public class HomeVh extends CoreViewHolder {

    @FindView(R.id.tv)
    TextView tv;

    public HomeVh(int layoutId, ViewGroup viewGroup) {
        super(layoutId, viewGroup);
    }
}
