package com.free.tshirtdesigner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.free.tshirtdesigner.action.InputActionListener;
import com.free.tshirtdesigner.adapter.LayerArrayAdapter;
import com.free.tshirtdesigner.dialog.InputDialog;
import com.free.tshirtdesigner.model.LayerModel;
import com.free.tshirtdesigner.util.UtilImage;
import com.free.tshirtdesigner.util.setting.ConstantValue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.RelativeLayout.LayoutParams;

public class MyActivity extends FragmentActivity
{
    private static final int CHECKOUT_CODE = 100;
    private int home_x, home_y;
    private LayoutParams layoutParams;
    private Button btGetImageGallery;
    private Button btTakePhoto;
    private RelativeLayout shapeLayout;
    private ImageView ivImageShow;
    private ImageView ivResizeBottom;
    private ImageView ivResizeTop;
    //    private RelativeLayout rlRootLayout;
    private Button btnCheckout;
    private Button btnLeftMenu;
    private Button btAddText;

    private int tShirtDirection;
    private int _yDelta;
    private int _xDelta;
    public static final String FRONT_TAG = "FRONT";
    public static final String LEFT_TAG = "LEFT";
    public static final String RIGHT_TAG = "RIGHT";
    public static final String BACK_TAG = "BACK";


    TShirtFragment tShirtFragment;
    private int countLayer = -1;
    private ListView lvListLayer;
    private int currentLayer = -1;
    private List<LayerModel> layerModels = new ArrayList<LayerModel>();
    Map<String, List<View>> zoomViewsMap = new HashMap<String, List<View>>();
    List<View> currentZoomView = new ArrayList<View>();
    public String currentSide;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpViewById();
        setActionListener();

        currentSide = FRONT_TAG;
        createOrUpdateFragment(FRONT_TAG);
        // footer controller
        RadioGroup rgShirtViewType = (RadioGroup) findViewById(R.id.rgShirtViewType);
        rgShirtViewType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (group.getCheckedRadioButtonId())
                {
                    case R.id.rbLeftSide:
                        createOrUpdateFragment(LEFT_TAG);
                        break;
                    case R.id.rbBackSide:
                        createOrUpdateFragment(BACK_TAG);
                        break;
                    case R.id.rbFrontSide:
                        createOrUpdateFragment(FRONT_TAG);
                        break;
                    case R.id.rbRightSide:
                        createOrUpdateFragment(RIGHT_TAG);
                        break;
                }
            }
        });

        RadioButton rbFront = (RadioButton) findViewById(R.id.rbFrontSide);
        rbFront.setChecked(true);

        findViewById(R.id.footer_control_btShowRightMenu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (tShirtFragment.getLlRightMenu().getVisibility() == View.GONE)
                {
                    LayerModel[] layers = new LayerModel[layerModels.size()];
                    layers = layerModels.toArray(layers);
                    LayerArrayAdapter adapter = new LayerArrayAdapter(MyActivity.this, R.id.menu_right_lvListLayer, layers);
                    lvListLayer = tShirtFragment.getLvListLayer();
                    lvListLayer.setAdapter(adapter);
                    lvListLayer.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                        {
                            Toast.makeText(getApplicationContext(), "item " + i, Toast.LENGTH_SHORT).show();
                            layerModels.get(i).getViewZoomer().setEnabled(true);
                            currentLayer = i;
                            tShirtFragment.getLlRightMenu().setVisibility(View.GONE);
                        }
                    });
                    for (LayerModel layer : layerModels)
                    {
                        layer.getViewZoomer().setEnabled(false);
                    }
                    tShirtFragment.getRlRootLayout().bringToFront();
                    tShirtFragment.getLlRightMenu().setVisibility(View.VISIBLE);
                }
                else
                {
                    tShirtFragment.getLlRightMenu().setVisibility(View.GONE);
                    if (currentLayer != -1)
                    {
                        layerModels.get(currentLayer).getViewZoomer().setEnabled(true);
                    }
                }
            }
        });
    }

    private void setUpViewById()
    {
        shapeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.shape_layout, null);
        btGetImageGallery = (Button) findViewById(R.id.footer_control_btAddImage);
        btTakePhoto = (Button) findViewById(R.id.footer_control_btTakePhoto);
        ivImageShow = (ImageView) shapeLayout.findViewById(R.id.main_activity_ivImage);
        ivImageShow.setTag("ImageShow");
        ivResizeTop = (ImageView) shapeLayout.findViewById(R.id.main_activity_ivResizeTop);
        ivResizeTop.setTag("ResizeTop");
        ivResizeBottom = (ImageView) shapeLayout.findViewById(R.id.main_activity_ivResizeBottom);
        ivResizeBottom.setTag("ResizeBottom");
        btnCheckout = (Button) findViewById(R.id.header_btCheckout);
        btnLeftMenu = (Button) findViewById(R.id.btn_left_menu);
        btAddText = (Button) findViewById(R.id.footer_control_btAddText);
    }

    private void setActionListener()
    {
        btGetImageGallery.setOnClickListener(onClickListener);
        btTakePhoto.setOnClickListener(onClickListener);
        btnCheckout.setOnClickListener(onClickListener);
        btnLeftMenu.setOnClickListener(onClickListener);
        btAddText.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.footer_control_btAddImage:
                    Intent intentGallery = new Intent();
                    intentGallery.setType("image/*");
                    intentGallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intentGallery, "Complete action using"), ConstantValue.PICK_FROM_FILE);
                    break;
                case R.id.footer_control_btTakePhoto:
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, ConstantValue.CAPTURE_PICTURE);
                    break;
                case R.id.header_btCheckout:
                    Intent intent = new Intent(MyActivity.this, CheckoutActivity.class);
                    startActivityForResult(intent, CHECKOUT_CODE);
                    break;
                case R.id.btn_left_menu:
                    break;
                case R.id.footer_control_btAddText:
                    new InputDialog(new InputActionListener()
                    {
                        @Override
                        public void onSubmit(String result)
                        {
                            ViewZoomer viewZoomer = new ViewZoomer(getApplicationContext(), result);
                            currentZoomView.add(viewZoomer);
                            tShirtFragment.getRlRootLayout().addView(viewZoomer);
                            layerModels.add(new LayerModel(countLayer++, ConstantValue.TEXT_ITEM_TYPE, result, viewZoomer));
                            currentLayer = countLayer;
                        }
                    }).show(getFragmentManager(), "InputDialog");
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        switch (requestCode)
        {
            case ConstantValue.PICK_FROM_FILE:
                Uri mImageCaptureUri = data.getData();
                mImageCaptureUri = Uri.fromFile(new File(UtilImage.getRealPathFromURI(this, mImageCaptureUri)));
                Bitmap icon = BitmapFactory.decodeFile(mImageCaptureUri.getPath());
                ViewZoomer viewZoomer = new ViewZoomer(getApplicationContext(), scaleImage(icon, 70, 70));
                tShirtFragment.getRlRootLayout().addView(viewZoomer);
                currentZoomView.add(viewZoomer);
                String name = getResources().getResourceEntryName(R.drawable.bt_red_popup_small);
                layerModels.add(new LayerModel(countLayer++, ConstantValue.IMAGE_ITEM_TYPE, name, viewZoomer));
                currentLayer = countLayer;
                break;
            case ConstantValue.CAPTURE_PICTURE:
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                addViewZoomAndModel(photo);
        }
    }

    private void addLayoutImage()
    {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        shapeLayout.setLayoutParams(layoutParams);
        ivImageShow.setImageResource(R.drawable.ic_launcher);
        ivImageShow.setOnTouchListener(onTouchListenerImage);
        ivResizeBottom.setOnTouchListener(onTouchListenerImage);
        ivResizeTop.setOnTouchListener(onTouchListenerImage);
        tShirtFragment.getRlRootLayout().addView(shapeLayout);
    }
