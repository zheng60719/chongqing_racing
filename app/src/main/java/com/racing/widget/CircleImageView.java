package com.racing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lottery.R;


/**
 * Created by zcs on 2017/5/25.
 */

public class CircleImageView extends ImageView {
    //外圆的宽度
    private int outCircleWidth;
    //外圆的颜色
    private int outCircleColor = Color.WHITE;
    //定义画笔
    private Paint mPaint;
    //定义内部圆的宽高
    private int viewWidth;
    private int viewHeigth;
    //这个circleImageView所加载的图片
    private Bitmap mBitmap;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = null;
        if (attrs != null) {
            array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            for (int i = 0; i < array.length(); i++) {
                switch (array.getIndex(i)) {
                    case R.styleable.CircleImageView_outCircleColor:
                        outCircleColor = array.getColor(array.getIndex(i), Color.WHITE);
                        break;
                    case R.styleable.CircleImageView_outCircleWidth:
                        outCircleWidth = (int) array.getDimension(array.getIndex(i), 2);
                        break;
                }
            }
            mPaint = new Paint();
            mPaint.setColor(outCircleColor);
            mPaint.setAntiAlias(true);//设置抗锯齿
            array.recycle();//资源回收
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWith(widthMeasureSpec);//得到测量之后的宽
        int height = measureWith(heightMeasureSpec);//高
        viewWidth = width - outCircleWidth * 2;
        viewHeigth = height - outCircleWidth * 2;
        setMeasuredDimension(width, height);//设置的是测量之后的值，，不是viewWidth
    }

    private int measureWith(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        /*
        三种模式
        MeasureSpec.AT_MOST;
        MeasureSpec.UNSPECIFIED;
        MeasureSpec.EXACTLY;
        */
        if (mode == MeasureSpec.EXACTLY) {//资源文件中若给出具体值，则使用此具体值
            result = size;
        } else {
            result = viewWidth;//其它情况下，使用默认值
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getBitmapSetted();//
        if (mBitmap != null) {
            int min = Math.min(viewWidth, viewHeigth);
            int circleCenter = min / 2;
            //把mBitmap scale为宽高为min的大小
            mBitmap = Bitmap.createScaledBitmap(mBitmap, min, min, false);
            canvas.drawCircle(circleCenter + outCircleWidth, circleCenter + outCircleWidth, circleCenter + outCircleWidth, mPaint);
            canvas.drawBitmap(createCircleBitmap(mBitmap, min), outCircleWidth, outCircleWidth, null);
        }
    }

    private Bitmap createCircleBitmap(Bitmap image, int min) {
        Bitmap bitmap = null;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //画一个和图片大小相等的画布
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(image, 0, 0, paint);
        return bitmap;
    }

    private void getBitmapSetted() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null) {
            mBitmap = bitmapDrawable.getBitmap();
        }
    }
}