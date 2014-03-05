package com.free.tshirtdesigner.util;

import android.graphics.Rect;
import android.view.View;
import android.widget.PopupWindow;

/**
 * User: Dell 3360
 * Date: 1/26/14
 */

public class LocationComponent
{
    public static Rect getLocation(View v)
    {
        int[] loc_int = new int[2];
        if (v == null)
        {
            return null;
        }
        try
        {
            v.getLocationOnScreen(loc_int);
        }
        catch (NullPointerException npe)
        {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }

    public static Rect getLocationSpec(View v, PopupWindow pwindo)
    {
        int[] loc_xy = new int[2];
        v.getLocationOnScreen(loc_xy);

        Rect location = new Rect();
        location.right = v.getWidth() - 10;
        location.top = v.getHeight() + loc_xy[1];
        location.left = location.right - pwindo.getWidth();

        return location;
    }
}
