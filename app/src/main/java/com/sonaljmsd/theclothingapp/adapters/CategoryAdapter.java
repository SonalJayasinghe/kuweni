package com.sonaljmsd.theclothingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonaljmsd.theclothingapp.models.CategoryModel;
import com.sonaljmsd.theclothingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryModel> categories;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        //Define the listener
        void onItemClick(CategoryModel category);
    }

    public CategoryAdapter(Context context, List<CategoryModel> categories, OnItemClickListener listener) {
        // Initialize the adapter
        this.categories = categories;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for the RecyclerView
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_component,parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        //Set the data for the RecyclerView
        holder.textView.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getImageUrl()).into((ImageView) holder.imageView.findViewById(R.id.imgProduct));
        holder.bind(categories.get(position), listener);

    }

    @Override
    public int getItemCount() {
        //Return the size of the list
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        //Define the views for the RecyclerView
        TextView textView;
        ImageView imageView;


        public CategoryViewHolder(View itemView) {
            //Initialize the views
            super(itemView);
            textView = itemView.findViewById(R.id.txtProductTitle);
            imageView = itemView.findViewById(R.id.imgProduct);
        }

        public void bind(CategoryModel category, OnItemClickListener listener) {
            //Set the click listener for the RecyclerView
            textView.setText(category.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(category));
        }
    }
}
