package cn.com.codingtu.func4a.core.view.layer;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import cn.com.codingtu.func4a.core.R;
import cn.com.codingtu.func4a.core.activity.ActivityFunc;
import cn.com.codingtu.func4a.core.activity.WhenKeyDown;
import cn.com.codingtu.func4a.core.funcs.InflateFunc;
import cn.com.codingtu.func4a.core.funcs.ViewFunc;

public class Layer {

    protected View dialogView;
    private View shadowView;
    private AlphaAnimation showShadowAnim;
    private AlphaAnimation hiddenShadowAnim;
    private Layer.onHidden onHidden;
    int defaultDuration = 200;

    public Layer(Activity act, int layoutId) {

        ViewGroup rootView = ViewFunc.getRootViewGroup(act);

        dialogView = InflateFunc.inflate(layoutId, rootView);

        shadowView = dialogView.findViewById(R.id.shadowView);

        showShadowAnim = new AlphaAnimation(0f, 1f);
        showShadowAnim.setDuration(defaultDuration);

        hiddenShadowAnim = new AlphaAnimation(1f, 0f);
        hiddenShadowAnim.setDuration(defaultDuration);
        hiddenShadowAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (onHidden != null)
                    onHidden.onHiddenFinish();
                ViewFunc.gone(dialogView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (act instanceof ActivityFunc) {
            ((ActivityFunc) act).addToKeyDowns(new WhenKeyDown() {
                @Override
                public boolean onKeyDown(int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && isShow()) {
                        if (isHiddenWhenBackClick())
                            hidden();
                        return true;
                    }
                    return false;
                }
            });
        }

        if (isHiddenWhenShadowClick())
            shadowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hidden();
                }
            });

        rootView.addView(dialogView);

    }

    protected boolean isHiddenWhenBackClick() {
        return true;
    }

    protected boolean isHiddenWhenShadowClick() {
        return true;
    }

    public void show() {
        ViewFunc.visible(dialogView);
        shadowView.startAnimation(showShadowAnim);
    }

    public void hidden() {
        hidden(null);
    }

    public void hidden(Layer.onHidden onHidden) {
        this.onHidden = onHidden;
        shadowView.startAnimation(hiddenShadowAnim);
    }

    private boolean isShow() {
        return ViewFunc.isVisible(dialogView);
    }


    public void setOnClick(View.OnClickListener onClickListener, int... ids) {
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                dialogView.findViewById(ids[i]).setOnClickListener(onClickListener);
            }
        }
    }

    public static interface onHidden {
        public void onHiddenFinish();
    }
}
