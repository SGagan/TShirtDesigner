package com.free.tshirtdesigner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.*;
import com.free.tshirtdesigner.action.InputActionListener;
import com.free.tshirtdesigner.dialog.InputDialog;

import static android.widget.RelativeLayout.LayoutParams;

public class MyActivity extends FragmentActivity
{
    private static final int CHECKOUT_CODE = 100;
    private int home_x, home_y;
    private LayoutParams layoutParams;
    private Button btGetImageGallery;
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpViewById();
        setActionListener();

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
                    tShirtFragment.getLlRightMenu().setVisibility(View.VISIBLE);
                }
                else
                {
                    tShirtFragment.getLlRightMenu().setVisibility(View.GONE);
                }
            }
        });
    }

    private void setUpViewById()
    {
        shapeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.shape_layout, null);
        btGetImageGallery = (Button) findViewById(R.id.main_activity_btGetImageGallery);
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
                case R.id.main_activity_btGetImageGallery:
                    addLayoutImage();
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
                            tShirtFragment.getRlRootLayout().addView(
                                    new TouchExampleView(getApplicationContext(), result));
                        }
                    }).show(getFragmentManager(), "InputDialog");
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
        tShirtFragment.getRlRootLayout().addView(shapeLayout);
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

}
