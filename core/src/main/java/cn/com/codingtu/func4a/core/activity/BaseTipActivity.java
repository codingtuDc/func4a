package cn.com.codingtu.func4a.core.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.com.codingtu.func4a.core.R;
import cn.com.codingtu.func4a.core.bean.WH;
import cn.com.codingtu.func4a.core.funcs.AdjustFunc;
import cn.com.codingtu.func4a.core.funcs.InflateFunc;
import cn.com.codingtu.func4a.core.funcs.Margins;
import cn.com.codingtu.func4a.core.funcs.StatusBarFunc;
import cn.com.codingtu.func4a.core.funcs.ViewFunc;
import cn.com.codingtu.func4a.core.image.ImageFunc;
import cn.com.codingtu.func4j.MathFunc;

public abstract class BaseTipActivity extends CoreActivity {

    private ViewPager tipVp;
    private LinearLayout dotsLl;
    private String[] pics;
    private WH designPicWH;
    private WH picWh;
    private ImageView[] dotIvs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base_tip);
        StatusBarFunc.fullScreen(getThis());
        tipVp = findViewById(R.id.tipVp);
        dotsLl = findViewById(R.id.dotsLl);
    }

    @Override
    protected void onViewInitComplete() {
        super.onViewInitComplete();
        pics = getPics();

        int sW = tipVp.getMeasuredWidth();
        int sH = tipVp.getMeasuredHeight();

        designPicWH = getDesignPicWH();
        picWh = AdjustFunc.inBox(sW, sH, designPicWH.w, designPicWH.h);

        Margins.b(dotsLl, getSize(getDesignDotMarginBottom()));

        int dotW = getSize(getDesignDotWidth());
        int dotL = getSize(getDesignDotMarginLeft());
        dotIvs = new ImageView[pics.length];
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(getThis());
            ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(dotW, dotW);
            if (i != 0) {
                mlp.leftMargin = dotL;
                iv.setImageResource(getDotUncheckImage());
            } else {
                iv.setImageResource(getDotCheckedImage());
            }
            dotIvs[i] = iv;
            dotsLl.addView(iv, mlp);
        }

        tipVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pics.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                RelativeLayout inflate = (RelativeLayout) InflateFunc.inflate(R.layout.item_tip, container);

                ImageView tipIv = inflate.findViewById(R.id.tipIv);
                ViewFunc.setWH(tipIv, picWh.w, picWh.h);
                ImageFunc.setImage(tipIv, "file:///android_asset/" + pics[position]);

                if (position == getCount() - 1) {
                    ImageView startIv = new ImageView(getThis());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeResource(getResources(), getStartBtImage(), options);
                    startIv.setImageResource(getStartBtImage());
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(getSize(options.outWidth), getSize(options.outHeight));
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    ((RelativeLayout.MarginLayoutParams) rlp).bottomMargin = getSize(getDesignStartBtMarginBottom());
                    inflate.addView(startIv, rlp);
                    startIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onStartBtClick(v);
                        }
                    });
                }

                container.addView(inflate);
                return inflate;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        tipVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                changeDotBg(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    protected void changeDotBg(int index) {
        for (int i = 0; i < dotIvs.length; i++) {
            ImageView dotIv = dotIvs[i];
            if (i == index) {
                dotIv.setImageResource(getDotCheckedImage());
            } else {
                dotIv.setImageResource(getDotUncheckImage());
            }
        }
    }

    protected int getSize(int designSize) {
        return MathFunc.adjust(designPicWH.h, picWh.h, designSize);
    }

    protected abstract void onStartBtClick(View v);

    protected abstract int getStartBtImage();

    protected abstract int getDotUncheckImage();

    protected abstract int getDotCheckedImage();

    protected abstract int getDesignStartBtMarginBottom();

    protected abstract int getDesignDotMarginBottom();

    protected abstract int getDesignDotMarginLeft();

    protected abstract WH getDesignPicWH();

    protected abstract int getDesignDotWidth();

    protected abstract String[] getPics();
}