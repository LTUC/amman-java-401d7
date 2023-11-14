package com.love2code.buystuff.activity.adapter;

import static com.love2code.buystuff.activity.ProductListActivity.PRODUCT_ID_TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Product;
import com.love2code.buystuff.R;
import com.love2code.buystuff.activity.EditProductActivity;
import com.love2code.buystuff.activity.OrderFormActivity;
import com.love2code.buystuff.activity.ProductListActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ProductListRecyclerVIewAdapter extends RecyclerView.Adapter<ProductListRecyclerVIewAdapter.ProductListViewHolder> {

    List<Product> products;

    Context callingActivity;

    public ProductListRecyclerVIewAdapter(List<Product> products,Context callingActivity) {
        this.products = products;
        this.callingActivity=callingActivity;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_list, parent,false);

        return new ProductListViewHolder(productFragment);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {

        TextView productFragmentTextView = holder.itemView.findViewById(R.id.productFragmentTextView);
        Product product = products.get(position);

        DateFormat dateCreatedIso8061InputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        dateCreatedIso8061InputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        @SuppressLint("SimpleDateFormat") DateFormat dateCreatedOutputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateCreatedOutputFormat.setTimeZone(TimeZone.getDefault());
        String dateCreatedString = "";

        try {
            {
                Date dateCreatedJavaDate = dateCreatedIso8061InputFormat.parse(product.getDateCreated().format());
                if (dateCreatedJavaDate != null){
                    dateCreatedString = dateCreatedOutputFormat.format(dateCreatedJavaDate);
                }
            }
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
        productFragmentTextView.setText(position +". "+ product.getName()

            + "," + product.getDescription()
            + "," + dateCreatedString
                + "," + product.getProductCategory()
        + ", " + product.getContactPerson().getFullName());

        View productViewHolder = holder.itemView;
        productViewHolder.setOnClickListener(view -> {
            Intent goToOrderFormIntent = new Intent(callingActivity, EditProductActivity.class);
            goToOrderFormIntent.putExtra(PRODUCT_ID_TAG, product.getId());
            callingActivity.startActivity(goToOrderFormIntent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductListViewHolder extends RecyclerView.ViewHolder{

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
