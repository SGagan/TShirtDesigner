package com.free.tshirtdesigner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.free.tshirtdesigner.R;
import com.free.tshirtdesigner.model.LayerModel;
import com.free.tshirtdesigner.util.setting.ConstantValue;

/**
 * User: Admin
 * Date: 2/26/14
 * Time: 11:21 PM
 */
public class LayerArrayAdapter extends ArrayAdapter<LayerModel>
{
    Context mContext;
    int layoutResourceId;
    LayerModel data[] = null;

    public LayerArrayAdapter(Context mContext, int layoutResourceId, LayerModel[] data)
    {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.layer_item_layout, parent, false);
        }

        LayerModel layer = data[position];
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.layer_item_ivIcon);

        if (layer.getType() == ConstantValue.TEXT_ITEM_TYPE)
        {
            ivIcon.setImageResource(R.drawable.icon_text);
        }
        else if (layer.getType() == ConstantValue.IMAGE_ITEM_TYPE)
        {
            ivIcon.setImageResource(R.drawable.icon_image);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.layer_item_tvName);
        tvName.setText(layer.getName());

        return convertView;
    }
}
