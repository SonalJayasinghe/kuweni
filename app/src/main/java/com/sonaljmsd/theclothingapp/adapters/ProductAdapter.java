package com.sonaljmsd.theclothingapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonaljmsd.theclothingapp.R;
import com.sonaljmsd.theclothingapp.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemViewHolder> {

    private List<ProductModel> items;
    private ProductAdapter.OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        //Define the listener
        void onItemClick(ProductModel category);
    }

    public ProductAdapter(Context context, List<ProductModel> items, ProductAdapter.OnItemClickListener listener) {
        //Initialize the adapter
        this.items = items;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for the RecyclerView
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_component,parent, false);
        return new ProductAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ItemViewHolder holder, int position) {
        //Set the data for the RecyclerView
        holder.textView.setText(items.get(position).getName());
        holder.textPrice.setText(items.get(position).getPrice());
        Picasso.get().load(items.get(position).getImageUrl()).into((ImageView) holder.imageView.findViewById(R.id.imgProduct));
        holder.bind(items.get(position),  listener);

    }

    @Override
    public int getItemCount() {
        //Return the size of the list
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        //Define the views
        TextView textView;
        ImageView imageView;
        TextView textPrice;

        public ItemViewHolder(View itemView) {
            //Initialize the views
            super(itemView);
            textView = itemView.findViewById(R.id.txtProductTitle);
            imageView = itemView.findViewById(R.id.imgProduct);
            textPrice = itemView.findViewById(R.id.txtPrice);
        }

        public void bind(ProductModel item, ProductAdapter.OnItemClickListener listener) {
            //Set the click listener
            textView.setText(item.getName());
            textPrice.setText(item.getPrice());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
