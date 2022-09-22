package com.example.graduationproject.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.example.graduationproject.R;


public class PopupWindowThree extends PopupWindow implements View.OnClickListener {

    private OnItemClickListener onItemClickListener;
    private TextView tvTitle;
    private TextView tvItem1;
    private TextView tvItem2;
    private TextView tvItem3;
    private View view_1;

    public PopupWindowThree(@NonNull Context context) {
        initView(context);
    }

    private void initView(Context context) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_window_three,null);
        setContentView(popupView);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.shop_popup_window_anim);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#66000000")));
        setFocusable(true);

        tvTitle = popupView.findViewById(R.id.tv_title);
        tvItem1 = popupView.findViewById(R.id.tv_item_1);
        tvItem2 = popupView.findViewById(R.id.tv_item_2);
        tvItem3 = popupView.findViewById(R.id.tv_item_3);
        view_1 = popupView.findViewById(R.id.view_1);
        LinearLayout llRoot = popupView.findViewById(R.id.ll_root);
        tvItem1.setOnClickListener(this);
        tvItem2.setOnClickListener(this);
        tvItem3.setOnClickListener(this);

        llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public void setTexts(String[] strings) {
        tvTitle.setText(strings[0]);
        tvItem1.setText(strings[1]);
        tvItem2.setText(strings[2]);
        tvItem3.setText(strings[3]);
        if(StringUtils.isEmpty(strings[0])){
            tvTitle.setVisibility(View.GONE);
            view_1.setVisibility(View.GONE);
        }
    }
}
