package com.example.graduationproject.view.Activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.graduationproject.R;
import com.example.graduationproject.base.BaseActivity;
import com.example.graduationproject.controller.AppDatabase;
import com.example.graduationproject.controller.FilmDao;
import com.example.graduationproject.model.FilmBean;
import com.example.graduationproject.model.FilmDetailBean;
import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.utils.Constants;
import com.example.graduationproject.utils.DateUtil;
import com.example.graduationproject.utils.PopupWindowThree;
import com.example.graduationproject.utils.TitleBar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;

public class AddFilmActivity extends BaseActivity implements View.OnClickListener{
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
    @BindView(R.id.img_web_power)
    ImageView imgWebPower;
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
    @BindView(R.id.ll_device)
    LinearLayout llDevice;

    private boolean isImgWatch = false;
    private boolean isImgLove = false;
    private boolean isImgWebPower = false;
    private static final int REQUEST_CODE = 101;
    private static final int RESULT_CODE = 102;
    private float ratioX = 1.1f;
    private float ratioY = 1.4f;
    private FilmDetailBean filmBean = new FilmDetailBean();
    private boolean boo = false;
    private String stampDate;
    private String path;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_film;
    }

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initData() {

    }

     @Override
      protected void initView(Bundle bundle) {


        titleBar.setTitle("添加电影");
        titleBar.setBackOnclickListener(this);

        imgWatch.setOnClickListener(this);

        imgLove.setOnClickListener(this);

        imgWebPower.setOnClickListener(this);

        imgFilm.setOnClickListener(this);

        tvFilmTime.setOnClickListener(this);

        btnSave.setOnClickListener(this);

       // etFilmName=(EditText) findViewById(R.id.bus_detail_content);

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
                if(isImgWatch){
                    imgWatch.setImageResource(R.mipmap.watch);
                }else{
                    imgWatch.setImageResource(R.mipmap.unwatch);
                }
                break;
            case R.id.img_love:
                isImgLove = !isImgLove;
                if(isImgLove){
                    imgLove.setImageResource(R.mipmap.love);
                }else{
                    imgLove.setImageResource(R.mipmap.unlove);
                }
                break;
            case R.id.tv_film_time:
                initPickTimer(tvFilmTime);
                break;
            case R.id.img_web_power:
                if(etFilmName.getText().toString().equals("")){
                    Toast.makeText(this, "请输入要搜索电影名称", Toast.LENGTH_SHORT).show();
                    break;
                }
                isImgWebPower = changeImageWatch(imgWebPower, isImgWebPower);
                if(isImgWebPower){
                    toFilmListActivity();
                }
                // isImgWebPower = !isImgWebPower;
                break;
            case R.id.btn_save:
                if(haveData()){
                    saveFilm();
                    ToastShow("保存成功！");
                }
                break;

        }
    }
    private boolean haveData(){
        if(etFilmName.getText().equals("")){
            ToastShow("请输入电影名称");
            return false;
        }
        if(etFilmDetail.getText().equals("")){
            ToastShow("请输入电影详情");
            return false;
        }
        if(etPerformer.getText().equals("")){
            ToastShow("请输入电影演员");
            return false;
        }
        if(etDirector.getText().equals("")){
            ToastShow("请输入电影导演");
            return false;
        }
        if(etFilmStyle.getText().equals("")){
            ToastShow("请输入电影类型");
            return false;
        }
        if(etReview.getText().equals("")){
            ToastShow("请输入电影感想");
            return false;
        }
        if(tvFilmTime.getText().equals("")){
            ToastShow("请输入电影时间");
            return false;
        }

        return true;
    }
    private void saveFilm(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"myfilm.db").build();
                    FilmDao filmDao = db.filmDao();
                    FilmSaveBean filmSaveBean = new FilmSaveBean();
                 //   filmSaveBean.setUid(1);
                    filmSaveBean.setFilmName(etFilmName.getText().toString());
                    if(!StringUtils.isEmpty(path)){
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

                    filmDao.insertAll(filmSaveBean);
                }
            }).start();
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
    private void toFilmListActivity(){
        Intent intent = new Intent(this,FilmListActivity.class);
        intent.putExtra("name",etFilmName.getText().toString());
        startActivityForResult(intent,REQUEST_CODE);
    }


    private void fromData(String id) {
        String url = Constants.IMDB_TITLE + "/" + Constants.IMDBKEY + "/" + id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               if(response.isSuccessful()){
                   try {
                       JSONObject jsonObject = JSON.parseObject(response.body().string());
                       String data = jsonObject.toString();
                       filmBean = JSONObject.parseObject(data,FilmDetailBean.class);
                       Log.e("haolawa",filmBean.toString());

                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
            }
        });


    }
    private void showView(){

        if(!StringUtils.isEmpty(filmBean.getTitle())){
            etFilmName.setText(filmBean.getTitle());
        }
        if(!StringUtils.isEmpty(filmBean.getImage())){
            imgFilm.setImageURI(filmBean.getImage());
            path = filmBean.getImage();
        }
        if(!StringUtils.isEmpty(filmBean.getGenres())){
            etFilmStyle.setText(filmBean.getGenres());
        }
        if(!StringUtils.isEmpty(filmBean.getReleaseDate())){
            tvFilmTime.setText(filmBean.getReleaseDate());
        }
        if(!StringUtils.isEmpty(filmBean.getDirectors())){
            etDirector.setText(filmBean.getDirectors());
        }
        if(!StringUtils.isEmpty(filmBean.getStars())){
            etPerformer.setText(filmBean.getStars());
        }
        if(!StringUtils.isEmpty(filmBean.getPlot())){
            etFilmDetail.setText(filmBean.getPlot());
        }

    }

    private void showPopupWindow(View view) {
        PopupWindowThree popupWindow = new PopupWindowThree(AddFilmActivity.this);
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
                        Log.e("haolawa1","1");
                        break;
                    case R.id.tv_item_2:
                        Log.e("haolawa1","2");
                        popupWindow.dismiss();
                        //调相册
                        checkAlbum();
                        break;
                    case R.id.tv_item_3:
                        Log.e("haolawa1","3");
                        popupWindow.dismiss();
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
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            String id = data.getStringExtra("id");
            if(!id.equals("")){
                fromData(id);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                      showView();
                }
            },2000);
        }
        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            startUCrop(AddFilmActivity.this, mCameraImagePath, UCrop.REQUEST_CROP, ratioX, ratioY);

        }
        else if(requestCode == ALBUM_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {

                String imagePath = null;
                Uri uri = data.getData();
                if (DocumentsContract.isDocumentUri(this, uri)) {
                    // 如果是document类型的Uri，则通过document id处理
                    String docId = DocumentsContract.getDocumentId(uri);
                    if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                        String id = docId.split(":")[1];
                        // 解析出数字格式的id
                        String selection = MediaStore.Images.Media._ID + "=" + id;
                        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                    } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                        imagePath = getImagePath(contentUri, null);
                    }
                }


            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是content类型的Uri，则使用普通方式处理
                imagePath = getImagePath(uri, null);
            }
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // 如果是file类型的Uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            // 根据图片路径显示图片,使用Glide图片加载库显示图片
                startUCrop(AddFilmActivity.this, imagePath, UCrop.REQUEST_CROP, ratioX, ratioY);

        }
        }
        else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);

//            Glide.with(this)
//                    .load(resultUri.toString())
//                    .into(imgFilm);


            imgFilm.setImageURI(resultUri);
           // path = resultUri.toString();
            SaveImage(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            ToastShow("未知错误！");
        }

    }


    private void SaveImage(Uri uri) {

        //创建一个子线程，将耗时任务在子线程中完成，防止主线程被阻塞
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取要保存的图片的位图
                    Bitmap bitmap = BitmapFactory.decodeStream(getAppApplication().getContentResolver().openInputStream(uri));
                    //创建一个保存的Uri
                    Uri saveUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
                    if (StringUtils.isEmpty(saveUri.toString())) {
                        Looper.prepare();
                        Looper.loop();
                        return;
                    }
                    OutputStream outputStream = getContentResolver().openOutputStream(saveUri);
                    //将位图写出到指定的位置
                    //第一个参数：格式JPEG 是可以压缩的一个格式 PNG 是一个无损的格式
                    //第二个参数：保留原图像90%的品质，压缩10% 这里压缩的是存储大小
                    //第三个参数：具体的输出流
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)) {
                        Looper.prepare();
                        path = saveUri.toString();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




}