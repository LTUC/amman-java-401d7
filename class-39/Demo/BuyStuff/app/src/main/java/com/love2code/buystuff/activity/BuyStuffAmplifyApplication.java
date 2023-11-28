package com.love2code.buystuff.activity;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.geo.location.AWSLocationGeoPlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class BuyStuffAmplifyApplication extends Application {

    public static final String TAG= "buystuffapplication";
    @Override
    public void onCreate() {
        super.onCreate();

        try{
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSLocationGeoPlugin());
            Amplify.configure(getApplicationContext());
        }catch (AmplifyException ae){
            Log.e(TAG, "Error initializing Amplify" + ae.getMessage(), ae);
        }
    }
}
