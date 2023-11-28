package com.love2code.buystuff.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Contact;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
import com.google.android.material.snackbar.Snackbar;
import com.love2code.buystuff.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EditProductActivity extends AppCompatActivity {

    public static final String TAG = "editProductActivity";
    private Product productToEdit = null;
    private CompletableFuture<Product> productCompletableFuture = null;
    private Spinner productCategorySpinner = null;
    private Spinner contactSpinner = null;
    private CompletableFuture<List<Contact>> contactsFuture = null;
    private EditText nameEditText;
    private EditText descriptionEditText;

    ActivityResultLauncher<Intent> activityResultLauncher;

    private String s3ImageKey = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        productCompletableFuture = new CompletableFuture<>();
        contactsFuture = new CompletableFuture<>();
        activityResultLauncher = getImagePickingActivityResultLauncher();  // You MUST set this up in onCreate() in the lifecycle
        // WARNING: Do NOT set this up in on an onClickHandler()
        setUpNonButtonUiElements();
        setUpSaveButton();
        setUpDeleteButton();
        setUpAddImageButton();
        setUpDeleteImageButton();
        updateImageButtons();
    }

    private void setUpNonButtonUiElements()
    {
        Intent callingIntent = getIntent();
        String productId = null;
        if (callingIntent != null)
        {
            productId = callingIntent.getStringExtra(ProductListActivity.PRODUCT_ID_TAG);
        }

        String productId2 = productId;  // ugly hack to fix lambda processing
        Amplify.API.query(
                ModelQuery.list(Product.class),
                success ->
                {
                    Log.i(TAG, "Read products successfully!");

                    for (Product databaseProduct : success.getData())
                    {
                        if (databaseProduct.getId().equals(productId2))
                        {
                            productCompletableFuture.complete(databaseProduct);
                        }
                    }

                    runOnUiThread(() ->
                    {
                        // Update UI elements
                    });
                },
                failure -> Log.i(TAG, "Did not read products successfully!")
        );

        try
        {
            productToEdit = productCompletableFuture.get();
        }
        catch (InterruptedException ie)
        {
            Log.e(TAG, "InterruptedException while getting product");
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException ee)
        {
            Log.e(TAG, "ExecutionException while getting product");
        }

        if(productId != null){
            nameEditText = ((EditText)findViewById(R.id.editProductProductNameEditText));
            nameEditText.setText(productToEdit.getName());
            descriptionEditText = ((EditText)findViewById(R.id.editProductDescriptionEditText));
            descriptionEditText.setText(productToEdit.getDescription());
            s3ImageKey = productToEdit.getProductImageS3Key();
        }

        updateImageButtons();

        if (s3ImageKey != null && !s3ImageKey.isEmpty())
        {
            Amplify.Storage.downloadFile(
                    s3ImageKey,
                    new File(getApplication().getFilesDir(), s3ImageKey),
                    success ->
                    {
                        ImageView productImageView = findViewById(R.id.editProductImageImageView);
                        productImageView.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));
                    },
                    failure ->
                    {
                        Log.e(TAG, "Unable to get image from S3 for the product for S3 key: " + s3ImageKey + " for reason: " + failure.getMessage());
                    }
            );
        }
        if(productId != null) {
            setUpSpinners();
        }
    }

    private void setUpSpinners()
    {
        contactSpinner = (Spinner) findViewById(R.id.editProductContactSpinner);

        Amplify.API.query(
                ModelQuery.list(Contact.class),
                success ->
                {
                    Log.i(TAG, "Read contacts successfully!");
                    ArrayList<String> contactNames = new ArrayList<>();
                    ArrayList<Contact> contacts = new ArrayList<>();
                    for (Contact contact : success.getData())
                    {
                        contacts.add(contact);
                        contactNames.add(contact.getFullName());
                    }
                    contactsFuture.complete(contacts);

                    runOnUiThread(() ->
                    {
                        contactSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                contactNames));
                        contactSpinner.setSelection(getSpinnerIndex(contactSpinner, productToEdit.getContactPerson().getFullName()));
                    });
                },
                failure -> {
                    contactsFuture.complete(null);
                    Log.i(TAG, "Did not read contacts successfully!");
                }
        );

        productCategorySpinner = (Spinner) findViewById(R.id.editProductCategorySpinner);
        productCategorySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ProductCategoryEnum.values()));
        productCategorySpinner.setSelection(getSpinnerIndex(productCategorySpinner, productToEdit.getProductCategory().toString()));
    }

    private void setUpSaveButton()
    {
        Button saveButton = (Button)findViewById(R.id.editProductSaveButton);
        saveButton.setOnClickListener(v ->
        {
            saveProduct(s3ImageKey);
        });
    }

    private void saveProduct(String imageS3Key)
    {
        List<Contact> contacts = null;
        String contactToSaveString = contactSpinner.getSelectedItem().toString();
        try
        {
            contacts = contactsFuture.get();
        }
        catch (InterruptedException ie)
        {
            Log.e(TAG, "InterruptedException while getting product");
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException ee)
        {
            Log.e(TAG, "ExecutionException while getting product");
        }
        Contact contactToSave = contacts.stream().filter(c -> c.getFullName().equals(contactToSaveString)).findAny().orElseThrow(RuntimeException::new);
        Product productToSave = Product.builder()
                .name(nameEditText.getText().toString())
                .id(productToEdit.getId())
                .dateCreated(productToEdit.getDateCreated())
                .description(descriptionEditText.getText().toString())
                .contactPerson(contactToSave)
                .productCategory(productCategoryFromString(productCategorySpinner.getSelectedItem().toString()))
                // Added image s3 key here
                .productImageS3Key(imageS3Key)
                .build();

        Amplify.API.mutate(
                ModelMutation.update(productToSave),  // making a GraphQL request to the cloud
                successResponse ->
                {
                    Log.i(TAG, "EditProductActivity.onCreate(): edited a product successfully");
                    Snackbar.make(findViewById(R.id.editProductAcivity), "Product saved!", Snackbar.LENGTH_SHORT).show();
                },  // success callback
                failureResponse -> Log.i(TAG, "EditProductActivity.onCreate(): failed with this response: " + failureResponse)  // failure callback
        );
    }

    private void setUpDeleteButton()
    {
        Button deleteButton = (Button)findViewById(R.id.editProductDeleteButton);
        deleteButton.setOnClickListener(v ->
        {
            Amplify.API.mutate(
                    ModelMutation.delete(productToEdit),
                    successResponse ->
                    {
                        Log.i(TAG, "EditProductActivity.onCreate(): deleted a product successfully");
                        Intent goToProductListActivity = new Intent(EditProductActivity.this, ProductListActivity.class);
                        startActivity(goToProductListActivity);
                    },  // success callback
                    failureResponse -> Log.i(TAG, "EditProductActivity.onCreate(): failed with this response: " + failureResponse)
            );
        });
    }

    private void setUpAddImageButton()
    {
        Button addImageButton = (Button) findViewById(R.id.editProductAddImageButton);
        addImageButton.setOnClickListener(b ->
        {
            launchImageSelectionIntent();
        });

    }

    private void launchImageSelectionIntent()
    {
        // Part 1: Launch activity to pick file

        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");  // only allow one kind or category of file; if you don't have this, you get a very cryptic error about "No activity found to handle Intent"
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        // Below is simple version for testing
        //startActivity(imageFilePickingIntent);

        // Part 2: Create an image picking activity result launcher
        activityResultLauncher.launch(imageFilePickingIntent);

    }

    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher()
    {
        // Part 2: Create an image picking activity result launcher
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>()
                        {
                            @Override
                            public void onActivityResult(ActivityResult result)
                            {
                                Button addImageButton = findViewById(R.id.editProductAddImageButton);
                                if (result.getResultCode() == Activity.RESULT_OK)
                                {
                                    if (result.getData() != null)
                                    {
                                        Uri pickedImageFileUri = result.getData().getData();
                                        try
                                        {
                                            InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                            String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                            Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                            // Part 3: Use our InputStream to upload file to S3
                                            switchFromAddButtonToDeleteButton(addImageButton);
                                            uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename,pickedImageFileUri);

                                        } catch (FileNotFoundException fnfe)
                                        {
                                            Log.e(TAG, "Could not get file from file picker! " + fnfe.getMessage(), fnfe);
                                        }
                                    }
                                }
                                else
                                {
                                    Log.e(TAG, "Activity result error in ActivityResultLauncher.onActivityResult");
                                }
                            }
                        }
                );

        return imagePickingActivityResultLauncher;
    }

    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename,Uri pickedImageFileUri)
    {
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,  // S3 key
                pickedImageInputStream,
                success ->
                {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    // Part 4: Update/save our Product object to have an image key
                    saveProduct(success.getKey());
                    updateImageButtons();
                    ImageView productImageView = findViewById(R.id.editProductImageImageView);
                    InputStream pickedImageInputStreamCopy = null;  // need to make a copy because InputStreams cannot be reused!
                    try
                    {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    }
                    catch (FileNotFoundException fnfe)
                    {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }
                    productImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));

                },
                failure ->
                {
                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
                }
        );
    }

    private void setUpDeleteImageButton()
    {
        Button deleteImageButton = (Button)findViewById(R.id.editProductDeleteImageButton);
        String s3ImageKey = this.s3ImageKey;
        deleteImageButton.setOnClickListener(v ->
        {
            Amplify.Storage.remove(
                    s3ImageKey,
                    success ->
                    {
                        Log.i(TAG, "Succeeded in deleting file on S3! Key is: " + success.getKey());

                    },
                    failure ->
                    {
                        Log.e(TAG, "Failure in deleting file on S3 with key: " + s3ImageKey + " with error: " + failure.getMessage());
                    }
            );
            ImageView productImageView = findViewById(R.id.editProductImageImageView);
            productImageView.setImageResource(android.R.color.transparent);

            saveProduct("");
            switchFromDeleteButtonToAddButton(deleteImageButton);
        });
    }

    private void updateImageButtons() {
        Button addImageButton = findViewById(R.id.editProductAddImageButton);
        Button deleteImageButton = findViewById(R.id.editProductDeleteImageButton);
        runOnUiThread(() -> {
            if (s3ImageKey.isEmpty()) {
                deleteImageButton.setVisibility(View.INVISIBLE);
                addImageButton.setVisibility(View.VISIBLE);
            } else {
                deleteImageButton.setVisibility(View.VISIBLE);
                addImageButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void switchFromDeleteButtonToAddButton(Button deleteImageButton) {
        Button addImageButton = findViewById(R.id.editProductAddImageButton);
        deleteImageButton.setVisibility(View.INVISIBLE);
        addImageButton.setVisibility(View.VISIBLE);
    }

    private void switchFromAddButtonToDeleteButton(Button addImageButton) {
        Button deleteImageButton = findViewById(R.id.editProductDeleteImageButton);
        deleteImageButton.setVisibility(View.VISIBLE);
        addImageButton.setVisibility(View.INVISIBLE);
    }
    // Taken from https://stackoverflow.com/a/25005243/16889809
    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private int getSpinnerIndex(Spinner spinner, String stringValueToCheck){
        for (int i = 0;i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(stringValueToCheck)){
                return i;
            }
        }

        return 0;
    }

    public static ProductCategoryEnum productCategoryFromString(String inputProductCategoryText) {
        for (ProductCategoryEnum productCategory : ProductCategoryEnum.values()) {
            if (productCategory.toString().equals(inputProductCategoryText)) {
                return productCategory;
            }
        }
        return null;
    }
}
