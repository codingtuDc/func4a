package cn.com.codingtu.func4a.core.funcs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.codingtu.func4a.global.App;

/**
 * 布局文件转换成view
 */

public class InflateFunc {
    public static View inflate(int layoutId) {
        return inflate(layoutId, null, false);
    }

    public static View inflate(int layoutId, ViewGroup root) {
        return inflate(layoutId, root, false);
    }

    public static View inflate(int layoutId, ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(App.APP).inflate(layoutId, root, attachToRoot);
    }


    public static View inflate(LayoutInflater inflater, int layoutId, ViewGroup root) {
        return inflate(inflater, layoutId, root, false);
    }

    public static View inflate(LayoutInflater inflater, int layoutId, ViewGroup root,
                               boolean attachToRoot) {
        return inflater.inflate(layoutId, root, attachToRoot);
    }

}