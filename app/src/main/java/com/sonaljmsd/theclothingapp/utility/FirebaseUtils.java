package com.sonaljmsd.theclothingapp.utility;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    private static final String BASE_URL = "https://theclothingapp-default-rtdb.asia-southeast1.firebasedatabase.app/";
    public static DatabaseReference getDatabaseReference(String... pathSegments) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(BASE_URL);
        DatabaseReference ref = database.getReference();

        for (String segment : pathSegments) {
            ref = ref.child(segment);
        }
        return ref;
    }
}