package cn.com.codingtu.func4a.core.res;


import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;

import cn.com.codingtu.func4a.global.App;

public class Colors {

    public static int getColor(int colorId) {
        return App.APP.getResources().getColor(colorId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getColor(int colorId, Resources.Theme theme) {
        return App.APP.getResources().getColor(colorId, theme);
    }

}