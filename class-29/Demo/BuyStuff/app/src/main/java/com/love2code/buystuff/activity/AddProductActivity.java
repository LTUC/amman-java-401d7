package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.love2code.buystuff.R;
import com.love2code.buystuff.activity.database.BuyMoreStuffDatabase;
import com.love2code.buystuff.activity.model.Product;
import com.love2code.buystuff.activity.model.ProductCategoryEnum;

import java.util.Date;

public class AddProductActivity extends AppCompatActivity {

    BuyMoreStuffDatabase buyMoreStuffDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        buyMoreStuffDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        BuyMoreStuffDatabase.class,
                        "buy_more_stuff")
                .allowMainThreadQueries()
                .build();
        Spinner productCategorySpinner = (Spinner) findViewById(R.id.addProductProductCategorySpinner);
        productCategorySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ProductCategoryEnum.values()));

        Button saveButton = (Button) findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(v -> {

            Product newProduct = new Product(
                    ((EditText) findViewById(R.id.addProductProductNameEditTExt)).getText().toString(),
                    ((EditText) findViewById(R.id.addProductProductDescriptionEditText)).getText().toString(),
                    new Date(),
                    ProductCategoryEnum.fromString(productCategorySpinner.getSelectedItem().toString())
            );

            buyMoreStuffDatabase.productDao().insertAProduct(newProduct);
        });
    }
}