package com.example.user.svpizza.cart;

/**
 * Created by USER on 11-01-2018.
 */

public class cls_cart {

    String key = null;
    String name = null;
    String price = null;
    String count = null;

    public cls_cart(String iName,String iKey,String iprice,String icount){
        super();
        this.name = iName;
        this.key = iKey;
        this.price = iprice;
        this.count = icount;
    }

    public String getKey(){
        return key;
    }

    public String getName(){
        return name;
    }

    public String getPrice(){
        return price;
    }

    public String getCount(){
        return count;
    }
}

