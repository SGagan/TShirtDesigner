package com.free.tshirtdesigner.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.free.tshirtdesigner.R;

/**
 * User: AnhNT
 * Date: 1/21/14
 * Time: 9:29 PM
 */
public class OrderSizeDialog extends DialogFragment
{
    private EditText etSizeSCount;
    private EditText etSizeMCount;
    private EditText etSizeLCount;
    private EditText etSizeXLCount;

    public OrderSizeDialog()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));

        View view = inflater.inflate(R.layout.order_size_layout, container, false);

        view.findViewById(R.id.order_size_dialog_btButton1).setOnClickListener(onClickListener);
        view.findViewById(R.id.order_size_dialog_btButton2).setOnClickListener(onClickListener);

        view.findViewById(R.id.size_s_decrement).setOnClickListener(onClickListener);
        view.findViewById(R.id.size_m_decrement).setOnClickListener(onClickListener);
        view.findViewById(R.id.size_l_decrement).setOnClickListener(onClickListener);
        view.findViewById(R.id.size_xl_decrement).setOnClickListener(onClickListener);

        view.findViewById(R.id.size_s_increment).setOnClickListener(onClickListener);
        view.findViewById(R.id.size_m_increment).setOnClickListener(onClickListener);
        view.findViewById(R.id.size_l_increment).setOnClickListener(onClickListener);
        view.findViewById(R.id.size_xl_increment).setOnClickListener(onClickListener);

        etSizeSCount = (EditText) view.findViewById(R.id.size_s_etCount);
        etSizeMCount = (EditText) view.findViewById(R.id.size_m_etCount);
        etSizeLCount = (EditText) view.findViewById(R.id.size_l_etCount);
        etSizeXLCount = (EditText) view.findViewById(R.id.size_xl_etCount);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.order_size_dialog_btButton2:
                    Toast.makeText(getActivity().getApplicationContext(), "SAVED!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
                case R.id.order_size_dialog_btButton1:
                    dismiss();
                    break;
                case R.id.size_s_decrement:
                    incrementValue(etSizeSCount);
                    break;
                case R.id.size_s_increment:
                    decrementValue(etSizeSCount);
                    break;
                case R.id.size_m_decrement:
                    incrementValue(etSizeMCount);
                    break;
                case R.id.size_m_increment:
                    decrementValue(etSizeMCount);
                    break;
                case R.id.size_l_decrement:
                    incrementValue(etSizeLCount);
                    break;
                case R.id.size_l_increment:
                    decrementValue(etSizeLCount);
                    break;
                case R.id.size_xl_decrement:
                    incrementValue(etSizeXLCount);
                    break;
                case R.id.size_xl_increment:
                    decrementValue(etSizeXLCount);
                    break;
            }
        }
    };

    public void incrementValue(EditText etSizeCount)
    {
        int value = Integer.parseInt(etSizeCount.getText().toString());
        etSizeCount.setText("" + value++);
    }

    public void decrementValue(EditText etSizeCount)
    {
        int value = Integer.parseInt(etSizeCount.getText().toString());
        if (value > 0)
        {
            etSizeCount.setText("" + value--);
        }
    }
}
