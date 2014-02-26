package com.free.tshirtdesigner.dialog;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import com.free.tshirtdesigner.R;
import com.free.tshirtdesigner.action.InputActionListener;

/**
 * User: AnhNT
 * Date: 1/21/14
 * Time: 9:29 PM
 */
public class InputDialog extends DialogFragment
{
    private String title;
    private String value;
    private String btPositiveText = "Save";
    private String btNegativeText = "Cancel";
    private InputActionListener inputAction;
    private Button btSubmit;

    public InputDialog(InputActionListener inputAction)
    {
        this.title = title;
        this.inputAction = inputAction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));

        View view = inflater.inflate(R.layout.input_dialog_layout, container, false);

        Button btCancel = (Button) view.findViewById(R.id.input_dialog_btButton1);
        btCancel.setText(btNegativeText);
        btCancel.setOnClickListener(onClickListener);

        btSubmit = (Button) view.findViewById(R.id.input_dialog_btButton2);
        btSubmit.setText(btPositiveText);
        btSubmit.setOnClickListener(onClickListener);

        EditText etValue = (EditText) view.findViewById(R.id.input_item_etContent);
        etValue.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s == null || s.length() == 0)
                {
                    setAlpha(btSubmit, 0.45f);
                    btSubmit.setEnabled(false);
                }
                else
                {
                    value = s.toString();
                    setAlpha(btSubmit, 1f);
                    btSubmit.setEnabled(true);
                }
            }
        });
        return view;
    }

    private void setAlpha(View view, float alpha)
    {
        AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
        alphaUp.setFillAfter(true);
        view.startAnimation(alphaUp);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (view.getId() == R.id.input_dialog_btButton2)
            {
                inputAction.onSubmit(value);
            }
            dismiss();
        }
    };

    public void setInputValue(String inputValue)
    {
        this.value = inputValue;
    }
}
