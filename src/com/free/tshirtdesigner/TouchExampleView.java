package com.free.tshirtdesigner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TouchExampleView extends View {
    private Drawable mIcon;
    RelativeLayout lliMage;
    private float mPosX;
    private float mPosY;

    private VersionedGestureDetector mDetector;
    private float mScaleFactor = 1.f;
    
    public TouchExampleView(Context context) {
        this(context, null, 0);
    }
    
    public TouchExampleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TouchExampleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        lliMage = (RelativeLayout) layoutInflater.inflate(R.layout.text_or_image,null);
        int measureWidth = View.MeasureSpec.makeMeasureSpec(70, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(70, View.MeasureSpec.EXACTLY);
        lliMage.measure(measureWidth, measuredHeight);
        lliMage.layout(0, 0, lliMage.getMeasuredWidth(), lliMage.getMeasuredHeight());

        mIcon = context.getResources().getDrawable(R.drawable.ic_launcher);
        mIcon.setBounds(0, 0, mIcon.getIntrinsicWidth(), mIcon.getIntrinsicHeight());
        
        mDetector = VersionedGestureDetector.newInstance(context, new GestureCallback());
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        lliMage.draw(canvas);

        canvas.restore();
    }

    private class GestureCallback implements VersionedGestureDetector.OnGestureListener {
        public void onDrag(float dx, float dy) {
            mPosX += dx;
            mPosY += dy;
            invalidate();
        }

        public void onScale(float scaleFactor) {
            mScaleFactor *= scaleFactor;

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
        }
    }
}