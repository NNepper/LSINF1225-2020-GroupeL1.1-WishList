package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

public class Product {
    private String productName;
    private String productDescription;
    private String webLink;
    private String productPicture;
    private String shipping;
    private int position;
    private int quantity;

    public Product(String productName, String productDescription, String webLink, String productPicture, String shipping, int position, int quantity) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.webLink = webLink;
        this.productPicture = productPicture;
        this.shipping = shipping;
        this.position = position;
        this.quantity = quantity;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getWebLink() {
        return webLink;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public String getShipping() {
        return shipping;
    }

    public int getPosition() {
        return position;
    }

    public int getQuantity() {
        return quantity;
    }
}
