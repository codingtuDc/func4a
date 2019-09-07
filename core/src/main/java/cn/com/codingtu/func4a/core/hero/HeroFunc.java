package cn.com.codingtu.func4a.core.hero;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;

import cn.com.codingtu.func4a.core.funcs.ViewFunc;
import cn.com.codingtu.func4a.core.log.Logs;

public class HeroFunc {

    public static Hero bind(Activity binder) {
        try {
            String helperName = binder.getClass().getName() + "_Hero";
            Constructor<Hero> constructor = (Constructor<Hero>) Class.forName(helperName)
                    .getConstructor(
                            binder.getClass(),
                            View.class);
            return constructor.newInstance(binder, ViewFunc.getRootView(binder));
        } catch (Exception e) {
            Logs.w(e);
        }
        return null;
    }

    public static Hero bind(Object binder, View view) {
        try {
            String helperName = binder.getClass().getName() + "_Hero";
            Constructor<Hero> constructor = (Constructor<Hero>) Class.forName(helperName)
                    .getConstructor(
                            binder.getClass(),
                            View.class);
            return constructor.newInstance(binder, view);
        } catch (Exception e) {
            Logs.w(e);
        }
        return null;
    }

}