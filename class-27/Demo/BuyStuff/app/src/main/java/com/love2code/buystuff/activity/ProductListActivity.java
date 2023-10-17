package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.prefs.Preferences;

public class ProductListActivity extends AppCompatActivity {

    public final String TAG = "productActivity";

    public static final String PRODUCT_NAME_EXTRA_TAG="productName";

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setUpOrderButton();
        setUpSettingsImageView();

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
}