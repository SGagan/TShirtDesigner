package com.free.tshirtdesigner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.free.tshirtdesigner.action.ColorChooserInterface;
import com.free.tshirtdesigner.adapter.GridViewAdapter;
import com.free.tshirtdesigner.util.UtilImage;
import com.free.tshirtdesigner.util.UtilResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.widget.RelativeLayout.LayoutParams;

public class MyActivity extends Activity
{
    private static final int CHECKOUT_CODE = 100;
    private ImageView ivShirt;
    private int home_x, home_y;
    private LayoutParams layoutParams;
    private Button btGetImageGallery;
    private RelativeLayout shapeLayout;
    private ImageView ivImageShow;
    private ImageView ivResizeBottom;
    private ImageView ivResizeTop;
    private RelativeLayout rlRootLayout;
    private Button btnCheckout;
    private Button btnLeftMenu;

    private String colors = "white";
    private int tShirtDirection;
    private int _yDelta;
    private int _xDelta;

    private LinearLayout llRightMenu;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpViewById();
        btGetImageGallery.setOnClickListener(onClickListener);
        btnCheckout.setOnClickListener(onClickListener);

        ivShirt.setImageResource(R.drawable.tshirt_front_500);
        ivShirt.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                return onTouchShirt(view, motionEvent);
            }
        });

        // footer controller
        RadioGroup rgShirtViewType = (RadioGroup) findViewById(R.id.rgShirtViewType);
        rgShirtViewType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (group.getCheckedRadioButtonId())
                {
                    case R.id.rbLeftSide:
                        showDirectionTShirt(R.drawable.tshirt_left_500);
                        break;
                    case R.id.rbBackSide:
                        showDirectionTShirt(R.drawable.tshirt_back_500);
                        break;
                    case R.id.rbFrontSide:
                        showDirectionTShirt(R.drawable.tshirt_front_500);
                        break;
                    case R.id.rbRightSide:
                        showDirectionTShirt(R.drawable.tshirt_right_500);
                        break;
                }
            }
        });

        btnLeftMenu.setOnClickListener(onClickListener);

        RadioButton rbFront = (RadioButton) findViewById(R.id.rbFrontSide);
        rbFront.setChecked(true);

        findViewById(R.id.footer_control_btShowRightMenu).setOnClickListener(onClickListener);

        //right menu
        GridView gvColorChooser = (GridView) findViewById(R.id.right_menu_gvColorChooser);
        gvColorChooser.setAdapter(new GridViewAdapter(this, new ColorChooserInterface()
        {
            @Override
            public void itemClick(String colorNameSelected)
            {
                int colorSelectedId = UtilResource.getStringIdByName(getApplicationContext(), colorNameSelected);
                colors = getResources().getString(colorSelectedId);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), tShirtDirection);
                bitmap = UtilImage.grayScaleImage(bitmap, colors);
                ivShirt.setImageBitmap(bitmap);
            }
        }));
    }

    private void setUpViewById()
    {
        shapeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.shape_layout, null);
        rlRootLayout = (RelativeLayout) findViewById(R.id.main_activity_rlShowTShirt);
        rlRootLayout.setDrawingCacheEnabled(true);
        ivShirt = (ImageView) findViewById(R.id.ivShirt);
        btGetImageGallery = (Button) findViewById(R.id.main_activity_btGetImageGallery);
        ivImageShow = (ImageView) shapeLayout.findViewById(R.id.main_activity_ivImage);
        ivImageShow.setTag("ImageShow");
        ivResizeTop = (ImageView) shapeLayout.findViewById(R.id.main_activity_ivResizeTop);
        ivResizeTop.setTag("ResizeTop");
        ivResizeBottom = (ImageView) shapeLayout.findViewById(R.id.main_activity_ivResizeBottom);
        ivResizeBottom.setTag("ResizeBottom");
        btnCheckout = (Button) findViewById(R.id.header_btCheckout);
        btnLeftMenu = (Button) findViewById(R.id.btn_left_menu);
        llRightMenu = (LinearLayout) findViewById(R.id.right_menu_llRoot);
    }

    private void showDirectionTShirt(int tShirt_direction)
    {
        tShirtDirection = tShirt_direction;
        if (!colors.equals("white"))
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), tShirt_direction);
            bitmap = UtilImage.grayScaleImage(bitmap, colors);
            ivShirt.setImageBitmap(bitmap);
        }
        else
        {
            ivShirt.setImageResource(tShirt_direction);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.main_activity_btGetImageGallery:
                    addLayoutImage();
                    break;
                case R.id.header_btCheckout:
                    Intent intent = new Intent(MyActivity.this, CheckoutActivity.class);
                    startActivityForResult(intent, CHECKOUT_CODE);
                    break;
                case R.id.btn_left_menu:
                    saveTShirt();
                    break;
                case R.id.footer_control_btShowRightMenu:
                    if (llRightMenu.getVisibility() == View.GONE)
                    {
                        llRightMenu.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        llRightMenu.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    private void addLayoutImage()
    {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        shapeLayout.setLayoutParams(layoutParams);
        ivImageShow.setImageResource(R.drawable.ic_launcher);
        ivImageShow.setOnTouchListener(onTouchListenerImage);
        ivResizeBottom.setOnTouchListener(onTouchListenerImage);
        ivResizeTop.setOnTouchListener(onTouchListenerImage);
        rlRootLayout.addView(shapeLayout);
    }

    View.OnTouchListener onTouchListenerImage = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            final int X = (int) motionEvent.getRawX();
            final int Y = (int) motionEvent.getRawY();
            if (view.getTag().equals("ImageShow"))
            {
                actionImageShow(motionEvent, X, Y);
            }
            return true;
        }
    };

    private void actionImageShow(MotionEvent motionEvent, int x, int y)
    {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) shapeLayout.getLayoutParams();
                _xDelta = x - lParams.leftMargin;
                _yDelta = y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) shapeLayout.getLayoutParams();
                layoutParams.leftMargin = x - _xDelta;
                layoutParams.topMargin = y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                shapeLayout.setLayoutParams(layoutParams);
                break;
        }
        shapeLayout.invalidate();
    }

    private boolean onTouchShirt(View view, MotionEvent motionEvent)
    {
        layoutParams = (LayoutParams) view.getLayoutParams();
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                home_x = (int) motionEvent.getRawX();
                home_y = (int) motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x_moved = (int) motionEvent.getRawX();
                int y_moved = (int) motionEvent.getRawY();

                layoutParams.leftMargin = (x_moved >= home_x) ? x_moved - home_x : home_x - x_moved;
                layoutParams.topMargin = (y_moved >= home_y) ? y_moved - home_y : home_y - y_moved;

                view.setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                layoutParams.leftMargin = 0;
                layoutParams.topMargin = 0;
                view.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }

    public void saveTShirt()
    {
        Bitmap bitmap = rlRootLayout.getDrawingCache();
        File file, f = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            file = new File(Environment.getExternalStorageDirectory(), "Tshirt_cache");
            if (!file.exists())
            {
                file.mkdirs();
            }
            f = new File(file.getAbsolutePath() + File.separator + "front" + ".png");
        }
        try
        {
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, outputStream);
            outputStream.close();
            Toast.makeText(this, "SAVE T SHIRT DONE", Toast.LENGTH_LONG).show();
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

}
