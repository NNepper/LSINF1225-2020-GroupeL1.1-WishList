package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;

public class WishList {
    private String wishListName;
    private String wishListDescription;
    private ArrayList<Product> products;

    public WishList(int wishListID) {
        products = getProducts(wishListID);
        this.sortProductsByPosition();
        wishListName = getWishListName(wishListID);
        wishListDescription = getWishListDescription(wishListID);
    }

    public String getWishListName(int wishListID) { return WishListDAO.getWishListName(wishListID); }

    public String getWishListDescription(int wishListID) { return WishListDAO.getWishListDescription(wishListID); }

    public ArrayList<Product> getProducts(int wishListID) { return WishListDAO.getProducts(wishListID); }

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
