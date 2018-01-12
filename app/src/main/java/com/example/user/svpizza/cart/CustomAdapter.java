package com.example.user.svpizza.cart;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.svpizza.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<cls_cart> {

    private ArrayList<cls_cart> cartlist;
    Context context;

    public CustomAdapter(Context context, int textViewResourceId,
                           ArrayList<cls_cart> cartList) {
        super(context, textViewResourceId, cartList);
        this.cartlist = new ArrayList<cls_cart>();
        this.cartlist.addAll(cartList);
        this.context = context;
    }

    private class ViewHolder {
        TextView name;
        TextView price;
        TextView count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(context);
            convertView = vi.inflate(R.layout.activity_listview, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.rlText);
            holder.price = (TextView) convertView.findViewById(R.id.rlPrice);
            holder.count = (TextView) convertView.findViewById(R.id.rlCount);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        cls_cart cart = cartlist.get(position);
        holder.name.setText(cart.getName());
        holder.price.setText(cart.getPrice());
        holder.count.setText(cart.getCount());

        return convertView;

    }

}
