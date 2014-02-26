package com.free.tshirtdesigner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TouchExampleView extends View
{
    private Drawable mIcon;
    private TextView mText;
    RelativeLayout lliMage;
    private float mPosX;
    private float mPosY;
    private String font;
    private String color;

    private VersionedGestureDetector mDetector;
    private float mScaleFactor = 1.f;

    public TouchExampleView(Context context, String text, ViewDetail viewDetail)
    {
        super(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        lliMage = (RelativeLayout) layoutInflater.inflate(R.layout.text_or_image, null);
        mText = (TextView) lliMage.findViewById(R.id.main_tvText);
        if (viewDetail != null)
        {
            lliMage.measure(viewDetail.getWidth(), viewDetail.getHeight());
            lliMage.layout(0, 0, lliMage.getMeasuredWidth(), lliMage.getMeasuredHeight());
            mText.setText(viewDetail.getText());
        }
        else
        {
            int measureWidth = View.MeasureSpec.makeMeasureSpec(70, View.MeasureSpec.EXACTLY);
            int measuredHeight = View.MeasureSpec.makeMeasureSpec(70, View.MeasureSpec.EXACTLY);
            lliMage.measure(measureWidth, measuredHeight);

            lliMage.layout(0, 0, lliMage.getMeasuredWidth(), lliMage.getMeasuredHeight());

            mText.setText(text);
        }

        mDetector = VersionedGestureDetector.newInstance(context, new GestureCallback());
    }

    public ViewDetail getViewDetail()
    {
        ViewDetail viewDetail = new ViewDetail();
        viewDetail.setWidth(lliMage.getWidth());
        viewDetail.setHeight(lliMage.getHeight());
        int[] cordinate = new int[2];
        lliMage.getLocationOnScreen(cordinate);
        viewDetail.setCoordinate(cordinate);
        return viewDetail;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        mDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        lliMage.draw(canvas);
        lliMage.setBackgroundResource(R.drawable.box_move);

        canvas.restore();
    }

    private class GestureCallback implements VersionedGestureDetector.OnGestureListener
    {
        public void onDrag(float dx, float dy)
        {
            mPosX += dx;
            mPosY += dy;
            invalidate();
        }

        public void onScale(float scaleFactor)
        {
            mScaleFactor *= scaleFactor;

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
        }
    }

    public String getFont()
    {
        return font;
    }

    public void setFont(String font)
    {
        this.font = font;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

}