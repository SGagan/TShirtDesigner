package com.free.tshirtdesigner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
