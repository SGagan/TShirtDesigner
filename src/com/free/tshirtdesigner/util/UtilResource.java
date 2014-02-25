package com.free.tshirtdesigner.util;

import android.content.Context;

/**
 * User: Admin
 * Date: 2/25/14
 * Time: 12:08 AM
 */
public class UtilResource
{
    public static int getStringIdByName(Context context, String stringId)
    {
        return context.getResources().getIdentifier(stringId, "string", context.getPackageName());
    }

    public static int getDrawableIdByName(Context context, String drawableId)
    {
        return context.getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
    }

    public static int getComponentIdByName(Context context, String componentId)
    {
        return context.getResources().getIdentifier(componentId, "id", context.getPackageName());
    }

    public static String getStringResourceValue(Context context, int resourceId)
    {
        return context.getResources().getString(resourceId);
    }
}
