package com.free.tshirtdesigner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import com.free.tshirtdesigner.R;
import com.free.tshirtdesigner.action.ColorChooserInterface;

/**
 * User: Admin
 * Date: 2/24/14
 * Time: 9:27 PM
 */
public class GridViewAdapter extends BaseAdapter
{
    private Context context;
    private ColorChooserInterface colorChooser;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.white, R.drawable.gainsboro,
            R.drawable.dark_gray, R.drawable.black,
            R.drawable.khaki, R.drawable.orange,
            R.drawable.dark_orange, R.drawable.red,
            R.drawable.deep_pink, R.drawable.dark_red,
            R.drawable.dark_magenta, R.drawable.dodger_blue,
            R.drawable.lime, R.drawable.green,
            R.drawable.medium_blue, R.drawable.midnight_blue
    };

    public String[] mThumbName = {
            "white", "gainsboro",
            "dark_gray", "black",
            "khaki", "orange",
            "dark_orange", "red",
            "deep_pink", "dark_red",
            "dark_magenta", "dodger_blue",
            "lime", "green",
            "medium_blue", "midnight_blue"
    };

    // Constructor
    public GridViewAdapter(Context context, ColorChooserInterface colorChooser)
    {
        this.context = context;
        this.colorChooser = colorChooser;
    }

    @Override
    public int getCount()
    {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position)
    {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Button btColor = new Button(context);
        btColor.setBackgroundResource(mThumbIds[position]);
        btColor.setLayoutParams(new GridView.LayoutParams(70, 40));
        btColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                colorChooser.itemClick(mThumbName[position]);
            }
        });
        return btColor;
    }
}
