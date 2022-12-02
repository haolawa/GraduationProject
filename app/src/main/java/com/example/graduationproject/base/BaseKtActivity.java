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
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.viewbinding.ViewBinding;

import com.example.graduationproject.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

//implements EasyPermissions.PermissionCallbacks
public abstract class BaseKtActivity<T extends ViewBinding> extends AppCompatActivity {

    protected abstract void initArgs(Intent intent);
    protected abstract void initView(Bundle bundle);
    protected abstract void initData();
    protected BaseApplication mApplication;
    protected T viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = getViewBinding();
        setContentView(viewBinding.getRoot());


        try {
            initArgs(getIntent());
            initView(savedInstanceState);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BaseActivity", "onCreate: " + e.toString());
        }
    }

    protected abstract T getViewBinding();

    @Override
    protected void onPause() {
        super.onPause();
        //   hideInputMethod();
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

}
