package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;


public class Product {
    private int id;
    public String name;
    public String description;
    public String link;
    public int position;
    public int purchased;
    public int quantity;
    public float rating;

    public WishList wishlist;

    public Product(int id) {
        this.id = id;
    }

    public Product(String productName, int quantity, String link, int position){
        this.name = productName;
        this.quantity = quantity;
        this.link = link;
        this.position = position;
        this.description = " ";
        this.purchased = 0;
        this.id = -1;
        this.rating = 0;
    }


    public void setId(int id){ this.id = id; }

    public int getId(){ return this.id; }

    public void setWebLink(String link) {
        this.link = link;
        // ProductDAO.update(this); TODO ne fonctionne par car update n'est pas static
    }

    public void setPurchase(int purchased) {
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

    public void editProduct(String link, int purchased, int position, int quantity) {
        setWebLink(link);
        setPurchase(purchased);
        setPosition(position);
        setQuantity(quantity);
    }


}
