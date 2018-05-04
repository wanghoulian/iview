package cn.gcd.sb.widget;

import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class RippleView extends View {
    private static final String TAG = "RippleView";
    private Paint anionPaint;

    private float circleRadius;
    private int circleAlpha;
    private float loopRadius;
    private int loopAlpha;
    private Paint circlePaint;
    private Paint loopPaint;
    private AnimatorSet rippleSet;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(2);
        circlePaint.setStyle(Paint.Style.FILL);

        loopPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        loopPaint.setColor(Color.WHITE);
        loopPaint.setStrokeWidth(2);
        loopPaint.setStyle(Paint.Style.STROKE);

        anionPaint = new Paint();
        anionPaint.setColor(Color.WHITE);
        anionPaint.setStrokeWidth(2);
        anionPaint.setStyle(Paint.Style.STROKE);
        setupAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "density:" +canvas.getDensity());
        circlePaint.setAlpha(circleAlpha);
        canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, circleRadius/1.5f, circlePaint);
        loopPaint.setAlpha(loopAlpha);
        canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, loopRadius/1.5f, loopPaint);
    }

    private void setupAnimator() {
        final LinearInterpolator baseInterpolator = new LinearInterpolator();
        float density = getContext().getResources().getDisplayMetrics().density;

        PropertyValuesHolder radiusCircleUpHolder = PropertyValuesHolder.ofFloat("radius", 0.0f, 18f*density/1.5f);
        PropertyValuesHolder alphaCircleUpHolder = PropertyValuesHolder.ofInt("alpha", 0, 255);
        ValueAnimator circleUpAnimator = new ValueAnimator();
        circleUpAnimator.setValues(radiusCircleUpHolder, alphaCircleUpHolder);
        circleUpAnimator.setDuration(300);
        circleUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                circleAlpha = (int) valueAnimator.getAnimatedValue("alpha");
                circleRadius = (float) valueAnimator.getAnimatedValue("radius");
                invalidate();
            }
        });

        PropertyValuesHolder radiusCircleMiddleHolder = PropertyValuesHolder.ofFloat("radius", 18f, 16f*density/1.5f);
        ValueAnimator circleMiddleAnimator = new ValueAnimator();
        circleMiddleAnimator.setValues(radiusCircleMiddleHolder);
        circleMiddleAnimator.setDuration(100);
        circleMiddleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                circleRadius = (float) valueAnimator.getAnimatedValue("radius");
                invalidate();
            }
        });

        PropertyValuesHolder radiusCircleDownHolder = PropertyValuesHolder.ofFloat("radius", 16f, 10f*density/1.5f);
        PropertyValuesHolder alphaCircleDownHolder = PropertyValuesHolder.ofInt("alpha", 255, 102);
        ValueAnimator circleDownAnimator = new ValueAnimator();
        circleDownAnimator.setValues(radiusCircleDownHolder, alphaCircleDownHolder);
        circleDownAnimator.setDuration(200);
        circleDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                circleAlpha = (int) valueAnimator.getAnimatedValue("alpha");
                circleRadius = (float) valueAnimator.getAnimatedValue("radius");
                invalidate();
            }
        });



        PropertyValuesHolder radiusLoopUpHolder = PropertyValuesHolder.ofFloat("radius", 0f, 30f*density/1.5f);
        PropertyValuesHolder alphaLoopUpHolder = PropertyValuesHolder.ofInt("alpha", 0, 102);
        ValueAnimator loopUpAnimator = new ValueAnimator();
        loopUpAnimator.setValues(radiusLoopUpHolder, alphaLoopUpHolder);
        loopUpAnimator.setDuration(600);
        loopUpAnimator.setInterpolator(baseInterpolator);
        loopUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                loopAlpha = (int) valueAnimator.getAnimatedValue("alpha");
                loopRadius = (float) valueAnimator.getAnimatedValue("radius");
                invalidate();
            }
        });

        PropertyValuesHolder radiusLoopDownHolder = PropertyValuesHolder.ofFloat("radius", 30f*density/1.5f, 36f*density/1.5f);
        PropertyValuesHolder alphaLoopDownHolder = PropertyValuesHolder.ofInt("alpha", 102, 0);
        ValueAnimator loopDownAnimator = new ValueAnimator();
        loopDownAnimator.setValues(radiusLoopDownHolder, alphaLoopDownHolder);
        loopDownAnimator.setDuration(600);
        loopDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                loopAlpha = (int) valueAnimator.getAnimatedValue("alpha");
                loopRadius = (float) valueAnimator.getAnimatedValue("radius");
                invalidate();
            }
        });

        rippleSet = new AnimatorSet();
        rippleSet.setInterpolator(baseInterpolator);
        rippleSet.play(circleUpAnimator).before(circleMiddleAnimator).before(circleDownAnimator).with(loopUpAnimator).before(loopDownAnimator);
    }

    public void startAnimator(long delay) {
        if (rippleSet.isStarted() || rippleSet.isRunning()) {
            reset();
        }
        rippleSet.setStartDelay(delay);
        rippleSet.start();
    }

    public void reset() {
        rippleSet.end();
        rippleSet.removeAllListeners();
        circleRadius = 0;
        loopRadius = 0;
        circleAlpha = 0;
        loopAlpha = 0;
        clearAnimation();
        invalidate();
    }
}
