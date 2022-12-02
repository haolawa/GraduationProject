package com.example.graduationproject.view.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.graduationproject.R;
import com.example.graduationproject.base.BaseActivity;
import com.example.graduationproject.controller.AppDatabase;
import com.example.graduationproject.controller.FilmDao;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.utils.DateUtil;
import com.example.graduationproject.utils.PopupWindowThree;
import com.example.graduationproject.utils.ThreadUtils;
import com.example.graduationproject.utils.TitleBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yalantis.ucrop.UCrop;

import java.util.Date;

import butterknife.BindView;

public class FilmDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_film_name)
    EditText etFilmName;
    @BindView(R.id.img_film)
    SimpleDraweeView imgFilm;
    @BindView(R.id.img_love)
    ImageView imgLove;
    @BindView(R.id.img_watch)
    ImageView imgWatch;
    @BindView(R.id.et_film_style)
    EditText etFilmStyle;
    @BindView(R.id.tv_film_time)
    TextView tvFilmTime;

    @BindView(R.id.et_director)
    EditText etDirector;
    @BindView(R.id.et_performer)
    EditText etPerformer;
    @BindView(R.id.et_film_detail)
    EditText etFilmDetail;
    @BindView(R.id.et_review)
    EditText etReview;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.ll_device)
    LinearLayout llDevice;

    private boolean isImgWatch = false;
    private boolean isImgLove = false;
    private static final int REQUEST_CODE = 101;
    private static final int RESULT_CODE = 102;
    private int uid;
    private float ratioX = 1.1f;
    private float ratioY = 1.4f;
    private FilmSaveBean filmBean = new FilmSaveBean();
    private boolean boo = false;
    private String stampDate;
    private String path;


    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initData() {
        uid = getIntent().getIntExtra("uid", 0);
        new Thread(() -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "myfilm.db").build();
            FilmDao filmDao = db.filmDao();
            filmBean = filmDao.getIdDetail(uid);

            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(() -> {
                showView();
            });

        }).start();
    }

    @Override
    protected void initView(Bundle bundle) {


        titleBar.setTitle("电影详情");
        titleBar.setBackOnclickListener(this);

        imgWatch.setOnClickListener(this);

        imgLove.setOnClickListener(this);

        imgFilm.setOnClickListener(this);

        tvFilmTime.setOnClickListener(this);

        btnSave.setOnClickListener(this);

        btnDelete.setOnClickListener(this);


        etFilmName.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_film:
                showPopupWindow(view);
                break;
            case R.id.img_watch:
                isImgWatch = !isImgWatch;
                if (isImgWatch) {
                    imgWatch.setImageResource(R.mipmap.watch);
                } else {
                    imgWatch.setImageResource(R.mipmap.unwatch);
                }
                break;
            case R.id.img_love:
                isImgLove = !isImgLove;
                if (isImgLove) {
                    imgLove.setImageResource(R.mipmap.love);
                } else {
                    imgLove.setImageResource(R.mipmap.unlove);
                }
                break;
            case R.id.tv_film_time:
                initPickTimer(tvFilmTime);
                break;
            case R.id.btn_save:
                if (haveData()) {
                    upDataFilm();
                    ToastShow("保存成功！");
                }
                break;
            case R.id.btn_delete:
                deleteFilm();
                ToastShow("删除成功");
                break;
            default:
                break;

        }
    }

    private boolean haveData() {
        if (etFilmName.getText().equals("")) {
            ToastShow("请输入电影名称");
            return false;
        }
        if (etFilmDetail.getText().equals("")) {
            ToastShow("请输入电影详情");
            return false;
        }
        if (etPerformer.getText().equals("")) {
            ToastShow("请输入电影演员");
            return false;
        }
        if (etDirector.getText().equals("")) {
            ToastShow("请输入电影导演");
            return false;
        }
        if (etFilmStyle.getText().equals("")) {
            ToastShow("请输入电影类型");
            return false;
        }
        if (etReview.getText().equals("")) {
            ToastShow("请输入电影感想");
            return false;
        }
        if (tvFilmTime.getText().equals("")) {
            ToastShow("请输入电影时间");
            return false;
        }

        return true;
    }

    private void upDataFilm() {

        FilmSaveBean filmSaveBean = new FilmSaveBean();
        filmSaveBean.setUid(filmBean.getUid());
        filmSaveBean.setFilmName(etFilmName.getText().toString());
        if (!StringUtils.isEmpty(path)) {
            filmSaveBean.setFilmImg(path);
        }
        filmSaveBean.setFilmDetail(etFilmDetail.getText().toString());
        filmSaveBean.setFilmNote(etReview.getText().toString());
        filmSaveBean.setFilmDirectors(etDirector.getText().toString());
        filmSaveBean.setFilmTime(tvFilmTime.getText().toString());
        filmSaveBean.setFilmPerformer(etPerformer.getText().toString());
        filmSaveBean.setFilmType(etFilmStyle.getText().toString());
        filmSaveBean.setLove(isImgLove);
        filmSaveBean.setWatch(isImgWatch);

        ThreadUtils.filmSave(this, filmSaveBean);

    }

    //改变按钮背景
    private boolean changeImageWatch(ImageView img, boolean is) {
        if (is) {
            img.setBackgroundColor(getResources().getColor(R.color.secondary_text));
            is = false;
        } else {
            img.setBackgroundColor(getResources().getColor(R.color.teal_200));
            is = true;
        }
        return is;
    }

    private void showView() {

        if (!StringUtils.isEmpty(filmBean.getFilmName())) {
            etFilmName.setText(filmBean.getFilmName());
        }
        if (!StringUtils.isEmpty(filmBean.getFilmImg())) {
            imgFilm.setImageURI(filmBean.getFilmImg());
            path = filmBean.getFilmImg();
        }
        if (!StringUtils.isEmpty(filmBean.getFilmType())) {
            etFilmStyle.setText(filmBean.getFilmType());
        }
        if (!StringUtils.isEmpty(filmBean.getFilmTime())) {
            tvFilmTime.setText(filmBean.getFilmTime());
        }
        if (!StringUtils.isEmpty(filmBean.getFilmDirectors())) {
            etDirector.setText(filmBean.getFilmDirectors());
        }
        if (!StringUtils.isEmpty(filmBean.getFilmPerformer())) {
            etPerformer.setText(filmBean.getFilmPerformer());
        }
        if (!StringUtils.isEmpty(filmBean.getFilmDetail())) {
            etFilmDetail.setText(filmBean.getFilmDetail());
        }
        if (!StringUtils.isEmpty(filmBean.getFilmNote())) {
            etReview.setText(filmBean.getFilmNote());
        }

        if (filmBean.isLove) {
            imgLove.setImageResource(R.mipmap.love);
            isImgLove = true;
        } else {
            imgLove.setImageResource(R.mipmap.unlove);
            isImgLove = false;
        }

        if (filmBean.isWatch) {
            imgWatch.setImageResource(R.mipmap.watch);
            isImgWatch = true;
        } else {
            imgWatch.setImageResource(R.mipmap.unwatch);
            isImgWatch = false;
        }

    }

    private void showPopupWindow(View view) {
        PopupWindowThree popupWindow = new PopupWindowThree(FilmDetailActivity.this);
        String[] strings = new String[]{"", "拍照", "从相册选择", "取消"};
        popupWindow.setTexts(strings);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnItemClickListener(new PopupWindowThree.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_item_1:
                        popupWindow.dismiss();
                        //调相机
                        checkCamera();
                        Log.e("haolawa1", "1");
                        break;
                    case R.id.tv_item_2:
                        Log.e("haolawa1", "2");
                        popupWindow.dismiss();
                        //调相册
                        checkAlbum();
                        break;
                    case R.id.tv_item_3:
                        Log.e("haolawa1", "3");
                        popupWindow.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });

    }


    private void initPickTimer(TextView tvOption) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置开始时间
