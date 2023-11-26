package com.love2code.buystuff.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Contact;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
import com.google.android.material.snackbar.Snackbar;
import com.love2code.buystuff.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddProductActivity extends AppCompatActivity {

    public static final String TAG = "AddProductActivity";

    Spinner contactSpinner= null;

    Spinner productCategorySpinner= null;
    CompletableFuture<List<Contact>> contactFuture = new CompletableFuture<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        contactFuture = new CompletableFuture<>();
        setUpSpinners();
        setUpSaveButton();
    }

    @Override
    protected void onResume()
    {
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

       if(callingIntent != null && callingIntent.getType() != null && callingIntent.getType().startsWith("image") ){
           Uri incomingImageFileUri= callingIntent.getParcelableExtra(Intent.EXTRA_STREAM);

           if (incomingImageFileUri != null){
               InputStream incomingImageFileInputStream = null;

               try {
                   incomingImageFileInputStream = getContentResolver().openInputStream(incomingImageFileUri);

                   ImageView productImageView = findViewById(R.id.imageView);

                   if (productImageView != null) {

                       productImageView.setImageBitmap(BitmapFactory.decodeStream(incomingImageFileInputStream));
                   }else {
                       Log.e(TAG, "ImageView is null for some reasons");
                   }
               }catch (FileNotFoundException fnfe){
                   Log.e(TAG," Could not get file stram from the URI "+fnfe.getMessage(),fnfe);
               }
           }
       }

    }

    private void setUpSpinners(){
        contactSpinner = findViewById(R.id.addProductContactSpinner);

        Amplify.API.query(
                ModelQuery.list(Contact.class),
                success ->
                {
                    Log.i(TAG, "Read Contact Successfully");
                    ArrayList<String> contactNames = new ArrayList<>();
                    ArrayList<Contact> contacts = new ArrayList<>();
                    for(Contact contact: success.getData()){
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
                failure-> {
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

    private void setUpSaveButton(){
        Button saveButton = findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(v -> {

            String name = ((EditText)findViewById(R.id.addProductProductNameEditTExt)).getText().toString();
            String description = ((EditText)findViewById(R.id.addProductProductDescriptionEditText)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
            String selectedContactString = contactSpinner.getSelectedItem().toString();

            List<Contact> contacts=null;
            try {
                contacts=contactFuture.get();
            }catch (InterruptedException ie){
                Log.e(TAG, " InterruptedException while getting contacts");
            }catch (ExecutionException ee){
                Log.e(TAG," ExecutionException while getting contacts");
            }

            assert contacts != null;
            Contact selectedContact = contacts.stream().filter(c -> c.getFullName().equals(selectedContactString)).findAny().orElseThrow(RuntimeException::new);

            Product newProduct = Product.builder()
                    .name(name)
                    .description(description)
                    .dateCreated(new Temporal.DateTime(currentDateString))
                    .productCategory((ProductCategoryEnum) productCategorySpinner.getSelectedItem())
                    .contactPerson(selectedContact).build();

            Amplify.API.mutate(
                    ModelMutation.create(newProduct),
                    successResponse -> Log.i(TAG, "AddProductActivity.onCreate(): made a product successfully"),//success response
                    failureResponse -> Log.e(TAG, "AddProductActivity.onCreate(): failed with this response" + failureResponse)// in case we have a failed response
            );
            Snackbar.make(findViewById(R.id.addProductActivity), "Product saved!", Snackbar.LENGTH_SHORT).show();
        });
    }

    private String cleanText(String text) {
        // Remove links
        text = text.replaceAll("\\b(?:https?|ftp):\\/\\/\\S+\\b", "");

        // Remove double quotes
        text = text.replaceAll("\"", "");

        return text;
    }
}