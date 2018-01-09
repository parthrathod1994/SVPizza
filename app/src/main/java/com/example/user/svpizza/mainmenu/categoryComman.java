package com.example.user.svpizza.mainmenu;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by Parth on 19-06-2016.
 */
public class categoryComman {
    public void delete(Map<String,String> mapMenu,Map<String,String> mapRelation,String categoryKey)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("cart").child(categoryKey).removeValue();
    }
}
