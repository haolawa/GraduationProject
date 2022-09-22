package com.example.graduationproject.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.graduationproject.R;
import com.facebook.common.soloader.SoLoaderShim;

public class LoadingPointView extends View {
    public static final int MESSAGE_ID = 0;
    private boolean is = true;
    //白色圆点
    private Paint mWhitePaint;
    //绿色圆点
    private Paint mGreenPaint;
    //半径
    private int mRadius,mCircleX,mCircleY,mPoint;
    //下一个被选中的圆点的index
    private int mIndex;
    private android.os.Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ++mIndex;
            if (mIndex == 5) {
                mIndex = 0;
            }
            postInvalidate();
        }

//        @Override
//        public void close() throws SecurityException {
//
//        }
    };

    public LoadingPointView(Context context) {
        this(context, null);
    }

    public LoadingPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParmas(context);
    }

    private void initParmas(Context context) {
        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setColor(ContextCompat.getColor(context, R.color.white));

        mGreenPaint = new Paint();
        mGreenPaint.setAntiAlias(true);
        mGreenPaint.setStyle(Paint.Style.FILL);
        mGreenPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));

        mPoint = Px2DpUtil.dp2px(context, 2);
        mCircleX = Px2DpUtil.dp2px(context, 40);
        mCircleY = Px2DpUtil.dp2px(context, 40);
        mRadius = Px2DpUtil.dp2px(context, 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 5; i++) {
            //修改圆心x轴坐标，来画出多个圆点
            canvas.drawCircle(getHeight() / 2 + mRadius * i * 2 + 5 * i, getHeight() / 2, mRadius, mWhitePaint);
        }
        //动态修改绿色圆点的位置
        canvas.drawCircle(getHeight() / 2 + mRadius * mIndex * 2 + 5 * mIndex, getHeight() / 2, mRadius, mGreenPaint);
        //发送消息不断绘制，以达到无限循环的效果
        mHandler.sendEmptyMessageDelayed(MESSAGE_ID, 200);
    }
    //停止动画
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        //这里可能有问题
        if(is){
            mHandler.removeMessages(MESSAGE_ID);
            mHandler = null;
            is = false;
        }else{
            return;
        }

    }
}