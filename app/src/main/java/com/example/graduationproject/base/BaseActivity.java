package com.example.graduationproject.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.example.graduationproject.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

//implements EasyPermissions.PermissionCallbacks
public abstract class BaseActivity extends AppCompatActivity {


    private Unbinder unbinder;

    protected abstract void initArgs(Intent intent);

    protected abstract void initView(Bundle bundle);

    protected abstract void initData();

    private InputMethodManager mInputMethodManager;
    protected BaseApplication mApplication;


    public static final int RC_CAMERA_PERM = 123;
    public static final int RC_ALBUM_PERM = 124;
    public String mCameraImagePath;//用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    public static final int CAMERA_REQUEST_CODE = 0x00000010;// 拍照的requestCode
    public static final int ALBUM_REQUEST_CODE = 0x00000020;//相册的requestCode


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //绑定ButterKnife
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);

        mInputMethodManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        mApplication = (BaseApplication) getApplication();
        mApplication.addActivity(this);

        try {
            initArgs(getIntent());
            initView(savedInstanceState);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BaseActivity", "onCreate: " + e.toString() );
        }
    }

    protected abstract int getLayoutId();

    @Override
    protected void onPause() {
        super.onPause();
     //   hideInputMethod();
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.activity_empty);
        mInputMethodManager = null;
        mApplication.removeActivity(this);
        unbinder.unbind();

        super.onDestroy();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }


    /**
     * 获取Application
     */
    protected BaseApplication getAppApplication() {
        if (mApplication == null) {
            mApplication = (BaseApplication) getApplication();
        }
        return mApplication;
    }

//    /**
//     * 隐藏键盘
//     */
//    public void hideInputMethod() {
//        try {
//            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
//                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        } catch (Exception ignored) {
//
//        }
//    }
//
    /**
     * 提示信息
     */
    public void ToastShow(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
//
//    /**
//     * 隐藏键盘
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
//    }
//
//    /**
//     * 获取edittext的位置
//     *
//     * @param v
//     * @param event
//     * @return
//     */
//    public boolean isShouldHideInput(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] leftTop = {0, 0};
//            //获取输入框当前的location位置
//            v.getLocationInWindow(leftTop);
//            int left = leftTop[0];
//            int top = leftTop[1];
//            int bottom = top + v.getHeight();
//            int right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击的是输入框区域，保留点击EditText的事件
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
////            new AppSettingsDialog.Builder(this).build().show();
//            new AppSettingsDialog.Builder(this).setTitle("权限").setRationale("缺少必要的权限，点击确定去设置").build().show();
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//
    @AfterPermissionGranted(RC_ALBUM_PERM)
    public void checkAlbum() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean hasPerm = EasyPermissions.hasPermissions(this, permissions);
        if (hasPerm) {
            openAlbum();
        } else {
            EasyPermissions.requestPermissions(this, "选择相册需要读取权限", RC_ALBUM_PERM, permissions);
        }
    }
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("return-data", false);
        startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void checkCamera() {
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        boolean hasCamera = EasyPermissions.hasPermissions(this, permissions);
        if (hasCamera) {
            openCamera();
        } else {
            EasyPermissions.requestPermissions(this, "拍照需要相机和存取权限", RC_CAMERA_PERM, permissions);
        }
    }

    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "createImageFile error : " + e.toString() );
            }

            if (photoFile != null) {
                mCameraImagePath = photoFile.getAbsolutePath();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                    photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
                } else {
                    photoUri = Uri.fromFile(photoFile);
                }
            }

            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    /**
     * 启动裁剪
     *
     * @param activity       上下文
     * @param sourceFilePath 需要裁剪图片的绝对路径
     * @param requestCode    比如：UCrop.REQUEST_CROP
     * @param aspectRatioX   裁剪图片宽高比
     * @param aspectRatioY   裁剪图片宽高比
     * @return
     */
    public static String startUCrop(Activity activity, String sourceFilePath,
                                    int requestCode, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
        //裁剪后图片的绝对路径
        String cameraScalePath = outFile.getAbsolutePath();
        Uri destinationUri = Uri.fromFile(outFile);
        //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);
        options.setMaxBitmapSize(20);
        //UCrop配置
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
        //uCrop.useSourceImageAspectRatio();
        //跳转裁剪页面
        uCrop.start(activity, requestCode);
        return cameraScalePath;
    }


    /**
     * 创建保存图片的文件
     *
     * @return
     * @throws IOException
     */
    public File createImageFile() {
        File tempFile = null;
        try {
            String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
            tempFile = new File(storageDir, imageName);
            if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "createImageFile error: " + e.toString() );
        }
        return tempFile;
    }
}
