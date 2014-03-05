package com.free.tshirtdesigner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.free.tshirtdesigner.action.ColorChooserInterface;
import com.free.tshirtdesigner.action.InputActionListener;
import com.free.tshirtdesigner.action.TextChangeListener;
import com.free.tshirtdesigner.adapter.GridViewAdapter;
import com.free.tshirtdesigner.dialog.InputDialog;
import com.free.tshirtdesigner.util.UtilImage;
import com.free.tshirtdesigner.util.UtilResource;
import com.free.tshirtdesigner.util.setting.ConstantValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * User: binhtv
 * Date: 2/25/14
 * Time: 11:07 PM
 */
public class TShirtFragment extends Fragment
{
    RelativeLayout rlRootLayout;
    ImageView ivShirt;
    LinearLayout llRightMenu;
    LinearLayout llLeftMenu;
    GridView gvColorChooser;
    public int tShirtDirection;
    int sideTag;
    private ListView lvListLayer;

    private LinearLayout llChangeColor;
    private LinearLayout llChangeText;
    private LinearLayout llChangeFont;
    private LinearLayout llDeleteLayer;

    private TextChangeListener textChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rlRootLayout = (RelativeLayout) inflater.inflate(R.layout.shirt_layout, container, false);
        rlRootLayout.setDrawingCacheEnabled(true);
        ivShirt = (ImageView) rlRootLayout.findViewById(R.id.ivShirt);
        ivShirt.setImageResource(R.drawable.tshirt_front_500);
        ivShirt.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                return false;
            }
        });

        //right menu
        llRightMenu = (LinearLayout) rlRootLayout.findViewById(R.id.right_menu_llRoot);
        llLeftMenu = (LinearLayout) rlRootLayout.findViewById(R.id.left_menu_llRootLeftMenu);
        gvColorChooser = (GridView) rlRootLayout.findViewById(R.id.right_menu_gvColorChooser);
        lvListLayer = (ListView) rlRootLayout.findViewById(R.id.menu_right_lvListLayer);

        gvColorChooser.setAdapter(new GridViewAdapter(getActivity(), new ColorChooserInterface()
        {
            @Override
            public void itemClick(String colorNameSelected)
            {
                int colorSelectedId = UtilResource.getStringIdByName(getActivity(), colorNameSelected);
                MyActivity.colors = getResources().getString(colorSelectedId);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), tShirtDirection);
                bitmap = UtilImage.grayScaleImage(bitmap, MyActivity.colors);
                ivShirt.setImageBitmap(bitmap);
            }
        }));

        showDirectionTShirt();
        // get old view
        List<View> viewList = getMainActivity().getView(String.valueOf(sideTag));
        if (viewList != null && viewList.size() > 0)
        {
            for (View zoomView : viewList)
            {
                rlRootLayout.addView(zoomView);
            }
            getMainActivity().setCurrentZoomView(viewList);
        }

        // left menu
        llChangeColor = (LinearLayout) rlRootLayout.findViewById(R.id.left_menu_btChangeColor);
        llChangeText = (LinearLayout) rlRootLayout.findViewById(R.id.left_menu_btChangeText);
        llChangeFont = (LinearLayout) rlRootLayout.findViewById(R.id.left_menu_btChangeFont);
        llDeleteLayer = (LinearLayout) rlRootLayout.findViewById(R.id.left_menu_btDeleteLayer);

        llChangeColor.setOnClickListener(onClickListener);
        llChangeText.setOnClickListener(onClickListener);
        llChangeFont.setOnClickListener(onClickListener);
        llDeleteLayer.setOnClickListener(onClickListener);

        return rlRootLayout;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            llLeftMenu.setVisibility(View.GONE);
            switch (view.getId())
            {
                case R.id.left_menu_btChangeColor:

                    textChangeListener.changeColor();
                    break;
                case R.id.left_menu_btChangeText:
                    new InputDialog(new InputActionListener()
                    {
                        @Override
                        public void onSubmit(String result)
                        {
                            textChangeListener.changeText(result);
                        }
                    }).show(getActivity().getSupportFragmentManager().beginTransaction(), "InputDialog");
                    break;
                case R.id.left_menu_btChangeFont:
                    textChangeListener.changeFont();
                    break;
                case R.id.left_menu_btDeleteLayer:
                    textChangeListener.delete();
                    break;
            }
        }
    };

    public RelativeLayout getRlRootLayout()
    {
        return rlRootLayout;
    }

    public GridView getGvColorChooser()
    {
        return gvColorChooser;
    }

    public ImageView getIvShirt()
    {
        return ivShirt;
    }

    public LinearLayout getLlRightMenu()
    {
        return llRightMenu;
    }

    public LinearLayout getLlLeftMenu()
    {
        return llLeftMenu;
    }

    public void setTextChangeListener(TextChangeListener textChangeListener)
    {
        this.textChangeListener = textChangeListener;
    }

    private void showDirectionTShirt()
    {
        if (!MyActivity.colors.equals("white"))
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), tShirtDirection);
            bitmap = UtilImage.grayScaleImage(bitmap, MyActivity.colors);
            ivShirt.setImageBitmap(bitmap);
        }
        else
        {
            ivShirt.setImageResource(tShirtDirection);
        }
    }

    public void saveTShirt(Context context, int side)
    {
        Log.e("TAG", "saveTShirt" + side);
        Bitmap bitmap = rlRootLayout.getDrawingCache();
        File file, f = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            file = new File(Environment.getExternalStorageDirectory(), "Tshirt_cache");
            if (!file.exists())
            {
                file.mkdirs();
            }
            f = new File(file.getAbsolutePath() + File.separator + side + ".png");
        }
        try
        {
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, outputStream);
            outputStream.close();
            Toast.makeText(context, "SAVE T SHIRT DONE", Toast.LENGTH_LONG).show();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if (rlRootLayout != null)
        {
            rlRootLayout.removeAllViewsInLayout();
        }
        getMainActivity().saveState(String.valueOf(sideTag));
    }

    public MyActivity getMainActivity()
    {
        return (MyActivity) getActivity();
    }

    public ListView getLvListLayer()
    {
        return lvListLayer;
    }

    public void showMenuLeft(int itemType)
    {
        switch (itemType)
        {
            case ConstantValue.TEXT_ITEM_TYPE:
                llChangeColor.setVisibility(View.VISIBLE);
                llChangeFont.setVisibility(View.VISIBLE);
                llChangeText.setVisibility(View.VISIBLE);
                break;
            case ConstantValue.IMAGE_ITEM_TYPE:
                llChangeColor.setVisibility(View.GONE);
                llChangeFont.setVisibility(View.GONE);
                llChangeText.setVisibility(View.GONE);
                break;
        }
    }
}
