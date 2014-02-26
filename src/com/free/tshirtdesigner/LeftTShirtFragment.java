package com.free.tshirtdesigner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.free.tshirtdesigner.util.UtilImage;

/**
 * User: binhtv
 * Date: 2/25/14
 * Time: 5:21 PM
 */
public class LeftTShirtFragment extends TShirtFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        tShirtDirection = R.drawable.tshirt_left_500;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
