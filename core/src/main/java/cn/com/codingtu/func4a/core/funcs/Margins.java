package cn.com.codingtu.func4a.core.funcs;

import android.view.View;
import android.view.ViewGroup;

import cn.com.codingtu.func4a.core.bean.LTRB;

/**************************************************
 *
 * view的margin的操作
 *
 **************************************************/
public class Margins {

    //获取LayoutParam
    private static ViewGroup.MarginLayoutParams lp(View v) {
        return (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    }

    //获取margin
    public static LTRB ltrb(View v) {
        LTRB ltrb = new LTRB();
        if (v != null) {
            ViewGroup.MarginLayoutParams lp = lp(v);
            ltrb.l = lp.leftMargin;
            ltrb.t = lp.topMargin;
            ltrb.r = lp.rightMargin;
            ltrb.b = lp.bottomMargin;
        }
        return ltrb;
    }

    public static int l(View v) {
        if (v == null)
            return -1;
        return lp(v).leftMargin;
    }

    public static int t(View v) {
        if (v == null)
            return -1;
        return lp(v).topMargin;
    }

    public static int r(View v) {
        if (v == null)
            return -1;
        return lp(v).rightMargin;
    }

    public static int b(View v) {
        if (v == null)
            return -1;
        return lp(v).bottomMargin;
    }

    //设置margin,单位px
    public static void l(View v, int l) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        v.setLayoutParams(lp);
    }

    public static void lt(View v, int l, int t) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        v.setLayoutParams(lp);
    }

    public static void ltr(View v, int l, int t, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void ltrb(View v, int l, int t, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void ltb(View v, int l, int t, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void lr(View v, int l, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void lrb(View v, int l, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void lb(View v, int l, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void t(View v, int t) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        v.setLayoutParams(lp);
    }

    public static void tr(View v, int t, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void trb(View v, int t, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void tb(View v, int t, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void r(View v, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void rb(View v, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void b(View v, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    //设置margin，单位dp
    public static void l_dp(View v, int l) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        v.setLayoutParams(lp);
    }

    public static void lt_dp(View v, int l, int t) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        lp.topMargin = MobileFunc.dpToPx(t);
        v.setLayoutParams(lp);
    }

    public static void ltr_dp(View v, int l, int t, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        lp.topMargin = MobileFunc.dpToPx(t);
        lp.rightMargin = MobileFunc.dpToPx(r);
        v.setLayoutParams(lp);
    }

    public static void ltrb_dp(View v, int l, int t, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        lp.topMargin = MobileFunc.dpToPx(t);
        lp.rightMargin = MobileFunc.dpToPx(r);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }

    public static void ltb_dp(View v, int l, int t, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        lp.topMargin = MobileFunc.dpToPx(t);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }

    public static void lr_dp(View v, int l, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        lp.rightMargin = MobileFunc.dpToPx(r);
        v.setLayoutParams(lp);
    }

    public static void lrb_dp(View v, int l, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        lp.rightMargin = MobileFunc.dpToPx(r);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }

    public static void lb_dp(View v, int l, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = MobileFunc.dpToPx(l);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }

    public static void t_dp(View v, int t) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = MobileFunc.dpToPx(t);
        v.setLayoutParams(lp);
    }

    public static void tr_dp(View v, int t, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = MobileFunc.dpToPx(t);
        lp.rightMargin = MobileFunc.dpToPx(r);
        v.setLayoutParams(lp);
    }

    public static void trb_dp(View v, int t, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = MobileFunc.dpToPx(t);
        lp.rightMargin = MobileFunc.dpToPx(r);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }

    public static void tb_dp(View v, int t, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = MobileFunc.dpToPx(t);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }

    public static void r_dp(View v, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.rightMargin = MobileFunc.dpToPx(r);
        v.setLayoutParams(lp);
    }

    public static void rb_dp(View v, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.rightMargin = MobileFunc.dpToPx(r);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }

    public static void b_dp(View v, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.bottomMargin = MobileFunc.dpToPx(b);
        v.setLayoutParams(lp);
    }


}
