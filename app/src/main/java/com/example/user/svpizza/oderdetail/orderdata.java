package com.example.user.svpizza.oderdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.user.svpizza.R;
import com.example.user.svpizza.cart.cls_cart;
import com.example.user.svpizza.cart.firebaseCartDetailList;
import com.example.user.svpizza.mainmenu.firebaseCategoryList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by USER on 12-01-2018.
 */

public class orderdata extends AppCompatActivity {

    DatabaseReference databaseReference;

    ArrayList<String> Items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetail);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Items.clear();

                for (DataSnapshot categoryData : dataSnapshot.child("cart").getChildren()) {
                    firebaseCartDetailList obj_category = categoryData.getValue(firebaseCartDetailList.class);
                    Items.add(obj_category.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void confirmOrder(View view)
    {
        for (int i=0;i<Items.size();i++) {
            databaseReference.child("cart").child(Items.get(i)).child("count").setValue("0");
            databaseReference.child("cart").child(Items.get(i)).child("totalprice").setValue("0");
        }
        Toast.makeText(getApplicationContext(),"Your Order Is Placed",Toast.LENGTH_SHORT).show();
        finish();
    }
}
