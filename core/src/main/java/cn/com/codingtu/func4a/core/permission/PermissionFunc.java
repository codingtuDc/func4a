package cn.com.codingtu.func4a.core.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import cn.com.codingtu.func4a.global.App;
import cn.com.codingtu.func4a.global.CorePreferences;

public class PermissionFunc {

    public static void check(PermissionHelper helper, String name, int code, String... ps) {
        if (!CorePreferences.getPermissionChecked(name)) {
            CorePreferences.putPermissionChecked(name);
            check(helper, code, ps);
        } else {
            helper.onPermissionsBack(code, null, null);
        }
    }

    public static void check(PermissionHelper helper, int code, String... ps) {
        if (check(helper.getAct(), code, ps)) {
            helper.onPermissionsBack(code, ps, new int[ps.length]);
        }
    }


    private static boolean check(Activity act, int code, String... permissioins) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!allow(permissioins)) {
                act.requestPermissions(permissioins, code);
                return false;
            }
        }
        return true;
    }

    public static boolean allow(String[] permissions) {
        for (String permission : permissions) {
            if (!allow(permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean allow(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return App.APP.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static boolean allow(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
