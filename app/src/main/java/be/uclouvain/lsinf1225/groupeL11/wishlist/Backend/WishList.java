package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

public class WishList {

    private int id;
    public String name;
    public String description;
    public ArrayList<Product> products;
    public User user = null;

    public WishList(int id) {
        this.id = id;
        this.products = new ArrayList<Product>();
    }

    public void setId(int id){ this.id=id; }

    public int getId(){ return this.id; }

    public void addProduct(Product product){
        this.products.add(product);
        product.wishlist = this;
    }

    /**
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
     **/
    /**
    public Map<String, Object> getWishListInfos() {
        Map<String, Object> data = new HashMap<>();
        data.put("wishlist name", this.wishListName);
        data.put("wishlist description", this.wishListDescription);
        sortProductsByPosition();
        data.put("products", this.products);
        return data;
    }
     **/
}
