package com.free.tshirtdesigner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: binhtv
 * Date: 2/25/14
 * Time: 11:21 PM
 */
public class FrontTShirtFragment extends TShirtFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        tShirtDirection = R.drawable.tshirt_front_500;
        sideTag = MyActivity.FRONT_TAG;
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
