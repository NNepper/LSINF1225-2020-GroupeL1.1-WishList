package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;

public class Product {
    private int id = -1;
    public String name;
    public String description;
    public String link;
    public int position;
    public boolean purchased;
    public int quantity;

    public WishList wishlist;

    public Product(int id) {
        this.id = id;
    }

    public void setId(int id){ this.id = id; }

    public int getId(){ return this.id; }

    /**
    public void setWebLink(String newWeblink) { ProductDAO.setWeblink(newWeblink); }

    public void setShipping(String newShipping) { ProductDAO.setShpping(newShipping); }

    public void setPosition(int position) { ProductDAO.setPosition(position); }

    public void setQuantity(int quantity) { ProductDAO.setQuantity(quantity); }

    public String getProductName() {
        return ProductDAO.getProductName(this);
    }

    public String getProductDescription() {
        return ProductDAO.getProductDescription(this);
    }

    public String getWeblink() {
        return ProductDAO.getWeblink(this);
    }

    public String getProductPicture() {
        return ProductDAO.getProductPicture(this);
    }

    public String getShipping() {
        return ProductDAO.getShipping(this);
    }

    public int getPosition() {
        return ProductDAO.getPosition(this);
    }

    public int getQuantity() {
        return ProductDAO.getQuantity(this);
    }

    public void editProduct(String Weblink, String Shipping, int position, int quantity) {
        setWebLink(Weblink);
        setShipping(Shipping);
        setPosition(position);
        setQuantity(quantity);
    }
     **/
}
