package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;

public class WishList {
    ArrayList<Product> products;

    public WishList() {
        //TODO
    }

    public String getWishListName() { return WishListDAO.getWishListName(this); }

    public String getWishListDescription() {
        return WishListDAO.getWishListDescription(this);
    }

    public ArrayList<Product> getProducts() { return WishListDAO.getProducts(this); }

    public Product getProduct(int i) { return WishListDAO.getProduct(i, this); }

    public void addProduct(Product product) { WishListDAO.addProduct(product, this); }

    public void addProduct(int i, Product product) { WishListDAO.addProduct(i, product, this); }

    public boolean removeProduct(Product product) { return WishListDAO.removeProduct(product, this); }

    public Product removeProduct(int i) { return WishListDAO.removeProduct(i, this); }

    public void sortProductsByPosition() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.getPosition() - o1.getPosition();
            }
        });
    }
}
