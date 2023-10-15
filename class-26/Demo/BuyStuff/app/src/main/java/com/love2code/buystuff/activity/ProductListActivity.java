package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.love2code.buystuff.R;

public class ProductListActivity extends AppCompatActivity {

    public final String TAG = "productActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //step1: get a UI element By id
        Button orderButton = (Button) findViewById(R.id.productListOrderButton);
        Toast toast = Toast.makeText(this, "Hello from Toast", Toast.LENGTH_SHORT);
        // step 2: Add an event listener to that element
        orderButton.setOnClickListener(new View.OnClickListener() {

            //step 3: Attach a callback function with onClick() method
            @Override
            public void onClick(View view) {

                // step 4: Do stuff in the callback
                System.out.println("Hello, we submitted your order");

                toast.show();
                Log.i(TAG, "Hello, we're logging this time!!!!");

                Intent goToOrderFormIntent = new Intent(ProductListActivity.this, OrderFormActivity.class);
                startActivity(goToOrderFormIntent);

               // MainActivity.this.startActivity(goToOrderFormIntent);
            }
        });
    }


}