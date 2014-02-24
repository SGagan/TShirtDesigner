package com.free.tshirtdesigner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.free.tshirtdesigner.util.Validation;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;

import java.math.BigDecimal;

/**
 * User: Dell
 * Date: 2/24/14
 * Time: 9:07 PM
 */
public class CheckoutActivity extends Activity
{

    private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_NO_NETWORK;
    EditText edFirstName;
    EditText edLastName;
    EditText edAddress1;
    EditText edAddress2;
    EditText edPostcode;
    EditText edCountry;
    EditText edEmail;
    ImageButton btnCheckOut;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        edEmail = (EditText) findViewById(R.id.ed_email);
        edFirstName = (EditText) findViewById(R.id.ed_first_name);
        edLastName = (EditText) findViewById(R.id.ed_last_name);
        edAddress1 = (EditText) findViewById(R.id.ed_address_1);
        edAddress2 = (EditText) findViewById(R.id.ed_address_2);
        edPostcode = (EditText) findViewById(R.id.ed_postcode);
        edCountry = (EditText) findViewById(R.id.ed_country);
        btnCheckOut = (ImageButton) findViewById(R.id.ib_checkout);
        btnCheckOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                doCheckOut();
            }
        });


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "CLIENT_ID");
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, "RECEIVE_EMAIL");

        startService(intent);
    }

    private void doCheckOut()
    {
        String email = edEmail.getText().toString();
        String firstName = edFirstName.getText().toString();
        String lastName = edLastName.getText().toString();
        String address1 = edAddress1.getText().toString();
        String address2 = edAddress2.getText().toString();
        String postCode = edPostcode.getText().toString();
        String country = edCountry.getText().toString();
        if (email.isEmpty())
        {
            showAlertDialog("Please enter your email", "CHECK OUT ERROR");

        }
        else if (firstName.isEmpty())
        {
            showAlertDialog("Please enter your first name", "CHECK OUT ERROR");
        }
        else if (lastName.isEmpty())
        {
            showAlertDialog("Please enter your last name", "CHECK OUT ERROR");
        }
        else if (address1.isEmpty())
        {
            showAlertDialog("Please enter your address", "CHECK OUT ERROR");
        }
        else if (postCode.isEmpty())
        {
            showAlertDialog("Please enter your post code", "CHECK OUT ERROR");
        }
        else if (country.isEmpty())
        {
            showAlertDialog("Please enter your country", "CHECK OUT ERROR");
        }
        else
        {
            if (Validation.isValidEmail(email))
            {
                doBuyIt();

            }
            else
            {
                showAlertDialog("invalid email", "CHECK OUT ERROR");
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null)
            {
                try
                {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.

                }
                catch (JSONException e)
                {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED)
        {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID)
        {
            Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
        }
    }

    private void doBuyIt()
    {
        PayPalPayment buyIt = new PayPalPayment(new BigDecimal(10), "USD", "monthly");
        Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "CLIENT_ID");
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, "RECEIVE_EMAIL");
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "CLIENT_ID");
        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "RECEIVE_EMAIL");
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, buyIt);
        startActivityForResult(intent, 0);
    }


    @Override
    public void onDestroy()
    {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


    public void showAlertDialog(String message, String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}