package com.mokee.centers;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MyProgressView extends View {

    //分段颜色
    private static final int[] SECTION_COLORS = { Color.parseColor("#f63954"), Color.parseColor("#fdbd50")};
    private static final int[] SECTION_COLORS_1 = { Color.parseColor("#f84f54"), Color.parseColor("#fdbd50")};
    private float maxCount = 100;
    private float currentCount = 0;
    private Paint mPaint;
    private Paint mCirclePaint;
    private int mWidth, mHeight;

    private boolean drawCircle = false;
    private int circleX = 0;

        public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        public MyProgressView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MyProgressView(Context context) {
            this(context, null);
        }

        private void init(Context context) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth((float) 40.0);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeCap(Paint.Cap.ROUND);

            mCirclePaint =  new Paint();
            mCirclePaint.setAntiAlias(true);
            mCirclePaint.setStrokeWidth((float) 40.0);
            mCirclePaint.setStyle(Paint.Style.STROKE);
            mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
            mCirclePaint.setColor(Color.parseColor("#f63954"));

            startValue();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            RectF rectBlackBg = new RectF(20, 20, mWidth - 20, mHeight - 20);
            float section = currentCount / maxCount;
            canvas.drawArc(rectBlackBg, 230, section * 320, false, mPaint);
            if(drawCircle){
                canvas.drawCircle(circleX , getCircleY(circleX) ,5 , mCirclePaint);
            }
//            if(drawSan){
//                sanPaint.setXfermode(PorterDuff.Mode.XOR);
//                canvas.drawArc(rectBlackBg, arcPosition, 100, false, sanPaint);
//
//
//            }
        }


    private void startValue(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0 , 100);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float section = currentCount / maxCount;
                int[] colors = new int[2];
                System.arraycopy(SECTION_COLORS, 0, colors, 0, 2);
                LinearGradient shader = new LinearGradient(3, 3, (mWidth - 3)
                        * section, mHeight - 3, colors, null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                currentCount = (int)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //开始画圆
                drawCircle = true;
                startDrawCircle();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.start();
    }


        private int dipToPx(int dip) {
            float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
        }


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
            if (widthSpecMode == MeasureSpec.EXACTLY
                    || widthSpecMode == MeasureSpec.AT_MOST) {
                mWidth = widthSpecSize;
            } else {
                mWidth = 0;
            }
            if (heightSpecMode == MeasureSpec.AT_MOST
                    || heightSpecMode == MeasureSpec.UNSPECIFIED) {
                mHeight = dipToPx(15);
            } else {
                mHeight = heightSpecSize;
            }
            setMeasuredDimension(mWidth, mHeight);
        }


    private float getCircleY(int x ){
        int r = mWidth/2-20 ;
        double y = -Math.sqrt(r*r - (x-mWidth/2)*(x-mWidth/2))+mWidth/2;
        return (float)y;
    }


    private void startDrawCircle(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(30 ,70);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                circleX = (int)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //循环画 光纤
                startDrawGuang();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.start();
    }


    private void startDrawGuang(){

        ValueAnimator valueAnimator = ValueAnimator.ofInt(-135 ,235);
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int[] colors = new int[2];
                System.arraycopy(SECTION_COLORS_1, 0, colors, 0, 2);
                int a = (int)valueAnimator.getAnimatedValue();
                int x0 =mWidth /2 ;
                int y0 = mWidth/2;
                int r = mWidth/2-20 ;
                LinearGradient shader = new LinearGradient(x0   +   (float) (r   *   Math.cos(a   *  Math.PI   /180   )), y0   +   (float)( r   *   Math.sin(a   *  Math.PI   /180   )), x0   +   (float) (r   *   Math.cos((a+180)   *  Math.PI   /180   ))
                        , y0   +   (float)( r   *   Math.sin((a+180)   *  Math.PI   /180   )), colors, null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                invalidate();
            }
        });
        valueAnimator.start();



    }

}
