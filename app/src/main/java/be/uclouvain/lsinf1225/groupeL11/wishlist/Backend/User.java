package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.*;

public class User implements Parcelable {
    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public String username;
    public String color;
    public String tshirtSize;
    public String trouserSize;
    public int shoeSize;
    public String address;
    public Integer privacy = 0;
    public ArrayList<Interest> interests;
    public ArrayList<User> following;
    public ArrayList<WishList> wishlists;


    /* User's constructor */
    public User(int id)
    {
        this.id = id;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = null;
        this.lastname = null;
        this.color = null;
        this.tshirtSize = null;
        this.trouserSize = null;
        this.shoeSize = 0;
        this.address = null;
        this.privacy = 0;
        this.interests = null;
        this.following = null;
        this.wishlists = null;
    }

    public User(String email){
        this.email = email;
        // null init to avoid Segfault
        this.firstname = null;
        this.lastname = null;
        this.email = null;
        this.password = null;
        this.username = null;
        this.color = null;
        this.tshirtSize = null;
        this.trouserSize = null;
        this.shoeSize = 0;
        this.address = null;
        this.privacy = 0;
        this.interests = null;
        this.following = null;
        this.wishlists = null;
    }

    protected User(Parcel in) {
        id = in.readInt();
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        password = in.readString();
        username = in.readString();
        color = in.readString();
        tshirtSize = in.readString();
        trouserSize = in.readString();
        shoeSize = in.readInt();
        address = in.readString();
        byte tmpPrivacy = in.readByte();
        privacy = in.readInt();
        interests = in.createTypedArrayList(Interest.CREATOR);
        following = in.createTypedArrayList(User.CREATOR);
        wishlists = in.createTypedArrayList(WishList.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public int getId() { return this.id; }

    public void setId(int id){ this.id = id; }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(username);
        dest.writeString(color);
        dest.writeString(tshirtSize);
        dest.writeString(trouserSize);
        dest.writeInt(shoeSize);
        dest.writeInt(privacy);
        dest.writeTypedList(interests);
        dest.writeTypedList(following);
        dest.writeTypedList(wishlists);
    }





    /**
    public void askFollow(User followed) {
        if (this.getPrivacy()) {
            UserDAO.addFollow(this, followed, false);
        } else {
            UserDAO.addFollow(this, followed, true);
        }
    }
    public void acceptFollow(User toAccept) {
        UserDAO.setFollow(toAccept, this, true);
    }
     **/
}

