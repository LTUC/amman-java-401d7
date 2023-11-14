package com.love2code.buystuff.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.love2code.buystuff.R;

public class UserProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public static final String USER_NICKNAME_TAG="userNickname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Button saveButton= findViewById(R.id.saveUserSettingsButton);

        saveButton.setOnClickListener(view -> {
            SharedPreferences.Editor preferneceEditor= sharedPreferences.edit();
            EditText userNicknameEditText = findViewById(R.id.nicknameInputEditText);
            String userNicknameString = userNicknameEditText.getText().toString();

            preferneceEditor.putString(USER_NICKNAME_TAG, userNicknameString);//k,v
            preferneceEditor.apply();

            Snackbar.make(findViewById(R.id.userProfileActivity), "Settings Saved", Snackbar.LENGTH_SHORT).show();
        });

    }
}