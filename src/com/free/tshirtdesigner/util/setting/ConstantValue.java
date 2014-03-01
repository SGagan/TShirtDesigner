package com.free.tshirtdesigner.util.setting;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Admin
 * Date: 2/25/14
 * Time: 8:48 PM
 */
public class ConstantValue
{
    public static final int TEXT_ITEM_TYPE = 0;
    public static final int IMAGE_ITEM_TYPE = 1;

    public static final int PICK_FROM_FILE = 777;
    public static final int CAPTURE_PICTURE = 999;

    public static final Map<String, Integer> COLORS = new HashMap<String, Integer>()
    {
        {
            put("Black", Color.BLACK);
            put("Red", Color.RED);
            put("Green", Color.GREEN);
            put("Yellow", Color.YELLOW);
            put("Blue", Color.BLUE);
            put("White", Color.WHITE);
            put("Gray", Color.GRAY);
            put("LightGray", Color.LTGRAY);
            put("DarkGray", Color.DKGRAY);
            put("Cyan", Color.CYAN);
            put("Magenta", Color.MAGENTA);
        }
    };

    public static final Map<String, String> FONTS = new HashMap<String, String>()
    {
        {
            put("Caledo_bold", "caledo_bold.otf");
            put("Chunk", "chunk.ttf");
            put("Gtw", "gtw.ttf");
            put("Hobby_of_night", "hobby_of_night.ttf");
            put("Junction", "junction.otf");
            put("Knewave", "knewave.ttf");
            put("Linden_hill", "linden_hill.otf");
            put("Orbitron_bold", "orbitron_bold.ttf");
            put("Ostrich_rounded", "ostrich_rounded.ttf");
        }
    };
}
