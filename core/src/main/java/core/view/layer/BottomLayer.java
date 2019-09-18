package core.view.layer;

import android.app.Activity;
import android.view.View;
import android.view.animation.TranslateAnimation;

import core.R;
import core.funcs.ViewFunc;

public class BottomLayer extends Layer {

    private TranslateAnimation hiddenTranslateAnim;
    private TranslateAnimation showTranslateAnim;
    protected View bottomView;

    public BottomLayer(Activity act, int layoutId) {
        super(act, layoutId);
        bottomView = dialogView.findViewById(R.id.bottomView);
        ViewFunc.measureView(bottomView);

        showTranslateAnim = new TranslateAnimation(0f, 0f, bottomView.getMeasuredHeight(), 0f);
        showTranslateAnim.setDuration(defaultDuration);

        hiddenTranslateAnim = new TranslateAnimation(0f, 0f, 0f, bottomView.getMeasuredHeight());
        hiddenTranslateAnim.setDuration(defaultDuration);
    }

    @Override
    public void show() {
        super.show();
        bottomView.startAnimation(showTranslateAnim);
    }

    @Override
    public void hidden(CenterLayer.onHidden onHidden) {
        super.hidden(onHidden);
        bottomView.startAnimation(hiddenTranslateAnim);
    }
}