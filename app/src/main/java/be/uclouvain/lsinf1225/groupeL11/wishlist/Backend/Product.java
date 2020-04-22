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

    public void setWebLink(String link) {
        this.link = link;
        // ProductDAO.update(this); TODO ne fonctionne par car update n'est pas static
    }

    public void setPurchase(boolean purchased) {
        this.purchased = purchased;
        // ProductDAO.update(this); TODO ne fonctionne par car update n'est pas static
    }

    public void setPosition(int position) {
        this.position = position;
        // ProductDAO.update(this); TODO ne fonctionne par car update n'est pas static
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // ProductDAO.update(this); TODO ne fonctionne par car update n'est pas static
    }

    public void editProduct(String link, boolean purchased, int position, int quantity) {
        setWebLink(link);
        setPurchase(purchased);
        setPosition(position);
        setQuantity(quantity);
    }
}
