package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.love2code.buystuff.R;

public class OrderFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        Intent callingIntent = getIntent();
        String productNameString = null;

        if(callingIntent != null){
            productNameString = callingIntent.getStringExtra(ProductListActivity.PRODUCT_NAME_EXTRA_TAG);
        }

        TextView orderFormTextView = (TextView) findViewById(R.id.orderFormTextView);

        if (productNameString != null){
            orderFormTextView.setText(productNameString);
        }else {
            orderFormTextView.setText("no product name specified!!!!");
        }
    }
}