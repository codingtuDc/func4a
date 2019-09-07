package cn.com.codingtu.func4a.core.bean;

import android.graphics.Rect;

public class LTRB extends CoreBean{
    public int l;
    public int t;
    public int r;
    public int b;

    public LTRB() {
    }

    public LTRB(int l, int t, int r, int b) {
        this.l = l;
        this.t = t;
        this.r = r;
        this.b = b;
    }

    public LTRB copyOne() {
        return new LTRB(l, t, r, b);
    }

    public Rect toRect() {
        return new Rect(l, t, r, b);
    }
}

