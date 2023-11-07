package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Product;
import com.love2code.buystuff.R;
import com.love2code.buystuff.activity.adapter.ProductListRecyclerVIewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    public final String TAG = "productActivity";

    public static final String PRODUCT_NAME_EXTRA_TAG="productName";
    SharedPreferences preferences;

    List<Product> products=null;

    ProductListRecyclerVIewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        products = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Product.class),
                success ->
                {
                    Log.i(TAG, "Read Product successfully");
                    //products = new ArrayList<>();
                    products.clear();
                    for (Product databaseProduct : success.getData()){
                        products.add(databaseProduct);
                    }
                    //adapter.notifyDataSetChanged();
                    runOnUiThread(() ->{
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i(TAG, "Did not read products successfully")
        );
        setUpOrderButton();
        setUpSettingsImageView();
        setUpProductListRecyclerView();
        setUpAddProductButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String userNickname = preferences.getString(UserProfileActivity.USER_NICKNAME_TAG, "No nickname");

        ((TextView)findViewById(R.id.userNicknameTextView)).setText(getString(R.string.nickname_with_input, userNickname));

    }

    private void setUpOrderButton(){

        Button orderButton = (Button) findViewById(R.id.productListOrderButton);

        orderButton.setOnClickListener(V -> {

            String productName = ((EditText)findViewById(R.id.productNameEditText)).getText().toString();

            Intent goToOrderFromIntent = new Intent(ProductListActivity.this, OrderFormActivity.class);
            goToOrderFromIntent.putExtra(PRODUCT_NAME_EXTRA_TAG, productName);
            startActivity(goToOrderFromIntent);
        });
    }

    private void setUpSettingsImageView(){

        ImageView userSettingsImageView = (ImageView) findViewById(R.id.userProfileImageView2);

        userSettingsImageView.setOnClickListener(V -> {
            Intent goToUserSettingsIntent = new Intent(ProductListActivity.this, UserProfileActivity.class);
            startActivity(goToUserSettingsIntent);
        });
    }

    private void setUpProductListRecyclerView(){
        RecyclerView productListRecyclerView = (RecyclerView) findViewById(R.id.productListRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        productListRecyclerView.setLayoutManager(layoutManager);

        adapter = new ProductListRecyclerVIewAdapter(products, this);
        productListRecyclerView.setAdapter(adapter);


    }

    private void setUpAddProductButton(){
        Button addProductButton = findViewById(R.id.AddProductButton);
        addProductButton.setOnClickListener(v -> {
            Intent goToAddProductActivity = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(goToAddProductActivity);
        });
    }
}