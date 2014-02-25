package com.free.tshirtdesigner.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * User: Admin
 * Date: 2/25/14
 * Time: 8:44 PM
 */
public class UtilTextView
{
    public static void changeTextFont(TextView textView, AssetManager asm, String fontName)
    {
        Typeface fontsStyle = Typeface.createFromAsset(asm, "Gill Sans MT.ttf");
        textView.setTypeface(fontsStyle);
    }

    public static void changeTextSize(TextView textView, float size)
    {
        textView.setTextSize(size);
    }

    public static void changeTextColor(TextView textView, int color)
    {
        textView.setTextColor(color);
    }
}
