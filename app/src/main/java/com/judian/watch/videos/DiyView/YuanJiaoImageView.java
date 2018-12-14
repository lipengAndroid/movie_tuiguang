package com.judian.watch.videos.DiyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class YuanJiaoImageView extends AppCompatImageView {

    //圆角弧度
    private float[] rids = {20.0f, 20.0f, 20.0f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    public YuanJiaoImageView(Context context) {
        super(context);
    }

    public YuanJiaoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YuanJiaoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        @SuppressLint("DrawAllocation") Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        //绘制圆角imageview
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}