package com.sonaljmsd.theclothingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.sonaljmsd.theclothingapp.adapters.ProductAdapter;
import com.sonaljmsd.theclothingapp.models.ProductModel;
import com.sonaljmsd.theclothingapp.utility.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private List<ProductModel> items;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //Get the category key and subcategory key, name from the intent
        String categoryKey = getIntent().getStringExtra("EXTRA_CATEGORY_KEY");
        String subCategoryKey = getIntent().getStringExtra("EXTRA_SUBCATEGORY_KEY");
        String subCategoryName = getIntent().getStringExtra("EXTRA_SUBCATEGORY_NAME");

        //Set the title of the activity
        TextView textView = findViewById(R.id.txtTitle);
        textView.setText(subCategoryName);

        //Set the Layout for the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //Set and Start the ProgressBar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        items = new ArrayList<>();

        //Create an adapter for the RecyclerView
        ProductAdapter adapter = new ProductAdapter(this, items, item -> { /*Do Nothing*/ });
        recyclerView.setAdapter(adapter);

        //Get the items database reference from Firebase
        //categories -> category key eka -> subcategories -> subCategory key eka -> items
        DatabaseReference itemRef = FirebaseUtils.getDatabaseReference("categories", categoryKey, "subcategories", subCategoryKey, "items");

        // Get data from Firebase
        itemRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());

            } else {

                // Get the data from Firebase and add it to the items list
                for (DataSnapshot categorySnapshot : task.getResult().getChildren()) {

                    String name = categorySnapshot.child("name").getValue(String.class);
                    String imgUrl = categorySnapshot.child("imgUrl").getValue(String.class);
                    Double price = categorySnapshot.child("price").getValue(Double.class);
                    items.add(new ProductModel(name, imgUrl, "$"+price.toString()));
                }

                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();

            }
        });
    }
}

