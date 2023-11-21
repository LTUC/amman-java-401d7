package com.love2code.buystuff.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Product;
import com.love2code.buystuff.R;
import com.love2code.buystuff.activity.adapter.ProductListRecyclerVIewAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    public static final String PRODUCT_NAME_EXTRA_TAG = "productName";
    public final String TAG = "productActivity";

    public static final String PRODUCT_ID_TAG = "Product ID Tag";
    SharedPreferences preferences;

    List<Product> products = null;

    ProductListRecyclerVIewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        init();

        // first step, sign up
        /*Amplify.Auth.signUp("tselawe706@gmail.com",
                "p@ssw0rd523",
                AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), "tselawe706@gmail.com")
                        .userAttribute(AuthUserAttributeKey.nickname(), "T")
                        .build(),
                good ->
                {
                    Log.i(TAG, "Signup succeeded: "+ good.toString());
                },
                bad ->
                {
                    Log.i(TAG, "Signup failed with username: "+ "tselawe706@gmail.com"+ " with this message: "+ bad.toString());
                }
        );*/

        // next step, we need to verify the user
        /*Amplify.Auth.confirmSignUp("tselawe706@gmail.com",
                "939205",
                success ->
                {
                    Log.i(TAG,"verification succeeded: "+ success.toString());

                },
                failure ->
                {
                    Log.i(TAG,"verification failed: "+ failure.toString());
                }
        );*/

        // next, we want to log in to our system
        /*Amplify.Auth.signIn("tselawe706@gmail.com",
                "p@ssw0rd523",
                success ->
                {
                    Log.i(TAG, "Login succeeded: "+success.toString());
                },
                failure ->
                {
                    Log.i(TAG, "Login failed: "+failure.toString());
                }
        );*/

        // next we want to log out from out system
        /*Amplify.Auth.signOut(
                () ->
                {
                    Log.i(TAG,"Logout succeeded");
                },
                failure ->
                {
                    Log.i(TAG, "Logout failed");
                }
        );*/
        //setUpOrderButton();

        String emptyFilename= "emptyTestFileName";
        File emptyFile = new File(getApplicationContext().getFilesDir(), emptyFilename);

        try {
            BufferedWriter emptyFileBufferedWriter= new BufferedWriter(new FileWriter(emptyFile));

            emptyFileBufferedWriter.append("Some text here from Thaer\nAnother libe from Thaer");

            emptyFileBufferedWriter.close();
        }catch (IOException ioe){
            Log.i(TAG, "could not write locally with filename: "+ emptyFilename);
        }

        String emptyFileS3Key = "someFileOnS3.txt";
        Amplify.Storage.uploadFile(
                emptyFileS3Key,
                emptyFile,
                success ->
                {
                    Log.i(TAG, "S3 upload succeeded and the Key is: " + success.getKey());
                },
                failure ->
                {
                    Log.i(TAG, "S3 upload failed! " + failure.getMessage());
                }
        );
        setUpAddProductButton();
        setUpSettingsImageView();
        setUpProductListRecyclerView();
        setUpLoginAndLogoutButton();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

