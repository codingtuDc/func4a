package core.permission;

import android.app.Activity;

public interface PermissionHelper {

    public Activity getAct();

    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults);

}
