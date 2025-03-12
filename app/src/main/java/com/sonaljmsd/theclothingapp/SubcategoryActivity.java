package com.sonaljmsd.theclothingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.sonaljmsd.theclothingapp.adapters.SubCategoryAdapter;
import com.sonaljmsd.theclothingapp.models.SubCategoryModel;
import com.sonaljmsd.theclothingapp.utility.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryActivity extends AppCompatActivity {
    private List<SubCategoryModel> subCategories;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        // Get the category key and name from the intent
        String categoryKey = getIntent().getStringExtra("EXTRA_CATEGORY_KEY");
        String categoryName = getIntent().getStringExtra("EXTRA_CATEGORY_NAME");

        // Set the title of the activity
        TextView textView = findViewById(R.id.txtTitle);
        textView.setText(categoryName);

        // Set the Layout for the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set and Start the ProgressBar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        subCategories = new ArrayList<>();

        // Create an adapter for the RecyclerView
        SubCategoryAdapter adapter = new SubCategoryAdapter(this, subCategories, subCategory -> {

            // Start the ItemActivity based on the clicked subcategory
            Intent intent = new Intent(SubcategoryActivity.this, ProductActivity.class);

            // Pass the category key and subcategory key, name to the ItemActivity
            intent.putExtra("EXTRA_CATEGORY_KEY", subCategory.getCategoryKey());
            intent.putExtra("EXTRA_SUBCATEGORY_KEY", subCategory.getSubcategoryKey());
            intent.putExtra("EXTRA_SUBCATEGORY_NAME", subCategory.getName());

            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);


        // Get the subcategories database reference from Firebase
        //categories -> category key eka -> subcategories
        DatabaseReference subCategoryRef = FirebaseUtils.getDatabaseReference(
                "categories",
                categoryKey,
                "subcategories");

        // Get data from Firebase
        subCategoryRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());

            } else {

                // Get the data from Firebase and add it to the subcategories list
                for (DataSnapshot categorySnapshot : task.getResult().getChildren()) {
                    String name = categorySnapshot.child("name").getValue(String.class);
                    String imgUrl = categorySnapshot.child("imgUrl").getValue(String.class);
                    String key = categorySnapshot.getKey();
                    subCategories.add(new SubCategoryModel(name, imgUrl, categoryKey, key));
                }
                // Hide progress bar
                progressBar.setVisibility(View.GONE);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });
    }
}

