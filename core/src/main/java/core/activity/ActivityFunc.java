package core.activity;

import core.hero.OnActivityBack;
import core.permission.PermissionHelper;

public interface ActivityFunc {

    public void addOnActivityBack(OnActivityBack back);

    public void addPermissionHelper(PermissionHelper helper);

    public void addToKeyDowns(WhenKeyDown keyDown);

}
