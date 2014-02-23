package com.free.tshirtdesigner.util;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.List;

/**
 * User: RuaTre_IT
 * Date: 2/23/14
 * Time: 3:34 PM
 */
public class UtilImage {
    public Bitmap grayScaleImage(Bitmap src,String colorSource) {
        // constant factors
        String[] colors = colorSource.split(",");
        final double GS_RED = Double.parseDouble(colors[0]);
        final double GS_GREEN = Double.parseDouble(colors[1]);
        final double GS_BLUE = Double.parseDouble(colors[2]);

        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        // return final image
        return bmOut;
    }
}
