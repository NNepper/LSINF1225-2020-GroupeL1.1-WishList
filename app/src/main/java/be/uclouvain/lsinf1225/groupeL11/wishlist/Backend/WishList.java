package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class WishList {
    private String wishlistName;
    private String wishlistDescrpition;
    private ArrayList<Product> products;

    public WishList(String wishlistName, String wishlistDecrpition, ArrayList<Product> products) {
        this.wishlistName = wishlistName;
        this.wishlistDescrpition = wishlistDecrpition;
        this.products = products;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public String getWishlistDecrpition() {
        return wishlistDescrpition;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Product getProduct(int i) {
        return products.get(i);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(int i, Product product) {
        products.add(i, product);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public Product removeProduct(int i) {
        return products.remove(i);
    }

    public void sortProductsByPosition() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.getPosition() - o1.getPosition();
            }
        });
    }
}
