package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;

public class Product implements Parcelable {
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

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        link = in.readString();
        position = in.readInt();
        purchased = in.readByte() != 0;
        quantity = in.readInt();
        wishlist = in.readParcelable(WishList.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeInt(position);
        dest.writeByte((byte) (purchased ? 1 : 0));
        dest.writeInt(quantity);
        dest.writeParcelable(wishlist, flags);
    }
}
