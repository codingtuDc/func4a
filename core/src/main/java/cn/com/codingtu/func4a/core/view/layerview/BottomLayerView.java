package cn.com.codingtu.func4a.core.view.layerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

public class BottomLayerView extends LayerView {
    private View bottomView;
    private TranslateAnimation showTranslateAnim;
    private TranslateAnimation hiddenTranslateAnim;
    private boolean isShowOnLayoutComplete;

    public BottomLayerView(Context context) {
        super(context);
    }

    public BottomLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomLayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 1) {
            throw new IllegalStateException("BottomLayerView can host only one direct child");
        }
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 1) {
            throw new IllegalStateException("BottomLayerView can host only one direct child");
        }
        super.addView(child, index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 1) {
            throw new IllegalStateException("BottomLayerView can host only one direct child");
        }
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 1) {
            throw new IllegalStateException("BottomLayerView can host only one direct child");
        }
        super.addView(child, index, params);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (bottomView == null)
            bottomView = getChildAt(1);
        int measuredHeight = getMeasuredHeight();
        int top = measuredHeight - bottomView.getMeasuredHeight();
        bottomView.layout(0, top, bottomView.getMeasuredWidth(), measuredHeight);

        if (showTranslateAnim == null) {
            showTranslateAnim = new TranslateAnimation(0f, 0f, bottomView.getMeasuredHeight(), 0f);
            showTranslateAnim.setDuration(defaultDuration);
        }
        if (hiddenTranslateAnim == null) {
            hiddenTranslateAnim = new TranslateAnimation(0f, 0f, 0f,
                    bottomView.getMeasuredHeight());
            hiddenTranslateAnim.setDuration(defaultDuration);
        }

        if (isShowOnLayoutComplete) {
            isShowOnLayoutComplete = false;
            bottomView.startAnimation(showTranslateAnim);
        }


    }

    @Override
    public void show() {
        super.show();
        if (bottomView != null) {
            bottomView.startAnimation(showTranslateAnim);
        } else {
            isShowOnLayoutComplete = true;
        }

    }

    @Override
    public void hidden(OnHidden onHidden) {
        super.hidden(onHidden);
        getChildAt(1).startAnimation(hiddenTranslateAnim);
    }
}