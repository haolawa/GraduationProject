package com.example.graduationproject.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.graduationproject.R;
import com.example.graduationproject.model.FilmBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class FilmListAdapter extends BaseQuickAdapter<FilmBean, BaseViewHolder> {

    public FilmListAdapter() {
        super(R.layout.item_film_list);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, FilmBean bean) {

        SimpleDraweeView imgFilm = holder.getView(R.id.img_film);
        TextView tvFilmName = holder.getView(R.id.tv_film_name);
        TextView tvTime = holder.getView(R.id.tv_time);
        ImageView imgCollect = holder.getView(R.id.img_collect);
        ImageView imgWatch = holder.getView(R.id.img_watch);
        TextView tvDate = holder.getView(R.id.tv_date);
        imgCollect.setVisibility(View.GONE);
        tvTime.setText("简介");
        imgWatch.setVisibility(View.GONE);
        if (!StringUtils.isEmpty(bean.getImage())) {
            imgFilm.setImageURI(bean.getImage());
        }
        if (!StringUtils.isEmpty(bean.getTitle())) {
            tvFilmName.setText(bean.getTitle());
        }
        if (!StringUtils.isEmpty(bean.getDescription())) {
            tvDate.setText(bean.getDescription());
        }
    }
}

