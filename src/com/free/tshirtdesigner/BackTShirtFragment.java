package com.free.tshirtdesigner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: binhtv
 * Date: 2/26/14
 * Time: 12:30 AM
 */
public class BackTShirtFragment extends TShirtFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        tShirtDirection = R.drawable.tshirt_back_500;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
