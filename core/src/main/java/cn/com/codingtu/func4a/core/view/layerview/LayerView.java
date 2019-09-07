package cn.com.codingtu.func4a.core.view.layerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import cn.com.codingtu.func4a.core.R;
import cn.com.codingtu.func4a.core.activity.ActivityFunc;
import cn.com.codingtu.func4a.core.activity.WhenKeyDown;
import cn.com.codingtu.func4a.core.funcs.ViewFunc;

public abstract class LayerView extends RelativeLayout {

    private AlphaAnimation showShadowAnim;
    private AlphaAnimation hiddenShadowAnim;

    protected int defaultDuration = 300;

    private View shadowView;

    private OnHidden onHidden;


    public LayerView(Context context) {
        this(context, null);
    }

    public LayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    void init(Context context, AttributeSet attrs, int defStyleAttr) {

        shadowView = new View(context);
        shadowView.setBackgroundColor(context.getResources().getColor(R.color.colorShadow));
        addView(shadowView, ViewFunc.MATCH_PARENT, ViewFunc.MATCH_PARENT);
        if (isHiddenWhenShadowClick())
            shadowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hidden();
                }
            });

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
                ViewFunc.gone(LayerView.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (context instanceof ActivityFunc) {
            ((ActivityFunc) context).addToKeyDowns(new WhenKeyDown() {
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


    }

    public void show() {
        ViewFunc.visible(this);
        shadowView.startAnimation(showShadowAnim);
    }

    private boolean isHiddenWhenShadowClick() {
        return true;
    }

    protected boolean isHiddenWhenBackClick() {
        return true;
    }

    private boolean isShow() {
        return ViewFunc.isVisible(this);
    }

    public void setOnHidden(OnHidden onHidden) {
        this.onHidden = onHidden;
    }

    public void hidden() {
        hidden(null);
    }

    public void hidden(OnHidden onHidden) {
        this.onHidden = onHidden;
        shadowView.startAnimation(hiddenShadowAnim);
    }

    public static interface OnHidden {
        public void onHiddenFinish();
    }

}