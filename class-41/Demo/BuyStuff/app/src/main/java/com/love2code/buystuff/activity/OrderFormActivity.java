package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.love2code.buystuff.R;

import java.util.Objects;

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

        TextView orderFormTextView = findViewById(R.id.orderFormTextView);

        orderFormTextView.setText(Objects.requireNonNullElse(productNameString, "no product name specified!!!!"));
    }
}