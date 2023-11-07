package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
import com.love2code.buystuff.R;

import java.util.Date;

public class AddProductActivity extends AppCompatActivity {

    public static final String TAG = "AddProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        Spinner productCategorySpinner = (Spinner) findViewById(R.id.addProductProductCategorySpinner);
        productCategorySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ProductCategoryEnum.values()));

        Button saveButton = (Button) findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(v -> {

            String name = ((EditText)findViewById(R.id.addProductProductNameEditTExt)).getText().toString();
            String description = ((EditText)findViewById(R.id.addProductProductDescriptionEditText)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

            Product newProduct = Product.builder()
                    .name(name)
                    .description(description)
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .productCategory((ProductCategoryEnum) productCategorySpinner.getSelectedItem()).build();

            Amplify.API.mutate(
                    ModelMutation.create(newProduct),
                    successResponse -> Log.i(TAG, "AddProductActivity.onCreate(): made a product successfully"),//success response
                    failureResponse -> Log.e(TAG, "AddProductActivity.onCreate(): failed with this response" + failureResponse)// in case we have a failed response
            );
        });
    }
}