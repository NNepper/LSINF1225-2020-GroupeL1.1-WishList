package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.os.Parcel;
import android.os.Parcelable;

public class Interest implements Parcelable {
    private int id=-1;
    private String interestname;

    public Interest(String name) {
        this.interestname = name;
    }

    protected Interest(Parcel in) {
        id = in.readInt();
        interestname = in.readString();
    }

    public static final Creator<Interest> CREATOR = new Creator<Interest>() {
        @Override
        public Interest createFromParcel(Parcel in) {
            return new Interest(in);
        }

        @Override
        public Interest[] newArray(int size) {
            return new Interest[size];
        }
    };

    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public String getInterestName() {return this.interestname;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(interestname);
    }
}
