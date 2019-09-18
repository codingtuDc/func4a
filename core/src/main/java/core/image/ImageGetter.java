package core.image;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import java.io.File;

import core.activity.ActivityFunc;
import core.funcs.ToastFunc;
import core.hero.OnActivityBack;
import core.log.Logs;
import core.permission.PermissionFunc;
import core.permission.PermissionHelper;
import global.CoreConfigs;
import global.CoreRequestCode;

public final class ImageGetter implements PermissionHelper, OnActivityBack {

    private File outputImage;
    private Activity act;
    private ImageGetterBack back;

    private ImageGetter() {
    }

    public static void fromCamera(Activity act, ImageGetterBack back) {
        new ImageGetter().getPicByCamera(act, back);
    }

    public static void fromGallery(Activity act, ImageGetterBack back) {
        new ImageGetter().getPicByGallery(act, back);
    }


    private void getPicByCamera(Activity act, ImageGetterBack back) {
        from(act, back, CoreRequestCode.GET_PIC_BY_CAMERA);
    }

    private void getPicByGallery(Activity act, ImageGetterBack back) {
        from(act, back, CoreRequestCode.GET_PIC_BY_GALLERY);
    }

    private void from(Activity act, ImageGetterBack back, int code) {
        this.act = act;
        this.back = back;
        if (act instanceof ActivityFunc) {
            ((ActivityFunc) act).addPermissionHelper(this);
            ((ActivityFunc) act).addOnActivityBack(this);
        }
        PermissionFunc.check(this, code, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    @Override
    public Activity getAct() {
        return this.act;
    }

    @Override
    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults) {
        if (PermissionFunc.allow(grantResults)) {
            if (requestCode == CoreRequestCode.GET_PIC_BY_CAMERA) {
                getPicByCamera();
            } else if (requestCode == CoreRequestCode.GET_PIC_BY_GALLERY) {
                getPicByGallery();
            } else {
                clear();
            }
        } else {
            if (requestCode == CoreRequestCode.GET_PIC_BY_CAMERA) {
                ToastFunc.toast("您禁用了拍照的权限，请恢复权限后再尝试");
            } else if (requestCode == CoreRequestCode.GET_PIC_BY_GALLERY) {
                ToastFunc.toast("您禁用了读取照片权限，请恢复权限后再尝试");
            }
            clear();
        }
    }

    private void getPicByGallery() {

    }

    private void getPicByCamera() {
        try {
            File dir = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CoreConfigs.configs()
                            .getPicDirName());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            outputImage = new File(dir, System.currentTimeMillis() + ".jpg");
            //            outputImage = new File(App.APP.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            if (!outputImage.exists()) {
                outputImage.createNewFile();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider
                        .getUriForFile(act, "cn.bookgood.reader.fileprovider", outputImage));
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
            }
            act.startActivityForResult(intent, CoreRequestCode.GET_PIC_BY_CAMERA);
        } catch (Exception e) {
            act = null;
            Logs.w(e);
        }
    }


    private void clear() {
        this.act = act;
        this.back = null;
    }

    public static interface ImageGetterBack {
        public void imageBack(String image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CoreRequestCode.GET_PIC_BY_CAMERA) {
                cameraBack();
            } else if (requestCode == CoreRequestCode.GET_PIC_BY_GALLERY) {
                galleryBack(data);
            }
        }
    }

    private void cameraBack() {
        if (outputImage != null) {
            ImageFunc.updateAlbum(outputImage);
            String absolutePath = outputImage.getAbsolutePath();
            back.imageBack(absolutePath);
        }
        clear();
    }

    private void galleryBack(Intent data) {

        String pic = null;
        if (Build.VERSION.SDK_INT >= 19) {
            pic = handleImageOnKitKat(data);
        } else {
            pic = handleImageBeforeKitKat(data);
        }

        back.imageBack(pic);
        clear();
    }

    /********************************
     *
     * 获取相册选择后的照片（4.4以上版本)
     *
     ********************************/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(act, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris
                        .withAppendedId(Uri.parse("content://downloads/public_downloads"),
                                Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }


    /********************************
     *
     * 获取相册选择后的照片（4.4之前版本)
     *
     ********************************/
    private String handleImageBeforeKitKat(Intent data) {
        return getImagePath(data.getData(), null);
    }

    /********************************
     *
     * 从uri中获得图片地址
     *
     ********************************/
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = act.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}