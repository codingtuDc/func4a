package cn.com.codingtu.func4a.core.activity;

import cn.com.codingtu.func4a.core.hero.OnActivityBack;
import cn.com.codingtu.func4a.core.permission.PermissionHelper;

public interface ActivityFunc {

    public void addOnActivityBack(OnActivityBack back);

    public void addPermissionHelper(PermissionHelper helper);

    public void addToKeyDowns(WhenKeyDown keyDown);

}