//
//    private void actionImageShow(MotionEvent motionEvent, int x, int y)
//    {
//        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
//        {
//            case MotionEvent.ACTION_DOWN:
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) shapeLayout.getLayoutParams();
//                _xDelta = x - lParams.leftMargin;
//                _yDelta = y - lParams.topMargin;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) shapeLayout.getLayoutParams();
//                layoutParams.leftMargin = x - _xDelta;
//                layoutParams.topMargin = y - _yDelta;
//                layoutParams.rightMargin = -250;
//                layoutParams.bottomMargin = -250;
//                shapeLayout.setLayoutParams(layoutParams);
//                break;
//        }
//        shapeLayout.invalidate();
//    }

    public void createOrUpdateFragment(String fragmentTag)
    {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        tShirtFragment = (TShirtFragment) getSupportFragmentManager().findFragmentByTag(fragmentTag);

        if (tShirtFragment == null)
        {
            createNewFragment(fragmentTag);
            tShirtFragment.setRetainInstance(true);
            ft.replace(R.id.embed_shirt, tShirtFragment, fragmentTag).addToBackStack(null);
            ft.commit();
        }
        else
        {
            ft.replace(R.id.embed_shirt, tShirtFragment).addToBackStack(null);
            ft.commit();
        }
    }

    private void createNewFragment(String fragmentTag)
    {
        if (fragmentTag.equals(LEFT_TAG))
        {
            tShirtFragment = new LeftTShirtFragment();
        }
        else if (fragmentTag.equals(FRONT_TAG))
        {
            tShirtFragment = new FrontTShirtFragment();
        }
        else if (fragmentTag.equals(BACK_TAG))
        {
            tShirtFragment = new BackTShirtFragment();
        }
        else if (fragmentTag.equals(RIGHT_TAG))
        {
            tShirtFragment = new RightTShirtFragment();
        }
    }


    public void saveState(String sideTag)
    {
        zoomViewsMap.put(sideTag, currentZoomView);
        currentZoomView = new ArrayList<View>();
    }

    public List<View> getView(String sideTag)
    {
        return zoomViewsMap.get(sideTag);
    }

    public void setCurrentZoomView(List<View> views)
    {
        currentZoomView = views;

    }

    private Bitmap scaleImage(Bitmap bitmap, int maxWidth, int maxHeight)
    {
//        int widthBitmap = bitmap.getWidth();
//        int heightBitmap = bitmap.getHeight();
//        if (widthBitmap > heightBitmap)
//        {
//            return Bitmap.createScaledBitmap(bitmap, 200, heightBitmap * 200 / widthBitmap, true);
//        }
//        else
//        {
//            return Bitmap.createScaledBitmap(bitmap, widthBitmap*200/heightBitmap, 200, true);
//        }
        double ratioX = (double) maxWidth / bitmap.getWidth();
        double ratioY = (double) maxHeight / bitmap.getHeight();
        double ratio = Math.min(ratioX, ratioY);

        int newWidth = (int) (bitmap.getWidth() * ratio);
        int newHeight = (int) (bitmap.getHeight() * ratio);

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

    }

}
