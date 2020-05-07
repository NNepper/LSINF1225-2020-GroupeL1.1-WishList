package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.os.Parcel;
import android.os.Parcelable;

public class Interest {
    private int id=-1;
    private String interestname;

    public Interest(String name) {
        this.interestname = name;
    }

    protected Interest(Parcel in) {
        id = in.readInt();
        interestname = in.readString();
    }


    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public String getInterestname() {return this.interestname;}

    public String getInterestName() {return this.interestname;}

}
