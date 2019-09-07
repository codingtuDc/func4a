package cn.com.codingtu.func4a.core.hero;

import android.view.View;

import cn.com.codingtu.func4a.core.net.NetBackI;
import cn.com.codingtu.func4a.core.permission.PermissionHelper;

public interface Hero
        extends NetBackI, View.OnClickListener, PermissionHelper, OnActivityBack {
}