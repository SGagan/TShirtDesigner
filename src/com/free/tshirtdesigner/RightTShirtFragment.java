package com.free.tshirtdesigner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: binhtv
 * Date: 2/25/14
 * Time: 6:33 PM
 */
public class RightTShirtFragment extends TShirtFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        tShirtDirection = R.drawable.tshirt_right_500;
        sideTag = MyActivity.RIGHT_TAG;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
