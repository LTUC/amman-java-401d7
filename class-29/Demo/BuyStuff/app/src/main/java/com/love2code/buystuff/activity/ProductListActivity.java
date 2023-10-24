package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.love2code.buystuff.R;
import com.love2code.buystuff.activity.adapter.ProductListRecyclerVIewAdapter;
import com.love2code.buystuff.activity.database.BuyMoreStuffDatabase;
import com.love2code.buystuff.activity.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class ProductListActivity extends AppCompatActivity {

    public final String TAG = "productActivity";

    public static final String PRODUCT_NAME_EXTRA_TAG="productName";

    public static  final String DATABASE_NAME = "buy_more_stuff";
    SharedPreferences preferences;

    BuyMoreStuffDatabase buyMoreStuffDatabase;

    List<Product> products=null;

    ProductListRecyclerVIewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        buyMoreStuffDatabase = Room.databaseBuilder(
                getApplicationContext(),
                BuyMoreStuffDatabase.class,
                DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                                .build();
        products= buyMoreStuffDatabase.productDao().findAll();
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

        products.clear();
        products.addAll(buyMoreStuffDatabase.productDao().findAll());
        adapter.notifyDataSetChanged();
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
        //TODO: step 1-1: Add a RecyclerView to the Activity in the WSYIWYG editor
        //TODO: step 1-2: grab the RecyclerView
        RecyclerView productListRecyclerView = (RecyclerView) findViewById(R.id.productListRecyclerView);

        //TODO: step 1-3: set the layout manager of the RecyclerView to a LinerLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        productListRecyclerView.setLayoutManager(layoutManager);

        //TODO: step 2-2: Make some data items
//        List<Product> products = new ArrayList<>();

//        products.add(new Product("Pens"));
//        products.add(new Product("Pencils"));
//        products.add(new Product("Binders"));
//        products.add(new Product("Mice"));
//        products.add(new Product("Keyboard"));
//        products.add(new Product("Flash Drives"));
//        products.add(new Product("Monitors"));
//        products.add(new Product("Printer"));
//        products.add(new Product("Mouse"));
//        products.add(new Product("HeadSet"));
//        products.add(new Product("Tape"));
//        products.add(new Product("HeadSet Holder"));
//        products.add(new Product("HeadSet Holder"));
//        products.add(new Product("HeadSet Holder"));
//        products.add(new Product("HeadSet Holder"));

        //TODO: step 1-5: create and attach the RecyclerView.Adapter
        //TODO: step 2-3: Hand in data items
        //TODO: step 3-2: Hand in the Activity context
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