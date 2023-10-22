package com.love2code.buystuff.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.love2code.buystuff.R;
import com.love2code.buystuff.activity.OrderFormActivity;
import com.love2code.buystuff.activity.ProductListActivity;
import com.love2code.buystuff.activity.model.Product;

import java.util.List;

//TODO: step 1-4: Make a class whose purpose is to manage the RecyclerView
//TODO: step 3-1: clean up the RecyclerView.Adapter reference to actually use ProductListRecyclerViewAdapter
public class ProductListRecyclerVIewAdapter extends RecyclerView.Adapter<ProductListRecyclerVIewAdapter.ProductListViewHolder> {

    //TODO: step: 2-3: Hand in data items
    List<Product> products;

    //TODO: step 3-2: Hand in the Activity context
    Context callingActivity;

   //TODO: step: 2-3: Hand in data items
    public ProductListRecyclerVIewAdapter(List<Product> products,Context callingActivity) {
        this.products = products;
        this.callingActivity=callingActivity;
    }

    //TODO: step 1-7: start Inflate fragment
    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_list, parent,false);

        //TODO: step 1-9: Attach fragment to viewHolder
        return new ProductListViewHolder(productFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {

        //TODO: step 2-4: Bind data items to Fragment inside of ViewHolder
        TextView productFragmentTextView = (TextView) holder.itemView.findViewById(R.id.productFragmentTextView);
        String productName = products.get(position).getName();
        productFragmentTextView.setText(position +". "+ productName);

        //TODO: step 3-3: create a onClickListener, make an intent inside it and call this intent with extra to go to another activity
        View productViewHolder = holder.itemView;
        productViewHolder.setOnClickListener(view -> {
            Intent goToOrderFormIntent = new Intent(callingActivity, OrderFormActivity.class);
            goToOrderFormIntent.putExtra(ProductListActivity.PRODUCT_NAME_EXTRA_TAG, productName);
            callingActivity.startActivity(goToOrderFormIntent);
        });
    }

    @Override
    public int getItemCount() {
        //TODO: STEP 2-5: Make the size of the list dynamic
        return products.size();
    }

    // TODO: step 1-8: make a ViewHolder class to hold a fragment
    public static class ProductListViewHolder extends RecyclerView.ViewHolder{

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
