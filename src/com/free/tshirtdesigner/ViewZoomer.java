package com.free.tshirtdesigner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT;

public class ViewZoomer extends View
{
    private RelativeLayout rlContent;
    private TextView mText;
    private ImageView mImage;
    private float mPosX;
    private float mPosY;
    private Bitmap bitmap;
    private Context context;
    private Integer colorDefault = Color.BLACK;
    private String fontDefault = "gtw.ttf";

    private VersionedGestureDetector mDetector;

    private float mScaleFactor = 1.f;

    public ViewZoomer(Context context, String text, Integer color, String font)
    {
        super(context);
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        rlContent = (RelativeLayout) layoutInflater.inflate(R.layout.text_item, null);
        rlContent.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

        mText = (TextView) rlContent.findViewById(R.id.item_tvText);
        mText.setText(text);

        colorDefault = (color == null) ? colorDefault : color;
        mText.setTextColor(colorDefault);

        fontDefault = (font == null) ? fontDefault : font;
        Typeface fontsStyle = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontDefault);
        mText.setTypeface(fontsStyle);

        int measureWidth = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mText.measure(measureWidth, measuredHeight);
        rlContent.measure(measureWidth, measuredHeight);

        rlContent.layout(0, 0, rlContent.getMeasuredWidth(), rlContent.getMeasuredHeight());

        mDetector = VersionedGestureDetector.newInstance(context, new GestureCallback());
    }

    public ViewZoomer(Context context, Bitmap bitmap)
    {
        super(context);
        this.bitmap = bitmap;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        rlContent = (RelativeLayout) layoutInflater.inflate(R.layout.image_item, null);
        rlContent.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

        mImage = (ImageView) rlContent.findViewById(R.id.item_ivImage);
        mImage.setImageBitmap(bitmap);

        int measureWidth = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mImage.measure(measureWidth, measuredHeight);
        rlContent.measure(measureWidth, measuredHeight);

        rlContent.layout(0, 0, rlContent.getMeasuredWidth(), rlContent.getMeasuredHeight());

        mDetector = VersionedGestureDetector.newInstance(context, new GestureCallback());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (isEnabled())
        {
            mDetector.onTouchEvent(ev);
            return true;
        }
        return false;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        rlContent.draw(canvas);

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
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            invalidate();
        }
    }

    public float getmPosX()
    {
        return mPosX;
    }

    public void setmPosX(float mPosX)
    {
        this.mPosX = mPosX;
    }

    public float getmPosY()
    {
        return mPosY;
    }

    public void setmPosY(float mPosY)
    {
        this.mPosY = mPosY;
    }

    public float getmScaleFactor()
    {
        return mScaleFactor;
    }

    public void setmScaleFactor(float mScaleFactor)
    {
        this.mScaleFactor = mScaleFactor;
    }

    public Integer getColorDefault()
    {
        return colorDefault;
    }

    public void setColorDefault(Integer colorDefault)
    {
        this.colorDefault = colorDefault;
    }

    public String getFontDefault()
    {
        return fontDefault;
    }

    public void setFontDefault(String fontDefault)
    {
        this.fontDefault = fontDefault;
    }

    public String getText()
    {
        return mText.getText().toString();
    }

    public void setBackground()
    {
        rlContent.setBackgroundResource(R.drawable.item_background);
    }

    public void setBackgroundNull()
    {
        rlContent.setBackgroundDrawable(null);
    }
}