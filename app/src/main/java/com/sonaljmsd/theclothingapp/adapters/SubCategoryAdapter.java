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
import com.sonaljmsd.theclothingapp.models.SubCategoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private List<SubCategoryModel> subCategories;
    private SubCategoryAdapter.OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        //Define the listener
        void onItemClick(SubCategoryModel category);
    }

    public SubCategoryAdapter(Context context, List<SubCategoryModel> subCategories, SubCategoryAdapter.OnItemClickListener listener) {
        //Initialize the adapter
        this.subCategories = subCategories;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout for the RecyclerView
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_component,parent, false);
        return new SubCategoryAdapter.SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.SubCategoryViewHolder holder, int position) {
        //Set the data for the RecyclerView
        holder.textView.setText(subCategories.get(position).getName());
        Picasso.get().load(subCategories.get(position).getImageUrl()).into((ImageView) holder.imageView.findViewById(R.id.imgProduct));
        holder.bind(subCategories.get(position),  listener);

    }

    @Override
    public int getItemCount() {
        //Return the size of the list
        return subCategories.size();
    }

    public static class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        //Define the ViewHolder
        TextView textView;
        ImageView imageView;


        public SubCategoryViewHolder(View itemView) {
            //Initialize the views
            super(itemView);
            textView = itemView.findViewById(R.id.txtProductTitle);
            imageView = itemView.findViewById(R.id.imgProduct);
        }

        public void bind(SubCategoryModel subCategory, SubCategoryAdapter.OnItemClickListener listener) {
            //Set the click listener for the RecyclerView
            textView.setText(subCategory.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(subCategory));
        }
    }
}
