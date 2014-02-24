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
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel) * GS_RED);
                G = (int)(Color.green(pixel) * GS_GREEN);
                B = (int)(Color.blue(pixel) * GS_BLUE);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }
}
