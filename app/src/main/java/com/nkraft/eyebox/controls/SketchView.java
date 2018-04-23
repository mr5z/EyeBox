package com.nkraft.eyebox.controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SketchView extends View {

    PointF touchDownPoint;
    private Path path = new Path();
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SketchView(Context context) {
        super(context);
        init();
    }

    public SketchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SketchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SketchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, linePaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        PointF touchPoint = new PointF(event.getX(), event.getY());

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                handleTouchDown(touchPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                handleTouchMove(touchPoint);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                handleTouchUp(touchPoint);
                break;
        }

        return true;
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    public Bitmap bitmap() {
        Bitmap returnedBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        }
        else {
            canvas.drawColor(Color.WHITE);
        }
        draw(canvas);
        return returnedBitmap;
    }

    void init() {
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(0);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    void handleTouchDown(PointF touchPoint) {
        touchDownPoint = touchPoint;
        path.moveTo(touchPoint.x, touchPoint.y);
    }
    private static final float TOUCH_TOLERANCE = 4;
    void handleTouchMove(PointF touchPoint) {
        float mX = touchDownPoint.x;
        float mY = touchDownPoint.y;
        float dx = Math.abs(touchPoint.x - touchDownPoint.x);
        float dy = Math.abs(touchPoint.y - touchDownPoint.y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(mX, mY, (touchPoint.x + mX) / 2, (touchPoint.y + mY) / 2);
            touchDownPoint = new PointF(touchPoint.x, touchPoint.y);
            invalidate();
        }
    }

    void handleTouchUp(PointF touchPoint) {

    }

}
