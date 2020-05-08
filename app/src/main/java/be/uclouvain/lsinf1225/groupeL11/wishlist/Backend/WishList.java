package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
public class WishList{

    private int id;
    public String name;
    public String description;
    public ArrayList<Product> products;
    public User user = null;

    public WishList(int id) {
        this.id = id;
        this.products = new ArrayList<Product>();
    }

    public WishList(String name, String description){
        this.name = name;
        this.description = description;
        this.products = new ArrayList<Product>();
    }


    public void setId(int id){ this.id=id; }

    public int getId(){ return this.id; }

    public void addProduct(Product product){
        this.products.add(product);
        product.wishlist = this;
    }

    public ArrayList<Product> getProducts() { return this.products; }

    public Product getProduct(int i) { return this.products.get(i); }

    public void reorder(Context context){
        ProductDAO productDAO = new ProductDAO(context);
        for(Product product:products){
            productDAO.update(product);
        }
        Log.v("debug-gwen", "savec in DB");
    }
}
