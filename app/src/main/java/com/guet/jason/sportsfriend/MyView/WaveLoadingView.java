package com.guet.jason.sportsfriend.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by andream on 17/11/14.
 * 自定义水纹加载动画
 */
public class WaveLoadingView extends View {
    //绘制波纹
    private Paint mWavePaint;
    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);//设置mode 为XOR
    //绘制圆
    private Paint mCirclePaint;
    private Canvas mCanvas;//我们自己的画布
    private Bitmap mBitmap;
    private int y = 200;
    private int x = 0;

    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWavePaint = new Paint();
        mCirclePaint = new Paint();
        mTextPaint = new Paint();
        mPaint = new Paint();
        rect = new Rect();
        mPaint.setStrokeWidth(10);
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0x2800ff66);
    }

    @SuppressLint("DrawAllocation")
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            int widthSize = 200 + getPaddingLeft() + getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            int heightSize = 200 + getPaddingTop() + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBitmap = Bitmap.createBitmap(mHeight, mWidth, Bitmap.Config.ARGB_8888); //生成一个bitmap
        mCanvas = new Canvas(mBitmap);//讲bitmp放在我们自己的画布上，实际上mCanvas.draw的时候 改变的是这个bitmap对象
    }

    private boolean isLeft = false;
    private Path mPath;
    private Paint mPaint;
    private  int mHeight=200;
    private  int mWidth=200;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight=getHeight();
        mWidth=getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (x > 50) {
            isLeft = true;
        } else if (x < -50) {
            isLeft = false;
        }
        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        mCirclePaint.setColor(0x0f99cc60);
        mWavePaint.setColor(0x0033b5e5);

        mCanvas.drawCircle(mHeight/2, mWidth/2, mHeight/2, mCirclePaint);
        mPaint.setStyle(Paint.Style.FILL);

        mPath.reset();
        int testHeight=mHeight/100*(100-60);
        mPath.moveTo(0, testHeight);
        mPath.cubicTo(mHeight/12*5, testHeight + x, testHeight, testHeight - x, mHeight, testHeight);
        mPath.lineTo(mHeight, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();


        mPaint.setXfermode(mMode);
        //  Log.e("TAT", String.valueOf(x));
        mCanvas.drawPath(mPath, mPaint);
        /*
        mCanvas.drawRect(100, 100, 550, 550, mWavePaint);*/

        canvas.drawBitmap(mBitmap, 0, 0, null);
        postInvalidateDelayed(1);
        // super.onDraw(canvas);
        String progressText= mPercent+"%";
        mTextPaint.getTextBounds(progressText, 0, progressText.length(), rect);//获取文字宽高
        mTextPaint.setColor(0xff584f60);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(68);
        canvas.drawText(progressText,mWidth/2f-rect.width()/2f,mHeight/2f+rect.height()/2f,mTextPaint);

    }
    private Paint mTextPaint;
    private int mPercent = 0;
    private Rect rect;
    public void setPercent(int percent){
        mPercent = percent;
    }

}
