package com.example.dominic.volumeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class VolumeView extends View {

    //当前音量大小
    private int currentVolume = 2;
    //最大音量大小
    private int maxVolume = 4;

    //喇叭图案
    private Bitmap loudspeaker;
    //画笔
    private Paint mPaint;
    //音量线的高度
    private float strokeWidth = 5;

    public VolumeView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public VolumeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub


        canvas.drawBitmap(loudspeaker, getWidth() / 2 - loudspeaker.getWidth() / 2, getHeight() / 2 - loudspeaker.getHeight() / 2, mPaint);
        float centre = getWidth() / 2; //圆心的坐标
        float radius = 30f; //半径的长度
        RectF rect;
        for (int i = 0; i < currentVolume; i++) {
            radius = radius + i * 12;
            rect = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
            canvas.drawArc(rect, -45, 90, false, mPaint);

        }


    }

    /**
     * 当前音量+1
     */
    public void decreaseVolume() {
        currentVolume++;
        currentVolume = currentVolume > maxVolume ? maxVolume : currentVolume;
        currentVolume = currentVolume > 0 ? currentVolume : 0;
        postInvalidate();
    }

    /**
     * 当前音量-1
     */
    public void increaseVolume() {
        currentVolume--;
        currentVolume = currentVolume > maxVolume ? maxVolume : currentVolume;
        currentVolume = currentVolume > 0 ? currentVolume : 0;
        postInvalidate();
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;

            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown)// 下滑 ，表示音量减小
                {
                    increaseVolume();
                } else// 上滑，表示音量增大
                {

                    decreaseVolume();
                }
                break;
        }

        return true;
    }


    private void initView() {
        //绘制喇叭图案
        loudspeaker = BitmapFactory.decodeResource(getResources(), R.drawable.loudspeakers);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth); // 设置音量线的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 设置音量线两端为圆心
        mPaint.setStyle(Paint.Style.STROKE); // 设置样式为空心圆
        mPaint.setColor(Color.BLACK);

    }


}
