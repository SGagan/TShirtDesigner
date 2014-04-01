package com.free.tshirtdesigner.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

/**
 * User: RuaTre_IT
 * Date: 2/23/14
 * Time: 3:34 PM
 */
public class UtilImage
{
    // change color tshirt with color define static
    public static Bitmap grayScaleImage(Bitmap src, String colorSource)
    {
        // constant factors
        String[] colors = colorSource.split(",");
        final double GS_RED = Double.parseDouble(colors[0]);
        final double GS_GREEN = Double.parseDouble(colors[1]);
        final double GS_BLUE = Double.parseDouble(colors[2]);
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * GS_RED);
                G = (int) (Color.green(pixel) * GS_GREEN);
                B = (int) (Color.blue(pixel) * GS_BLUE);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    public static Bitmap getCurrentBitmap(ImageView imageView)
    {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }
    //get physical address image
    public static String getRealPathFromURI(Context context, Uri contentURI)
    {
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null)
        {
            return contentURI.getPath();
        }
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    // scale image keep rate with maxWith and maxHeight
    public static Bitmap scaleImage(Bitmap bitmap, float maxWidth, float maxHeight)
    {
        double ratioX = (double)maxWidth / bitmap.getWidth();
        double ratioY = (double)maxHeight / bitmap.getHeight();
        double ratio = Math.min(ratioX, ratioY);

        int newWidth = (int)(bitmap.getWidth() * ratio);
        int newHeight = (int)(bitmap.getHeight() * ratio);

        return  Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }
}
