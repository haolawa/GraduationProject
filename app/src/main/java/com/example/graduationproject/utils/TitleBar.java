package com.example.graduationproject.utils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.graduationproject.R;


public class TitleBar extends RelativeLayout {

    private ImageView ivBack;
    private View viewBack;
    private TextView tvTitle;
    private TextView tvRight;
    private ImageView ivRight;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }


    private void initView(final Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.title_bar,this);
        ivBack = rootView.findViewById(R.id.iv_back);
        viewBack = rootView.findViewById(R.id.view_back);
        tvTitle = rootView.findViewById(R.id.tv_title_name);
        tvRight = rootView.findViewById(R.id.tv_right);
        ivRight = rootView.findViewById(R.id.iv_right);
    }

    /**
     * 设置标题
     */
    public void setTitle(String string) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(string);
    }

    /**
     * 设置标题
     */
    public void setTitle(int resid) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(resid);
    }

    public void setBackOnclickListener(final Activity activity) {
        ivBack.setVisibility(VISIBLE);
        viewBack.setVisibility(VISIBLE);
        viewBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    /**
     * 设置右边文字和点击事件
     */
    public void setRightTV(String string, OnClickListener listener) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(string);
        tvRight.setOnClickListener(listener);
    }

    /**
     * 设置右边图片和点击事件
     */
    public void setRightIV(Context context, int id, OnClickListener onClickListener){
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageResource(id);
        ivRight.setOnClickListener(onClickListener);
    }
}