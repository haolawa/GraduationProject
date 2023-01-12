package com.example.graduationproject.view.Activity

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.example.graduationproject.R
import com.example.graduationproject.contract.FilmDetailContract
import com.example.graduationproject.databinding.ActivityAddFilmBinding
import com.example.graduationproject.model.FilmDetailBean
import com.example.graduationproject.model.FilmSaveBean
import com.example.graduationproject.presenter.FilmDetailPresenter
import com.example.graduationproject.utils.*
import com.example.graduationproject.utils.CameraUtil.ALBUM_REQUEST_CODE
import com.example.graduationproject.utils.CameraUtil.CAMERA_REQUEST_CODE
import com.yalantis.ucrop.UCrop

class AddFilmActivity : AppCompatActivity(), FilmDetailContract.IView {

    private var isImgWatch = false
    private var isImgLove = false
    private var isImgWebPower = false
    private val ratioX = 1.1f
    private val ratioY = 1.4f
    private var filmBean = FilmDetailBean()
    private var stampDate: String? = null
    private var path: String? = null
    private lateinit var cameraUtil: CameraUtil
    private lateinit var viewBinding: ActivityAddFilmBinding
    private val emptyString = ""
    private var presenter: FilmDetailPresenter? = null//presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddFilmBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initData()
    }

    fun initData() {
        cameraUtil = CameraUtil(this)
        viewBinding.apply {
            titleBar.setTitle("添加电影")
            titleBar.setBackOnclickListener(this@AddFilmActivity)
            imgWatch.setOnClickListener {
                isImgWatch = !isImgWatch
                if (isImgWatch) {
                    viewBinding.imgWatch.setImageResource(R.mipmap.watch)
                } else {
                    viewBinding.imgWatch.setImageResource(R.mipmap.unwatch)
                }
            }
            imgLove.setOnClickListener {
                isImgLove = !isImgLove
                if (isImgLove) {
                    viewBinding.imgLove.setImageResource(R.mipmap.love)
                } else {
                    viewBinding.imgLove.setImageResource(R.mipmap.unlove)
                }
            }
            imgWebPower.setOnClickListener {
                if (viewBinding.etFilmName.text.toString() == "") {
                    ToastShow("请输入要搜索电影名称！")
                }
                toFilmListActivity()
            }
            imgFilm.setOnClickListener {
                showPopupWindow(it)
            }
            tvFilmTime.setOnClickListener {
                initPickTimer(it as TextView?)
            }
            btnSave.setOnClickListener {
                if (haveData()) {
                    saveFilm()
                    ToastShow("保存成功！")
                    finish()
                }
//                else {
//                    ToastShow("保存失败！")
//                }
            }

            etFilmName.movementMethod = ScrollingMovementMethod.getInstance()
        }
    }


    private fun haveData(): Boolean {
        if (StringUtils.equals(viewBinding.etFilmName.text, emptyString)) {
            ToastShow("请输入电影名称")
            return false
        }
        if (StringUtils.equals(viewBinding.tvFilmTime.text, emptyString)) {
            ToastShow("请输入电影时间")
            return false
        }
        if (StringUtils.equals(viewBinding.etFilmStyle.text, emptyString)) {
            ToastShow("请输入电影类型")
            return false
        }
        return true
    }

    private fun saveFilm() {
        // TODO: 2022/11/19
        val filmSaveBean = FilmSaveBean()
        filmSaveBean.setFilmName(viewBinding.etFilmName.text.toString())
        if (!StringUtils.isEmpty(path)) {
            filmSaveBean.setFilmImg(path)
        }
        filmSaveBean.setFilmDetail(viewBinding.etFilmDetail.text.toString())
        filmSaveBean.setFilmNote(viewBinding.etReview.text.toString())
        filmSaveBean.setFilmDirectors(viewBinding.etDirector.text.toString())
        filmSaveBean.setFilmTime(viewBinding.tvFilmTime.text.toString())
        filmSaveBean.setFilmPerformer(viewBinding.etPerformer.text.toString())
        filmSaveBean.setFilmType(viewBinding.etFilmStyle.text.toString())
        filmSaveBean.setLove(isImgLove)
        filmSaveBean.setWatch(isImgWatch)
        ThreadUtils.insertData(this, filmSaveBean)
    }

    //改变按钮背景
    private fun changeImageWatch(img: ImageView?, isSelect: Boolean): Boolean {
        if (isSelect) {
            img!!.setBackgroundColor(resources.getColor(R.color.secondary_text))
            return false
        } else {
            img!!.setBackgroundColor(resources.getColor(R.color.teal_200))
            return true
        }
    }

    private fun toFilmListActivity() {
        val intent = Intent(this, FilmListActivity::class.java)
        intent.putExtra("name", viewBinding.etFilmName.text.toString())
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun getData(id: String?) {
        // TODO: 2022/11/26 MVP
        if (null == presenter) {
            presenter = FilmDetailPresenter(this)
        }
        presenter!!.requestFilmListResult("${Constants.IMDB_TITLE}/$id")
    }

    private fun showPopupWindow(view: View) {
        val popupWindow = PopupWindowThree(this@AddFilmActivity)
        val strings = arrayOf("", "拍照", "从相册选择", "取消")
        popupWindow.setTexts(strings)
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0)
        popupWindow.setOnItemClickListener { v ->
            when (v.id) {
                R.id.tv_item_1 -> {
                    popupWindow.dismiss()
                    //调相机
                    cameraUtil.checkCamera()
                }
                R.id.tv_item_2 -> {
                    popupWindow.dismiss()
                    //调相册
                    cameraUtil.checkAlbum()
                }
                R.id.tv_item_3 -> {
                    popupWindow.dismiss()
                }
            }
        }
    }

    private fun initPickTimer(tvOption: TextView?) {
        val dialog = DatePickDialog(this)
        //设置开始时间
//        if(!tvOption.getText().equals("")){
//            dialog.setStartDate(tvOption.getText().toString());
//        }


        //设置上下年分限制
        dialog.setYearLimt(20)
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(DateType.TYPE_YMD)
        //设置消息体的显示格式，日期格式
//        dialog.setMessageFormat("yyyy-MM-dd");
        dialog.setCanceledOnTouchOutside(false)
        //设置选择回调
        dialog.setOnChangeLisener(null)
        //设置点击确定按钮回调
        dialog.setOnSureLisener { date -> //获取日期转为年月日并刷新适配器
            val time = date.time / 1000
            val value = time.toString()
            stampDate = DateUtil.timeStamp2Date(value, "yyyy-MM-dd")
            tvOption!!.text = stampDate
            tvOption.setTextColor(Color.parseColor("#ffff3d4c"))
        }
        dialog.show()
    }


    @SuppressLint("Range")
    private fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        val cursor = contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            getData(data!!.getStringExtra("id"))//tt11405250
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            cameraUtil.startUCrop(cameraUtil.mCameraImagePath, UCrop.REQUEST_CROP, ratioX, ratioY)
        } else if (requestCode == ALBUM_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                var imagePath: String? = null
                val uri = data.data
                if (DocumentsContract.isDocumentUri(this, uri)) {
                    // 如果是document类型的Uri，则通过document id处理
                    val docId = DocumentsContract.getDocumentId(uri)
                    if ("com.android.providers.media.documents" == uri!!.authority) {
                        val id = docId.split(":").toTypedArray()[1]
                        // 解析出数字格式的id
                        val selection = MediaStore.Images.Media._ID + "=" + id
                        imagePath =
                            getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
                    } else if ("com.android.providers.downloads.documents" == uri.authority) {
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content: //downloads/public_downloads"),
                            java.lang.Long.valueOf(docId)
                        )
                        imagePath = getImagePath(contentUri, null)
                    }
                } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
                    // 如果是content类型的Uri，则使用普通方式处理
                    imagePath = getImagePath(uri, null)
                } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                    // 如果是file类型的Uri，直接获取图片路径即可
                    imagePath = uri.path
                }
                // 根据图片路径显示图片,使用Glide图片加载库显示图片
                cameraUtil.startUCrop(imagePath, UCrop.REQUEST_CROP, ratioX, ratioY)
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)

            Glide.with(this)
                .load(resultUri)
                .into(viewBinding.imgFilm);
            SaveImage(resultUri)
        } else if (resultCode == UCrop.RESULT_ERROR) {
            ToastShow("未知错误！")
        }
    }

    private fun SaveImage(uri: Uri?) {

        //创建一个子线程，将耗时任务在子线程中完成，防止主线程被阻塞
        Thread(Runnable {
            try {
                //获取要保存的图片的位图
                val bitmap = BitmapFactory.decodeStream(
                    application.contentResolver.openInputStream(
                        uri!!
                    )
                )
                //创建一个保存的Uri
                val saveUri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    ContentValues()
                )
                if (StringUtils.isEmpty(saveUri.toString())) {
                    Looper.prepare()
                    Looper.loop()
                    return@Runnable
                }
                val outputStream = contentResolver.openOutputStream(
                    saveUri!!
                )
                //将位图写出到指定的位置
                //第一个参数：格式JPEG 是可以压缩的一个格式 PNG 是一个无损的格式
                //第二个参数：保留原图像90%的品质，压缩10% 这里压缩的是存储大小
                //第三个参数：具体的输出流
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)) {
                    Looper.prepare()
                    path = saveUri.toString()
                    Looper.loop()
                } else {
                    Looper.prepare()
                    Looper.loop()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    companion object {
        private const val REQUEST_CODE = 101
        private const val RESULT_CODE = 102
    }

    private fun ToastShow(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun handleFilmDetailResult(result: FilmDetailBean) {
        viewBinding.run {
            etFilmName.setText(result.getTitle() ?: "")
            path = result.getImage() ?: ""
            imgFilm.setImageURI(path)
            etFilmStyle.setText(result.getGenres() ?: "")
            tvFilmTime.setText(result.getReleaseDate()?.toString() ?: "")
            etDirector.setText(result.getDirectors() ?: "")
            etPerformer.setText(result.getStars() ?: "")
            etFilmDetail.setText(result.getPlot() ?: "")
        }
    }

    override fun showToast(resId: Int) {
        TODO("Not yet implemented")
    }

    override fun showToast(text: String?) {
        TODO("Not yet implemented")
    }
}