package com.love2code.buystuff.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Contact;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.snackbar.Snackbar;
import com.love2code.buystuff.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddProductActivity extends AppCompatActivity {

    public static final String TAG = "AddProductActivity";
     static final int LOCATION_POLLING_INTERVAL = 5 * 1000;

    Spinner contactSpinner = null;

    Spinner productCategorySpinner = null;
    CompletableFuture<List<Contact>> contactFuture = new CompletableFuture<>();

    FusedLocationProviderClient locationProviderClient = null;

    Geocoder geocoder=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        contactFuture = new CompletableFuture<>();

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationProviderClient.flushLocations();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationProviderClient.getLastLocation().addOnSuccessListener(location ->
        {
            if (location == null) {
                Log.e(TAG, "Location CallBack was null");
            }
            String currentLatitude = Double.toString(location.getLatitude());
            String currentLongitude = Double.toString(location.getLongitude());
            Log.i(TAG, "Our userLatitude: " + location.getLatitude());
            Log.i(TAG, "Our userLongitude: " + location.getLongitude());
        });

        locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        });

        geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        LocationRequest locationRequest= LocationRequest.create();
        locationRequest.setInterval(LOCATION_POLLING_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                try {
                    String address = geocoder.getFromLocation(
                            locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude(),
                            1)
                            .get(0)
                            .getAddressLine(0);
                    Log.i(TAG,"Repeating current location is: "+address);
                }catch (IOException ioe){
                    Log.e(TAG, "Could not get subscribed location: "+ioe.getMessage(), ioe);
                }
            }
        };

        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
        setUpSpinners();
        setUpSaveButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.getType() != null && callingIntent.getType().equals("text/plain")) {
            String callingText = callingIntent.getStringExtra(Intent.EXTRA_TEXT);

            if (callingText != null) {
                // Clean the text by removing unwanted links or formatting
                String cleanedText = cleanText(callingText);

                // Set the cleaned text in the UI
                ((EditText) findViewById(R.id.addProductProductNameEditTExt)).setText(cleanedText);
            }
        }

        if (callingIntent != null && callingIntent.getType() != null && callingIntent.getType().startsWith("image")) {
            Uri incomingImageFileUri = callingIntent.getParcelableExtra(Intent.EXTRA_STREAM);

            if (incomingImageFileUri != null) {
                InputStream incomingImageFileInputStream = null;

                try {
                    incomingImageFileInputStream = getContentResolver().openInputStream(incomingImageFileUri);

                    ImageView productImageView = findViewById(R.id.imageView);

                    if (productImageView != null) {

                        productImageView.setImageBitmap(BitmapFactory.decodeStream(incomingImageFileInputStream));
                    } else {
                        Log.e(TAG, "ImageView is null for some reasons");
                    }
                } catch (FileNotFoundException fnfe) {
                    Log.e(TAG, " Could not get file stram from the URI " + fnfe.getMessage(), fnfe);
                }
            }
        }

    }

    private void setUpSpinners() {
        contactSpinner = findViewById(R.id.addProductContactSpinner);

        Amplify.API.query(
                ModelQuery.list(Contact.class),
                success ->
                {
                    Log.i(TAG, "Read Contact Successfully");
                    ArrayList<String> contactNames = new ArrayList<>();
                    ArrayList<Contact> contacts = new ArrayList<>();
                    for (Contact contact : success.getData()) {
                        contacts.add(contact);
                        contactNames.add(contact.getFullName());
                    }
                    contactFuture.complete(contacts);

                    runOnUiThread(() ->
                            contactSpinner.setAdapter(new ArrayAdapter<>(
                                    this,
                                    (android.R.layout.simple_spinner_item),
                                    contactNames
                            )));
                },
                failure -> {
                    contactFuture.complete(null);
                    Log.i(TAG, "Did not read contacts successfully");
                }
        );
        productCategorySpinner = findViewById(R.id.addProductProductCategorySpinner);
        productCategorySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ProductCategoryEnum.values()
        ));
    }

    private void setUpSaveButton() {
        Button saveButton = findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(v -> {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String name = ((EditText) findViewById(R.id.addProductProductNameEditTExt)).getText().toString();
            String description = ((EditText) findViewById(R.id.addProductProductDescriptionEditText)).getText().toString();
            String selectedContactString = contactSpinner.getSelectedItem().toString();

            List<Contact> contacts = null;
            try {
                contacts = contactFuture.get();
            } catch (InterruptedException ie) {
                Log.e(TAG, " InterruptedException while getting contacts");
            } catch (ExecutionException ee) {
                Log.e(TAG, " ExecutionException while getting contacts");
            }
            Contact selectedContact = contacts.stream().filter(c -> c.getFullName().equals(selectedContactString)).findAny().orElseThrow(RuntimeException::new);

            locationProviderClient.getLastLocation().addOnSuccessListener(location ->
                            {
                                if (location == null) {
                                    Log.e(TAG, "Location CallBack was null");
                                }
                                String currentLatitude = Double.toString(location.getLatitude());
                                String currentLongitude = Double.toString(location.getLongitude());
                                Log.i(TAG, "Our userLatitude: " + location.getLatitude());
                                Log.i(TAG, "Our userLongitude: " + location.getLongitude());
                                saveProduct(name, description, currentLatitude, currentLongitude, selectedContact);

                            }

                    ).addOnCanceledListener(() ->
                    {
                        Log.e(TAG, "Location request was Canceled");
                    })
                    .addOnFailureListener(failure ->
                    {
                        Log.e(TAG, "Location request failed, Error was: " + failure.getMessage(), failure.getCause());
                    })
                    .addOnCompleteListener(complete ->
                    {
                        Log.e(TAG, "Location request Completed");
                    });
        });
    }

    private void saveProduct(String name, String description, String latitude, String longitude, Contact selectedContact) {
        Product newProduct = Product.builder()
                .name(name)
                .description(description)
                .dateCreated(new Temporal.DateTime(new Date(), 0))
                .productCategory((ProductCategoryEnum) productCategorySpinner.getSelectedItem())
                .productLatitude(latitude)
                .productLongitude(longitude)
                .contactPerson(selectedContact)
                .productImageS3Key("")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newProduct),
                successResponse -> Log.i(TAG, "AddProductActivity.onCreate(): made a product successfully"),//success response
                failureResponse -> Log.e(TAG, "AddProductActivity.onCreate(): failed with this response" + failureResponse)// in case we have a failed response
        );
        Snackbar.make(findViewById(R.id.addProductActivity), "Product saved!", Snackbar.LENGTH_SHORT).show();
    }

    private String cleanText(String text) {
        // Remove links
        text = text.replaceAll("\\b(?:https?|ftp):\\/\\/\\S+\\b", "");

        // Remove double quotes
        text = text.replaceAll("\"", "");

        return text;
    }
}