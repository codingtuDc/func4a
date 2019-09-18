package core.view.layer;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import core.R;


public class CenterLayer extends Layer {

    private ScaleAnimation hiddenScaleAnim;
    private ScaleAnimation showScaleAnim;
    protected View centerView;

    public CenterLayer(Activity act, int layoutId) {
        super(act, layoutId);
        centerView = dialogView.findViewById(R.id.centerView);

        showScaleAnim = new ScaleAnimation(0.0f, 1f, 0.0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        showScaleAnim.setDuration(defaultDuration);

        hiddenScaleAnim = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        hiddenScaleAnim.setDuration(defaultDuration);

    }

    @Override
    public void show() {
        super.show();
        centerView.startAnimation(showScaleAnim);
    }

    @Override
    public void hidden(CenterLayer.onHidden onHidden) {
        super.hidden(onHidden);
        centerView.startAnimation(hiddenScaleAnim);
    }
}