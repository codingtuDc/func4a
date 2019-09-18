package core.hero;

import android.view.View;

import core.net.NetBackI;
import core.permission.PermissionHelper;

public interface Hero
        extends NetBackI, View.OnClickListener, PermissionHelper, OnActivityBack {
}