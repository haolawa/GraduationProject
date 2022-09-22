package com.example.graduationproject.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.graduationproject.R;
import com.example.graduationproject.model.FilmBean;
import com.example.graduationproject.model.FilmSaveBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class FilmSaveAdapter extends BaseQuickAdapter<FilmSaveBean, BaseViewHolder> {

    public FilmSaveAdapter(int layoutResId, @NonNull List<FilmSaveBean> data) {
        super(layoutResId,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, FilmSaveBean bean) {

        SimpleDraweeView imgFilm = holder.getView(R.id.img_film);
        TextView tvFilmName =  holder.getView(R.id.tv_film_name);
        TextView tvPerformName =  holder.getView(R.id.tv_perform_name);
//        TextView tvFilmType = holder.getView(R.id.tv_film_type);
        ImageView imgCollect =  holder.getView(R.id.img_collect);
        ImageView imgWatch =  holder.getView(R.id.img_watch);
        TextView tvDate =  holder.getView(R.id.tv_date);
        if(!StringUtils.isEmpty(bean.getFilmImg())){
            imgFilm.setImageURI(bean.getFilmImg());
        }

        if(!StringUtils.isEmpty(bean.getFilmName())){
            tvFilmName.setText(bean.getFilmName());
        }

        if(!StringUtils.isEmpty(bean.getFilmTime())){
            tvDate.setText(bean.getFilmTime());
        }

        if (!StringUtils.isEmpty(bean.getFilmDirectors())){
            tvPerformName.setText(bean.getFilmDirectors());
        }
        if (bean.isLove){
            imgCollect.setImageResource(R.mipmap.love);
        }else{
            imgCollect.setImageResource(R.mipmap.unlove);
        }
        if (bean.isWatch()){
            imgWatch.setImageResource(R.mipmap.watch);
        }else{
            imgWatch.setImageResource(R.mipmap.unwatch);
        }


    }

}