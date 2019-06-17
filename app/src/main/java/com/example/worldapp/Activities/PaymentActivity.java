package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

public class PaymentActivity extends BaseAppCompat {

    private EditText mCardNumber, mCardCvv, mExpirationDate;
    private TextView mPrice, mFee, mTotalPrice;
    private Button mFinishBtn;
    private String[] mExtraValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        super.SetToolbarTitle("Payment details");
        InitializeViews();

        Intent myIntent = getIntent();
        mExtraValues = myIntent.getStringArrayExtra("paymentDetails");
        setTextValues();
    }

    private void InitializeViews()
    {
        mCardCvv = findViewById(R.id.et_payment_cvv);
        mCardNumber = findViewById(R.id.et_payment_card);
        mExpirationDate = findViewById(R.id.et_payment_date);
        mPrice = findViewById(R.id.tv_payment_price);
        mFee = findViewById(R.id.tv_payment_fee);
        mTotalPrice = findViewById(R.id.tv_payment_total_price);
        mFinishBtn = findViewById(R.id.btn_payment_done);
    }

    private void setTextValues()
    {
        mPrice.setText("Price: "+mExtraValues[0]+" EUR");
        mFee.setText("Service fee: "+mExtraValues[1]+" EUR");
        mTotalPrice.setText("Total: "+mExtraValues[2]+" EUR");

        mCardNumber.setText("4624459234295823");
        mCardCvv.setText("752");
        mExpirationDate.setText("11/24");
    }

    private boolean checkFields()
    {
        if (mCardNumber.getText().length() != 16)
        {
            Toast.makeText(this, "Please insert a valid card number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mCardCvv.getText().length() != 3)
        {
            Toast.makeText(this, "Please insert a valid CVV!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mExpirationDate.getText().length() < 4)
        {
            Toast.makeText(this, "Please insert a valid date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void FinishPurchase(View view) {
        if (checkFields())
        {
            Toast.makeText(this, "Purchase done!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,ActivityHome.class));
        }
    }
}
