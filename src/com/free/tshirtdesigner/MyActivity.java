package com.free.tshirtdesigner;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.widget.RelativeLayout.LayoutParams;

public class MyActivity extends Activity
{
    private ImageView ivShirt;
    private int home_x, home_y;
    private LayoutParams layoutParams;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ivShirt = (ImageView) findViewById(R.id.ivShirt);
        ivShirt.setImageResource(R.drawable.tshirt_front_500);
        ivShirt.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                return onTouchShirt(view, motionEvent);
            }
        });

        RadioGroup rgShirtViewType = (RadioGroup) findViewById(R.id.rgShirtViewType);
        rgShirtViewType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (group.getCheckedRadioButtonId())
                {
                    case R.id.rbLeftSide:
                        ivShirt.setImageResource(R.drawable.tshirt_left_500);
                        break;
                    case R.id.rbBackSide:
                        ivShirt.setImageResource(R.drawable.tshirt_back_500);
                        break;
                    case R.id.rbFrontSide:
                        ivShirt.setImageResource(R.drawable.tshirt_front_500);
                        break;
                    case R.id.rbRightSide:
                        ivShirt.setImageResource(R.drawable.tshirt_right_500);
                        break;
                }
            }
        });

        RadioButton rbFront = (RadioButton) findViewById(R.id.rbFrontSide);
        rbFront.setChecked(true);
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
                layoutParams.leftMargin = 30;
                layoutParams.topMargin = 30;
                view.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }
}
