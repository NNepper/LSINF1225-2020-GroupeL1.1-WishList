package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;

public class Product {
    private int id = -1;
    private String name;
    private String description;
    private String link;
    public WishList list;
    public int position;
    public String shipping;
    public int quantity;

    public Product(String name,
                   String description,
                   String link,
                   int position,
                   String shipping,
                   int quantity,
                   WishList list) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.position = position;
        this.shipping = shipping;
        this.quantity = quantity;
        this.list = list;
    }

    public void setId(int id){ this.id = id; }

    public int getId(){ return this.id; }

    public String getName(){ return this.name; }

    public String getDescription(){ return this.description; }

    public String getLink(){ return this.link; }

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
