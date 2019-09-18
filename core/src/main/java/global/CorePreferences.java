package global;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

import core.bean.CoreBean;
import core.json.JsonFunc;

public class CorePreferences {

    public static SharedPreferences sp() {
        return PreferenceManager.getDefaultSharedPreferences(App.APP);
    }

    /**************************************************
     *
     * put
     *
     **************************************************/

    public static void putString(String key, String value) {
        sp().edit().putString(key, value).commit();
    }

    public static void putInt(String key, int value) {
        sp().edit().putInt(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        sp().edit().putBoolean(key, value).commit();
    }

    public static void putFloat(String key, float value) {
        sp().edit().putFloat(key, value).commit();
    }

    public static void putLong(String key, long value) {
        sp().edit().putLong(key, value).commit();
    }

    public static void putStringSet(String key, Set<String> values) {
        sp().edit().putStringSet(key, values).commit();
    }

    public static void putBean(String key, CoreBean value) {
        sp().edit().putString(key, value.toString()).commit();
    }

    /**************************************************
     *
     * get
     *
     **************************************************/

    public static String getString(String key) {
        return sp().getString(key, null);
    }

    public static int getInt(String key) {
        return sp().getInt(key, 0);
    }

    public static float getFloat(String key) {
        return sp().getFloat(key, 0f);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sp().getBoolean(key, defValue);
    }

    public static long getLong(String key) {
        return sp().getLong(key, 0);
    }

    public static Set<String> getStringSet(String key) {
        return sp().getStringSet(key, null);
    }

    public static <T> T getT(Class<T> tClass, String key) {
        String json = getString(key);
        return JsonFunc.toBean(tClass, json);
    }

    /**************************************************
     *
     * 特别方法
     *
     **************************************************/

    public static void putPermissionChecker(String key) {

    }

    public static boolean getPermissionChecked(String key) {
        return getBoolean(key);
    }

    public static void putPermissionChecked(String key) {
        putBoolean(key, true);
    }
}
