# Permission使用

权限检测的回调方法，方法的参数只能为以下三种形式。

isForce如果为true，则每次都会调用系统的检测方法。如果为false，则检测后会缓存检测结果，之后不再调用系统的检测方法。

```
    @PermissionCheck(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE}, isForce = false)
    public void checkWriteExternalStorage(String[] permissions, int[] grantResults) {

    }
    @PermissionCheck(value = {Manifest.permission.CAMERA}, isForce = false)
    public void chekCamera(boolean isAllow) {

    }
    @PermissionCheck(value = {Manifest.permission.CAMERA}, isForce = false)
    public void chekCamera1() {

    }
```

通过上面的方法。会生成检测的方法

```
Permissions.checkWriteExternalStorage(hero);
```
