package cn.com.codingtu.func4a.core.funcs;

import android.content.pm.PackageInfo;

import cn.com.codingtu.func4a.core.log.Logs;
import cn.com.codingtu.func4a.global.App;
import cn.com.codingtu.func4j.StringFunc;

public class VersionFunc {

    private static String VERSION_NAME;
    private static int VERSION_CODE = -1;

    public static synchronized String getVersionName() {
        if (StringFunc.isBlank(VERSION_NAME)) {
            try {
                PackageInfo packageInfo = SystemFunc.getPackageManager().getPackageInfo(
                        App.APP.getPackageName(), 0);
                VERSION_NAME = packageInfo.versionName;
            } catch (Exception e) {
                Logs.w(e);
            }
        }
        return VERSION_NAME;
    }


    public static synchronized int getVersionCode() {

        if (VERSION_CODE < 0) {
            try {
                PackageInfo packageInfo = SystemFunc.getPackageManager().getPackageInfo(
                        App.APP.getPackageName(), 0);
                VERSION_CODE = packageInfo.versionCode;
            } catch (Exception e) {
                Logs.w(e);
            }
        }
        return VERSION_CODE;
    }

}
