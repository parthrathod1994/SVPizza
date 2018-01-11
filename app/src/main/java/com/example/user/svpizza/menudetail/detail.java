package com.example.user.svpizza.menudetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.svpizza.R;
import com.example.user.svpizza.mainmenu.firebaseCategoryList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 10-01-2018.
 */

public class detail extends AppCompatActivity {

    Bundle bundle;
    String key,name,ing,price,imgurl;
    TextView tw_name,tw_price,tw_count,tw_ing,tw_total;

    Button add_button;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail);
        overridePendingTransition(0, 0);
        bundle=getIntent().getExtras();
        key = (String) bundle.getCharSequence("name");

        add_button = (Button) findViewById(R.id.btadd);

        tw_name = (TextView) findViewById(R.id.textview1);
        tw_ing = (TextView) findViewById(R.id.textView2);
        tw_price = (TextView) findViewById(R.id.textView5);
        tw_count = (TextView) findViewById(R.id.textView6);
        tw_total = (TextView) findViewById(R.id.textView8);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        //textView.setText(name);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("menu").child(key).child("name").getValue().toString();
                ing = dataSnapshot.child("menu").child(key).child("ing").getValue().toString();
                price = dataSnapshot.child("menu").child(key).child("price").getValue().toString();
                imgurl = dataSnapshot.child("menu").child(key).child("link").getValue().toString();
                tw_name.setText(name);
                tw_ing.setText(ing);
                tw_price.setText(price);
                int count_value = Integer.parseInt(tw_count.getText().toString());
                count_value = count_value * Integer.parseInt(price);
                tw_total.setText(String.valueOf(count_value));
                new DownLoadImageTask(imageView).execute(imgurl);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void button_plus(View view)
    {
        int i = Integer.parseInt(tw_count.getText().toString());
        i++;
        tw_count.setText(String.valueOf(i));
        int count_value = i * Integer.parseInt(price);
        tw_total.setText(String.valueOf(count_value));
    }

    public void button_addcart(View view)
    {
        int i = Integer.parseInt(tw_count.getText().toString());

        Map<String, String> dataValue = new HashMap<String, String>();

        dataValue.put("key", key);
        dataValue.put("name", name);
        dataValue.put("count", String.valueOf(i));
        dataValue.put("price", price);
        dataValue.put("totalprice", tw_total.getText().toString());


        databaseReference.child("cart").push().setValue(dataValue);
    }

    public void button_minus(View view)
    {
        int i = Integer.parseInt(tw_count.getText().toString());
        if(i>0) {
            i--;
        }
        tw_count.setText(String.valueOf(i));
        int count_value = i * Integer.parseInt(price);
        tw_total.setText(String.valueOf(count_value));
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}
