package cn.com.codingtu.func4a.core.funcs;

import android.app.Activity;
import android.content.Intent;

import cn.com.codingtu.func4a.core.R;

/********************************
 *
 * 跟activity有关的工具类
 *
 ********************************/

public class ActFunc {

    /********************************
     *
     * activity跳转动画
     *
     ********************************/
    private static void overridePendingTransition(Activity act, int enterAnim, int exitAnim) {
        act.overridePendingTransition(enterAnim, exitAnim);
    }

    /********************************
     *
     * 从右进入的动画
     *
     ********************************/
    public static void actRightIn(Activity act) {
        overridePendingTransition(act, R.anim.translatex100to0, R.anim.translatex0tof100);
    }

    /********************************
     *
     * 从右出去的动画
     *
     ********************************/
    public static void actRightOut(Activity act) {
        overridePendingTransition(act, R.anim.translatexf100to0, R.anim.translatex0to100);
    }

    /**************************************************
     *
     * 启动activity
     *
     **************************************************/

    public static final void startActivity(Activity act, Class<? extends Activity> aClass) {
        startActivity(act, new Intent(act, aClass));
    }

    public static final void startActivityForResult(Activity act, Class<? extends Activity> aClass,
                                                    int requestCode) {
        startActivityForResult(act, new Intent(act, aClass), requestCode);
    }

    public static final void startActivity(Activity act, Intent intent) {
        act.startActivity(intent);
        ActFunc.actRightIn(act);
    }

    public static final void startActivityForResult(Activity act, Intent intent, int requestCode) {
        act.startActivityForResult(intent, requestCode);
        ActFunc.actRightIn(act);
    }

}