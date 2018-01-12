package com.example.user.svpizza.mainmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.user.svpizza.R;
import com.example.user.svpizza.cart.*;
import com.example.user.svpizza.menudetail.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class category extends AppCompatActivity {

    Spinner sp_spinner;

    final Context context = this;

    String text;

    Map<String, String> mapMenu;

    DatabaseReference databaseReference;

    Button bt_proceed;

    ImageView img_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapMenu = new HashMap<String, String>();

        sp_spinner = (Spinner) findViewById(R.id.menuspinner);
        bt_proceed = (Button) findViewById(R.id.btproceed);

        img_cart = (ImageView) findViewById(R.id.imageView4);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> Items = new ArrayList<String>();
                mapMenu.clear();

                for (DataSnapshot categoryData : dataSnapshot.child("menu").getChildren()) {
                    firebaseCategoryList obj_category = categoryData.getValue(firebaseCategoryList.class);
                    Items.add(obj_category.getName());
                    mapMenu.put(obj_category.getName(), categoryData.getKey());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_center_allign,Items);
                sp_spinner.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bt_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text != null) {
                    Intent menu_detail = new Intent(getApplicationContext(), detail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", text);
                    menu_detail.putExtras(bundle);
                    startActivity(menu_detail);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Select Any Pizza",Toast.LENGTH_SHORT).show();
                }
            }
        });

        sp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text = mapMenu.get(sp_spinner.getSelectedItem().toString()).toString();
                //Toast.makeText(context,text,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent Login = new Intent(getApplicationContext(), menu.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", mapCategory.get(adapter.getItem(position).toString()));
                bundle.putString("name", adapter.getItem(position).toString());
                Login.putExtras(bundle);
                startActivity(Login);
            }
        });
        registerForContextMenu(listView);*/

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                category.this.adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(getApplicationContext(), print.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.mlMenu) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(adapter.getItem(info.position).toString());
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        final String listItemName = adapter.getItem(info.position).toString();
        if (menuItemName.equals("Edit")) {
            layoutInflater = LayoutInflater.from(context);

            promptView = layoutInflater.inflate(R.layout.app_promptbox, null);

            alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setView(promptView);

            input = (EditText) promptView.findViewById(R.id.userInput);

            input.setText(listItemName);

            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            String editText = input.getText().toString();

                            if (mapCategory.size() == 0 || mapCategory.get(editText) == null) {
                                String key = mapCategory.get(listItemName);

                                databaseReference.child("category").child(key).child("name").setValue(editText);

                                Toast.makeText(getApplicationContext(), editText + " Edited ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Data Already Exist ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }
                    );
            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
        } else if (menuItemName.equals("Delete")) {
            alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setMessage("Do you want to Delete this item ?");

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String categoryKey = mapCategory.get(listItemName);

                    categoryComman objComman = new categoryComman();

                    objComman.delete(mapMenu, mapRelation, categoryKey);

                    Toast.makeText(context, "item deleted", Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartmain:
                Intent menu_cart = new Intent(getApplicationContext(), menucart.class);
                startActivity(menu_cart);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}


