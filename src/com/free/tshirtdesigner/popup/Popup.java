package com.free.tshirtdesigner.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;
import com.free.tshirtdesigner.R;

/**
 * User: AnhNT
 * Date: 2/20/14
 * Time: 10:13 PM
 */
public class Popup
{
    private PopupWindow popupWindow;
    private PopupListener popupListener;
    private ScrollView rootView;
    private LinearLayout contentView;
    private LayoutInflater lInf;

    private Context context;

    public Popup(Context context, String[] arrayItem)
    {
        this.context = context;

        this.popupWindow = new PopupWindow(context);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setFocusable(true);
        this.popupWindow.setBackgroundDrawable(new BitmapDrawable());

        lInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInf.inflate(R.layout.popup_layout, null);
        rootView = (ScrollView) view.findViewById(R.id.popup_svRootView);
        contentView = (LinearLayout) view.findViewById(R.id.popup_llContentView);

        for (int i = 0; i < arrayItem.length; i++)
        {
            addItemTitleOnly(new PopupItem(i, arrayItem[i], null));
            addSperator();
        }
    }

    public void addItemTitleOnly(final PopupItem item)
    {
        TextView tvTitle = (TextView) lInf.inflate(R.layout.popup_item, null);
        tvTitle.setText(item.getItemTitle());
        tvTitle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupListener.onItemClick(item.getId(), item.getItemTitle());
                popupWindow.dismiss();
            }
        });

        contentView.addView(tvTitle);
        tvTitle.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        resizePopupWithTextOnly(tvTitle);
    }

    public void addSperator()
    {
        ImageView view = new ImageView(context);
        view.setBackgroundResource(R.drawable.line_dropdown_menu);
        contentView.addView(view);
    }

    public void showBelow(final View viewAbove, final int locationType)
    {
        viewAbove.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        contentView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        popupWindow.setContentView(rootView);
        if (popupWindow.getWidth() < getScreenWidth() / 2 + 30)
        {
            popupWindow.setWidth(getScreenWidth() / 2 + 30);
        }

        int height = contentView.getMeasuredHeight();
        if (height > getScreenHeight() - getLocationY(viewAbove) - viewAbove.getMeasuredHeight())
        {
            height = getScreenHeight() - getLocationY(viewAbove) - viewAbove.getMeasuredHeight() - 10;
        }
        if (height > getScreenHeight() / 2)
        {
            height = getScreenHeight() / 2;
        }
        popupWindow.setHeight(height);

        viewAbove.post(new Runnable()
        {
            public void run()
            {
                int POPUP_TOP = getLocationY(viewAbove) + viewAbove.getMeasuredHeight();
                switch (locationType)
                {
                    case PopupLocationType.LEFT:
                        popupWindow.showAtLocation(viewAbove, Gravity.LEFT | Gravity.TOP, 10, POPUP_TOP);
                        break;
                    case PopupLocationType.CENTER:
                        popupWindow.showAtLocation(viewAbove, Gravity.LEFT | Gravity.TOP, (getScreenWidth() - popupWindow.getWidth()) / 2, POPUP_TOP);
                        break;
                    case PopupLocationType.RIGHT:
                        popupWindow.showAtLocation(viewAbove, Gravity.LEFT | Gravity.TOP, getScreenWidth() - popupWindow.getWidth() - 10, POPUP_TOP);
                        break;
                }
            }
        });
    }

    public void showAbove(final View viewBelow, final int locationType)
    {
        viewBelow.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        contentView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        popupWindow.setContentView(rootView);
        if (popupWindow.getWidth() < getScreenWidth() / 2 + 30)
        {
            popupWindow.setWidth(getScreenWidth() / 2 + 30);
        }
        if (contentView.getMeasuredHeight() > getLocationY(viewBelow) - getStatusBarHeight())
        {
            popupWindow.setHeight(getLocationY(viewBelow) - getStatusBarHeight());
        }
        else
        {
            popupWindow.setHeight(contentView.getMeasuredHeight());
        }

        viewBelow.post(new Runnable()
        {
            public void run()
            {
                int POPUP_TOP = getLocationY(viewBelow) - popupWindow.getHeight();
                switch (locationType)
                {
                    case PopupLocationType.LEFT:
                        popupWindow.showAtLocation(viewBelow, Gravity.LEFT | Gravity.TOP, 10, POPUP_TOP);
                        break;
                    case PopupLocationType.CENTER:
                        popupWindow.showAtLocation(viewBelow, Gravity.LEFT | Gravity.TOP, (getScreenWidth() - popupWindow.getWidth()) / 2, POPUP_TOP);
                        break;
                    case PopupLocationType.RIGHT:
                        popupWindow.showAtLocation(viewBelow, Gravity.LEFT | Gravity.TOP, getScreenWidth() - popupWindow.getWidth() - 10, POPUP_TOP);
                        break;
                }
            }
        });
    }

    public void setOnItemClickListener(PopupListener popupListener)
    {
        this.popupListener = popupListener;
    }

    public void dismiss()
    {
        if (popupWindow != null && isShowing())
        {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing()
    {
        return popupWindow.isShowing();
    }

    public void resizePopupWithTextOnly(TextView tvTitle)
    {
        if (popupWindow.getWidth() < tvTitle.getMeasuredWidth())
        {
            popupWindow.setWidth(tvTitle.getMeasuredWidth());
        }
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public int getScreenWidth()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public int getScreenHeight()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public int getLocationY(View view)
    {
        int[] locationXY = new int[2];
        view.getLocationOnScreen(locationXY);
        return locationXY[1];
    }

    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
