package com.example.graduationproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.graduationproject.R;
import com.example.graduationproject.model.FilmBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
public class FilmListAdapter extends BaseQuickAdapter<FilmBean, BaseViewHolder> {

    public FilmListAdapter(int layoutResId, @NonNull List<FilmBean> data) {
        super(layoutResId,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, FilmBean bean) {

        SimpleDraweeView imgFilm = holder.getView(R.id.img_film);
        TextView tvFilmName =  holder.getView(R.id.tv_film_name);
        TextView tvPerformName =  holder.getView(R.id.tv_perform_name);
//        TextView tvFilmType = holder.getView(R.id.tv_film_type);
        ImageView imgCollect =  holder.getView(R.id.img_collect);
        ImageView imgWatch =  holder.getView(R.id.img_watch);
        TextView tvDate =  holder.getView(R.id.tv_date);
        if(!StringUtils.isEmpty(bean.getImage())){
            imgFilm.setImageURI(bean.getImage());
        }
        if(!StringUtils.isEmpty(bean.getTitle())){
            tvFilmName.setText(bean.getTitle());
        }
        if(!StringUtils.isEmpty(bean.getDescription())){
            tvDate.setText(bean.getDescription());
        }






    }

}

