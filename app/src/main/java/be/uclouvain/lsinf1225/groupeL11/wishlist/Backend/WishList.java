package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WishList implements Parcelable {

    private int id;
    public String name;
    public String description;
    public ArrayList<Product> products;
    public User user = null;

    public WishList(int id) {
        this.id = id;
        this.products = new ArrayList<Product>();
    }

    public WishList(String name){
        this.name = name;
        this.products = new ArrayList<Product>();
    }

    protected WishList(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        products = in.createTypedArrayList(Product.CREATOR);
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<WishList> CREATOR = new Creator<WishList>() {
        @Override
        public WishList createFromParcel(Parcel in) {
            return new WishList(in);
        }

        @Override
        public WishList[] newArray(int size) {
            return new WishList[size];
        }
    };

    public void sortProductsByPosition() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.position - o1.position;
            }
        });
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
        dest.writeTypedList(products);
        dest.writeParcelable(user, flags);
    }

    public void setId(int id){ this.id=id; }

    public int getId(){ return this.id; }

    public void addProduct(Product product){
        this.products.add(product);
        product.wishlist = this;
    }

    public ArrayList<Product> getProducts() { return this.products; }

    public Product getProduct(int i) { return this.products.get(i); }

    public void reorder(int fromPosition, int toPosition){

    }

    private void upItem(){

    }

    private void downItem(){

    }
}