//        if(!tvOption.getText().equals("")){
//            dialog.setStartDate(tvOption.getText().toString());
//        }


        //设置上下年分限制
        dialog.setYearLimt(20);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
//        dialog.setMessageFormat("yyyy-MM-dd");
        dialog.setCanceledOnTouchOutside(false);
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                //获取日期转为年月日并刷新适配器
                long time = date.getTime() / 1000;
                String value = String.valueOf(time);
                stampDate = DateUtil.timeStamp2Date(value, "yyyy-MM-dd");
                tvOption.setText(stampDate);
                tvOption.setTextColor(Color.parseColor("#ffff3d4c"));
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            startUCrop(FilmDetailActivity.this, mCameraImagePath, UCrop.REQUEST_CROP, ratioX, ratioY);

            //   imgWebPower.setImageURI(mCameraImagePath);
            Glide.with(this)
                    .load(mCameraImagePath)
                    .into(imgFilm);

        } else if (requestCode == ALBUM_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                boolean moveToFirstSucceed = c.moveToFirst();
                if (!moveToFirstSucceed) {
                    ToastShow("图片异常");
                    return;
                }
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);

                Glide.with(this)
                        .load(imagePath)
                        .into(imgFilm);
            }

        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
//            Glide.with(this)
//                    .load(resultUri.toString())
//                    .into(imgFilm);

            //为什么不可以用uri
            //   imgFilm.setImageURI(resultUri);


        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            ToastShow("未知错误！");
        }

    }

    private void deleteFilm() {
        ThreadUtils.filmDelete(this, filmBean);
        finish();
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_add_film;
    }
}