//        String userNickname = preferences.getString(UserProfileActivity.USER_NICKNAME_TAG, "No nickname");
//
//        ((TextView) findViewById(R.id.userNicknameTextView)).setText(getString(R.string.nickname_with_input, userNickname));

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        String username="";
        if (authUser == null){
            Button loginButton = (Button) findViewById(R.id.productListLoginButton);
            loginButton.setVisibility(View.VISIBLE);
            Button logoutButton = (Button) findViewById(R.id.productListLogoutButton);
            logoutButton.setVisibility(View.INVISIBLE);
        }else{
             username = authUser.getUsername();
            Log.i(TAG, "Username is: "+ username);
            Button loginButton = (Button) findViewById(R.id.productListLoginButton);
            loginButton.setVisibility(View.INVISIBLE);
            Button logoutButton = (Button) findViewById(R.id.productListLogoutButton);
            logoutButton.setVisibility(View.VISIBLE);

            String username2 = username; // ugly way for lambda hack
            Amplify.Auth.fetchUserAttributes(
                    success ->
                    {
                        Log.i(TAG, "Fetch user attributes succeeded for username: "+username2);
                        for (AuthUserAttribute userAttribute: success){
                            if(userAttribute.getKey().getKeyString().equals("email")){
                                String userEmail = userAttribute.getValue();
                                runOnUiThread(() ->
                                {
                                    ((TextView)findViewById(R.id.userNicknameTextView)).setText(userEmail);
                                });
                            }
                        }
                    },
                    failure ->
                    {
                        Log.i(TAG, "Fetch user attributes failed: "+failure.toString());
                    }
            );
        }
        Amplify.API.query(
                ModelQuery.list(Product.class),
                success ->
                {
                    Log.i(TAG, "Read Product successfully");
                    //products = new ArrayList<>();
                    products.clear();
                    for (Product databaseProduct : success.getData()) {
                        String contactName= "John marten";
                        if(databaseProduct.getContactPerson().getFullName().equals(contactName)) {
                            products.add(databaseProduct);
                        }
                    }
                    //adapter.notifyDataSetChanged();
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                },
                failure -> Log.i(TAG, "Did not read products successfully")
        );
    }

    private void setUpOrderButton() {

        Button orderButton = findViewById(R.id.productListOrderButton);

        orderButton.setOnClickListener(V -> {

            String productName = ((EditText) findViewById(R.id.productNameEditText)).getText().toString();

            Intent goToOrderFromIntent = new Intent(ProductListActivity.this, OrderFormActivity.class);
            goToOrderFromIntent.putExtra(PRODUCT_NAME_EXTRA_TAG, productName);
            startActivity(goToOrderFromIntent);
        });
    }

    private void setUpSettingsImageView() {

        ImageView userSettingsImageView = findViewById(R.id.userProfileImageView2);

        userSettingsImageView.setOnClickListener(V -> {
            Intent goToUserSettingsIntent = new Intent(ProductListActivity.this, UserProfileActivity.class);
            startActivity(goToUserSettingsIntent);
        });
    }

    private void setUpProductListRecyclerView() {
        RecyclerView productListRecyclerView = findViewById(R.id.productListRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        productListRecyclerView.setLayoutManager(layoutManager);

        adapter = new ProductListRecyclerVIewAdapter(products, this);
        productListRecyclerView.setAdapter(adapter);


    }

    private void setUpAddProductButton() {
        Button addProductButton = findViewById(R.id.AddProductButton);
        addProductButton.setOnClickListener(v -> {
            Intent goToAddProductActivity = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(goToAddProductActivity);
        });
    }

    private void init() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        products = new ArrayList<>();
    }

    private void setUpLoginAndLogoutButton(){
        Button loginButton = (Button) findViewById(R.id.productListLoginButton);
        loginButton.setOnClickListener(v ->
        {
            Intent goToLogInIntent = new Intent(ProductListActivity.this, LoginActivity.class);
            startActivity(goToLogInIntent);
        });

        Button logoutButton = (Button) findViewById(R.id.productListLogoutButton);
        logoutButton.setOnClickListener(v->
        {
            Amplify.Auth.signOut(
                    () ->
                    {
                        Log.i(TAG,"Logout succeeded");
                        runOnUiThread(() ->
                        {
                            ((TextView)findViewById(R.id.userNicknameTextView)).setText("");
                        });
                        Intent goToLogInIntent = new Intent(ProductListActivity.this, LoginActivity.class);
                        startActivity(goToLogInIntent);
                    },
                    failure ->
                    {
                        Log.i(TAG, "Logout failed");
                        runOnUiThread(() ->
                        {
                            Toast.makeText(ProductListActivity.this, "Log out failed", Toast.LENGTH_LONG);
                        });
                    }
            );
        });
    }
}