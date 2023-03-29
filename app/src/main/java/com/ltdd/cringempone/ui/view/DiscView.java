package com.ltdd.cringempone.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.ltdd.cringempone.R;

public class DiscView extends View {
    public Drawable img;
    private Paint mCirclePaint;
    private Paint mMiddleOfDisc;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private RectF mArcBounds = new RectF();

    public DiscView(Context context) {
        super(context, null);
        initPaints();
    }

    public DiscView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initPaints();
        img = context.getResources().getDrawable(R.drawable.disc);
    }

    public DiscView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints(){
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.YELLOW);
        mMiddleOfDisc = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddleOfDisc.setStyle(Paint.Style.FILL_AND_STROKE);
//        mMiddleOfDisc.setStrokeWidth(16 * getResources().getDisplayMetrics().density);
//        mMiddleOfDisc.setStrokeCap(Paint.Cap.ROUND);
        mMiddleOfDisc.setColor(Color.WHITE);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mRadius = Math.min(w, h) / 2f;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);
        float mouthInset = 250f;
        mArcBounds.set(mouthInset, mouthInset, mRadius * 2 - mouthInset, mRadius * 2 - mouthInset);
        canvas.drawArc(mArcBounds, 0f, 360f, true, mMiddleOfDisc);
        canvas.drawCircle(mCenterX, mCenterY, 50, mMiddleOfDisc);

        Rect imgBound = canvas.getClipBounds();
        img.setBounds(imgBound);
        img.draw(canvas);
    }
}
