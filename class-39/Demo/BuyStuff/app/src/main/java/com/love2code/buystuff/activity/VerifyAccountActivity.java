package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.love2code.buystuff.R;

public class VerifyAccountActivity extends AppCompatActivity {

    public static final String TAG= "VerifyAccountActivity";

    public static final String VERIFY_ACCOUNT_EMAIL_TAG = "Verify_Account_Email_Tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);

        Intent callingIntent= getIntent();
        String email = callingIntent.getStringExtra(SignupActivity.SIGNUP_EMAIL_TAG);
        EditText usernameEditText = (EditText) findViewById(R.id.verifyAccountUsernameEditText);
        usernameEditText.setText(email);

        Button verifyAccountVerifyButton = findViewById(R.id.verifyAccountVerifyButton);
        verifyAccountVerifyButton.setOnClickListener(v ->
        {
            String username= usernameEditText.getText().toString();
            String verificationCode = ((EditText)findViewById(R.id.verifyAccountVerificatoinCodeEditText)).getText().toString();

            Amplify.Auth.confirmSignUp(username,
                    verificationCode,
                    god ->
                    {
                        Log.i(TAG,"verification succeeded: "+ god.toString());
                        Intent goToLoginIntent = new Intent(VerifyAccountActivity.this, LoginActivity.class);
                        goToLoginIntent.putExtra(VERIFY_ACCOUNT_EMAIL_TAG, username);
                        startActivity(goToLoginIntent);

                    },
                    failure ->
                    {
                        Log.i(TAG,"verification failed: "+ failure.toString());
                        runOnUiThread(() ->
                        {
                            Toast.makeText(VerifyAccountActivity.this, " Verify account failed!!", Toast.LENGTH_LONG);
                        });
                    }
            );
        });
    }
}