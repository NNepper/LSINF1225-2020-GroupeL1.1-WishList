package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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

    public WishList(String name){
        this.name = name;
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

    public void reorder(int fromPosition, int toPosition){

    }

    private void upItem(){

    }

    private void downItem(){

    }
}
