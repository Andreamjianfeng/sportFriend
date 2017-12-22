package com.guet.jason.sportsfriend.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.guet.jason.sportsfriend.R;

/**
 * Created by Andream on 2017/11/4.
 * 自定义动感加载图
 */

public class LoadView extends ProgressBar{
    private final int RADIUS=60;
    private final int LOAD_WIDTH=20;
    private final int TEXT_SIZE=28;
    private final int LOAD_COLOR=0xff584f60;
    private final int LOAD_BACKGROUND_COLOR=0xff343434;
    private final int BACKGROUND_COLOR=0xff565656;
    private final int TEXT_COLOR=0xff584f60;
    private float loadWidth=dp2sp(LOAD_WIDTH);
    private float radius=dp2sp(RADIUS);
    private int textSize=sp2dp(TEXT_SIZE);
    private int loadColor=LOAD_COLOR;
    private int loadBackgroundColor=LOAD_BACKGROUND_COLOR;
    private int backgroundColor=BACKGROUND_COLOR;
    private int textColor=TEXT_COLOR;

    private Paint mPaint;
    private RectF oval;
    private Rect rect;
    private Bitmap mTarget;

    public LoadView(Context context) {
        this(context,null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval=new RectF();
        rect = new Rect();
    }
    /**
     * 获取自定义属性
     * @param attrs attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LoadView);
        loadWidth = (int) ta.getDimension(R.styleable.LoadView_loadWidth, loadWidth);
        loadColor = (int) ta.getDimension(R.styleable.LoadView_loadColor, loadColor);
        backgroundColor = (int) ta.getDimension(R.styleable.LoadView_backgroundColor, backgroundColor);
        loadBackgroundColor = (int) ta.getDimension(R.styleable.LoadView_loadBackgroundColor, loadBackgroundColor);
        textColor = (int) ta.getDimension(R.styleable.LoadView_textLoadColor, textColor);
        radius = (int) ta.getDimension(R.styleable.LoadView_radius, radius);
        textSize = (int) ta.getDimension(R.styleable.LoadView_loadTextSize, textSize);
        ta.recycle();
    }

    /**
     * dp2sp
     * @param dpVal sp
     * @return dp
     */
    private int dp2sp(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp2dp
     * @param spVal dp
     * @return sp
     */
    private int sp2dp(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            int widthSize = (int) (radius*2f+loadWidth*2f + getPaddingLeft() + getPaddingRight());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            int heightSize = (int) (radius*2f+loadWidth*2f + getPaddingTop() + getPaddingBottom());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        float mWidth=getWidth();
        float mHeight=getHeight();
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth/2f,mHeight/2,radius,mPaint);



        oval.set(mWidth / 2 - radius, mWidth / 2 - radius, mHeight / 2
                + radius, mHeight / 2+ radius);//用于定义的圆弧的形状和大小的界限
        @SuppressLint("DrawAllocation") Bitmap mBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.mytest);
        //canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),oval,mPaint);
        if(mTarget == null){
            mTarget = Bitmap.createScaledBitmap(mBmp, (int) (radius*2+loadWidth), (int) (radius*2+loadWidth), false);
        }
        if(mTarget!= mBmp){
            mBmp.recycle();
        }
        @SuppressLint("DrawAllocation") Bitmap target = Bitmap.createBitmap(mTarget, 0, 0, mTarget.getWidth(), mTarget.getHeight());

        float sx = mWidth/2f - target.getWidth() / 2;
        float sy = mHeight/2f- target.getHeight() / 2;
        canvas.drawBitmap(target, sx, sy, mPaint);

        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        String progressText= getProgress()+"%";
        mPaint.getTextBounds(progressText, 0, progressText.length(), rect);//获取文字宽高
        canvas.drawText(progressText,mWidth/2f-rect.width()/2f,mHeight/2f+rect.height()/2f,mPaint);

        mPaint.setColor(loadBackgroundColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(loadWidth);

        canvas.drawArc(oval, 0, 360, false, mPaint);  //根据进度画圆弧
        mPaint.setColor(loadColor);
        canvas.drawArc(oval, 270, (getProgress()*3.6f), false, mPaint);  //根据进度画圆弧
        canvas.restore();
    }
}
