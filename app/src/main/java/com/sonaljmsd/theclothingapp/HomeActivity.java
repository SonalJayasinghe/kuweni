/*
* AS2021939 - J.M.S.D. Jayasinghe
* AS2021981 - G.H.V. Ardarsha
* AS2021980 - S. Sameer
* AS2021904 - B.W.G.D.T Weerasinghe
* AS2021959 - K.G.S.Dileka
* */

package com.sonaljmsd.theclothingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.sonaljmsd.theclothingapp.adapters.CategoryAdapter;
import com.sonaljmsd.theclothingapp.models.CategoryModel;
import com.sonaljmsd.theclothingapp.utility.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private List<CategoryModel> categories;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Layout Selection for RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set visibility and Start the ProgressBar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        categories = new ArrayList<>();

        // Create an adapter for the RecyclerView
        CategoryAdapter adapter = new CategoryAdapter(this, categories, category -> {

            // Start the DetailActivity based on the clicked category
            Intent intent = new Intent(HomeActivity.this, SubcategoryActivity.class);

            // Pass the category key and name to the DetailActivity
            intent.putExtra("EXTRA_CATEGORY_KEY", category.getCategoryKey());
            intent.putExtra("EXTRA_CATEGORY_NAME", category.getName());

            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Get categories reference from Firebase
        DatabaseReference categoryRef = FirebaseUtils.getDatabaseReference("categories");

        // Get data from Firebase
        categoryRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());

            } else {

                // Get the data from Firebase and add it to the categories list
                for (DataSnapshot categorySnapshot : task.getResult().getChildren()) {
                    String name = categorySnapshot.child("name").getValue(String.class);
                    String imgUrl = categorySnapshot.child("imgUrl").getValue(String.class);
                    String key = categorySnapshot.getKey();
                    categories.add(new CategoryModel(name, imgUrl, key));
                }

                // Stop the ProgressBar
                progressBar.setVisibility(View.GONE);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });
    }
}

